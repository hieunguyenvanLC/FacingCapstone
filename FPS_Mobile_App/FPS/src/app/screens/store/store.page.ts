import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import {
  ToastController,
  ModalController
} from '@ionic/angular';
import { OrdermodalPage } from '../ordermodal/ordermodal.page';
import { StoreService } from 'src/app/services/store.service';
import { Store } from './../../models/store.model';
import { LoadingService } from 'src/app/services/loading.service';
import { ToastHandleService } from 'src/app/services/toasthandle.service';
import { GoogleApiService } from 'src/app/services/google-api.service';
import { Storage } from '@ionic/storage';
import { Constant } from 'src/app/common/constant';

@Component({
  selector: 'app-store',
  templateUrl: './store.page.html',
  styleUrls: ['./store.page.scss'],
})
export class StorePage implements OnInit {
  num = 0;
  total = 0;
  products = []
  orders = []
  isHaveProduct = false;
  currentModal = null;
  storeId: number;
  store: Store[];
  quantity = 1;
  status_code = 0;
  temp = [];

  latitudeCus: any;
  longitudeCus: any;
  duration: any;
  distance: any;
  shpEarn: any;
  currentAddress: any;
  isLoaded = false;

  // {
  //   id: "AL1",
  //   img: "../../assets/image/trasua1.jpg",
  //   name: "Trà sữa chân trâu đường đen - size M (500ml)",
  //   price: "100000",
  //   quantity: 0
  // },
  // {
  //   id: "AL2",
  //   img: "../../assets/image/trasua1.jpg",
  //   name: "Trà sữa chân trâu đường đen - size L (750ml)",
  //   price: "500000",
  //   quantity: 0
  // },
  // {
  //   id: "AL3",
  //   img: "../../assets/image/trasua1.jpg",
  //   name: "Trà sữa chân trâu đường đen - size XL (1050ml)",
  //   price: "100000",
  //   quantity: 0
  // },
  // {
  //   id: "AL4",
  //   img: "../../assets/image/trasua1.jpg",
  //   name: "Trà sữa chân trâu đường đen - size XXL (1200ml)",
  //   price: "100000",
  //   quantity: 0
  // },
  // {
  //   id: "AL5",
  //   img: "../../assets/image/trasua1.jpg",
  //   name: "Trà sữa chân trâu đường đen - size S (500ml)",
  //   price: "100000",
  //   quantity: 0
  // }

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private modalController: ModalController,
    private storeService: StoreService,
    private loading: LoadingService,
    private toastHandle: ToastHandleService,
    private googleApi: GoogleApiService,
    private storage: Storage,
    private constant: Constant,
  ) {
  }

  ngOnInit() {
    this.loading.present(this.constant.LOADINGMSG).then(() => {
      this.getStoreDetail();
    })


  }

  getStoreDetail() {
    this.activatedRoute.paramMap
      .subscribe(param => {
        this.storeId = parseInt(param.get('id'));
        this.getStoreByid(this.storeId);
      });
  }

  addProduct(id) {
    let prod = this.products[0].data.proList.find(obj => obj.id == id); // find product in product list
    // console.log(prod);
    let prodInOrder;
    if (prod.quantity == 0) {
      prod.quantity++;
      this.total += parseInt(prod.price);
      prod.total = prod.quantity * prod.price;
    }
    if (this.orders != []) {
      prodInOrder = this.orders.find(obj => obj.id == id); // find product in order list
      if (prodInOrder != undefined) {
        prodInOrder.quantity++;
        this.total += parseInt(prod.price);
        prodInOrder.total = prodInOrder.quantity * prodInOrder.price;
      } else {
        this.orders.push(prod); //add new a product to array
      }
    }
    this.num++;
    if (this.num == 1) {
      this.isHaveProduct = true; // turn on footer
    }
    console.log(this.orders);
  }

  removeProduct(id) {
    let prodInOrder = this.orders.find(obj => obj.id == id);
    if (prodInOrder.id == id) {
      if (prodInOrder.quantity > 0) {
        prodInOrder.quantity--;
        this.total -= parseInt(prodInOrder.price);
        prodInOrder.total = prodInOrder.quantity * prodInOrder.price;
        if (prodInOrder.quantity == 0) {
          const index = this.orders.indexOf(id, 0);
          this.orders.splice(index, 1) // delete an array item in prodInOrder
        }
        if (this.num > 0) {
          this.num--;
          if (this.num == 0) {
            this.isHaveProduct = false; // turn off footer
          }
        }//end if num
      } //end if quantity
    }// end if id
    console.log(this.orders);
  }

  async openOrderModal() {
    console.log("o trong openModalOder");
    console.log("currentAddress");
    console.log(this.currentAddress);
    await this.modalController.create({
      animated: true,
      component: OrdermodalPage,
      componentProps: {
        myOrder: [{
          products: this.orders,
          latitudeStore: this.products[0].data.latitude, //latitude store
          longitudeStore: this.products[0].data.longitude, //longitude store
          addressStore: this.products[0].data.address + ", " + this.products[0].data.distStr,
          subTotal: this.total,
          shpEarn: this.shpEarn,
          duration: this.duration,
          distance: this.distance,
          currentAddress: this.currentAddress,
          latitudeCus: this.latitudeCus,
          longitudeCus: this.longitudeCus,
        }]

      }
    }).then(modal => {
      modal.present();
      this.currentModal = modal;
    });
  }

  backToHome() {
    this.router.navigateByUrl("home");
  }

  getStoreByid(id: number) {
    this.storeService.getStorebyid(id).subscribe(
      res => {
        this.products.push(res);
        console.log(this.products[0].data);

        this.status_code = this.products[0].status_code;
        this.storage.get("MYLOCATION").then(value => {
          console.log(value);
          this.latitudeCus = value.latitude;
          this.longitudeCus = value.longitude;
          // console.log(value.longitude);
          //console.log(this.products[0].data.latitude);
          //console.log(this.products[0].data.longitude);
          this.googleApi.getAddressGoogle(value.latitude, value.longitude, this.products[0].data.latitude, this.products[0].data.longitude)
            .then(res => {
              console.log("START GOOGLE ----")
              console.log(res);
              console.log(res.data)
              this.temp.push(res);
              let myArr =  JSON.parse(res.data);
              console.log("myArr");
              console.log(myArr);
              console.log(myArr.routes[0].legs[0].start_address);
              

              console.log("----Start address-----")
              // console.log(this.temp[0].routes[0].legs[0].start_address) //start address
              this.currentAddress = myArr.routes[0].legs[0].start_address
              // this.storage.set("STARTADDRESS", this.temp[0].routes[0].legs[0].start_address)
              // console.log(this.temp[0].routes[0].legs[0].start_location.lat) //start latitude
              // console.log(this.temp[0].routes[0].legs[0].start_location.lng)
              // console.log("----End address-----")
              // console.log(this.temp[0].routes[0].legs[0].end_address) //end address
              // console.log(this.temp[0].routes[0].legs[0].end_location.lat) //end latitude
              // console.log(this.temp[0].routes[0].legs[0].end_location.lng)
              // console.log("----Duration------")
              // console.log(this.temp[0].routes[0].legs[0].duration.text)// format : "3 phut"
              this.duration = myArr.routes[0].legs[0].duration.text.replace(" phút", "");
              this.duration = parseInt(this.duration) + 15;
              this.duration += " phút";
              console.log("duration: " + this.duration);
              // console.log(this.temp[0].routes[0].legs[0].distance.text)//distance
              this.distance = myArr.routes[0].legs[0].distance.text.split(" ",1);
              // console.log(distanceArr);
              // this.distance = distanceArr[0];
              this.shpEarn = this.calculateShpEarn(parseInt(this.distance));
              console.log(this.distance)
            });//end google api
        });//end storage
        // console.log(this.products[0].data.proList);
        this.products[0].data.proList.forEach(element => {
          //add 2 attribute into product detail
          element["quantity"] = 0;
          element["total"] = 0;
        });
      }, error => {
        this.toastHandle.presentToast("Error to get product store !");
        console.log(error);
      }, () => {
        if (this.status_code === 1) {
          //handle if success loading component will dismiss
          this.isLoaded = true;
          this.loading.dismiss();

        } else {
          //handle error
        }
      }
    )
  }

  calculateShpEarn(dis) {
    let price = 14000;
    let kms;
    if (dis < 1) {
      kms = 1
    } else {
      kms = dis
    }
    // let kms = Math.ceil(dis);
    if (kms > 0) {
      price += kms * 1000;
    }
    kms -= 5;
    if (kms > 0) {
      price += kms * 1000;
    }
    kms -= 5;
    if (kms > 0) {
      price += kms * 1000;
    }
    return Math.ceil(price/1000) *1000;
  }//end calculateShpEarn
} 
