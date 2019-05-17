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
import { AccountService } from 'src/app/services/account.service';

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
  quantity = 1;
  status_code = 0;

  latitudeCus: any;
  longitudeCus: any;
  duration: any;
  distance: any;
  shpEarn: any;
  currentAddress: any;
  isLoaded = false;
  userWallet : any;
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
    private accountService : AccountService,
  ) {
    this.products.length = 0;

    this.userWallet = '';

    this.latitudeCus = '';
    this.longitudeCus = '';
    this.duration = '';
    this.distance = '';
    this.shpEarn = '';
  }

  ngOnInit() {
    this.loading.present(this.constant.LOADINGMSG).then(() => {
      this.getStoreDetail();
      this.getWallet();
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
    setTimeout(() => {},1000);
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
          userWallet : this.userWallet,
        }]

      }
    }).then(modal => {
      modal.present();
      // this.currentModal = modal;
    });
  }

  backToHome() {
    this.router.navigateByUrl("home");
  }

  getStoreByid(id: number) {
    this.products.length = 0;
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
              let myArr =  JSON.parse(res.data);
              this.currentAddress = myArr.routes[0].legs[0].start_address
              this.duration = myArr.routes[0].legs[0].duration.text.replace(" phút", "");
              this.duration = parseInt(this.duration) + 15;
              this.duration += " phút";
              this.distance = myArr.routes[0].legs[0].distance.text.split(" ",1);
              this.shpEarn = this.calculateShpEarn(parseFloat(this.distance));
            });//end google api
        });//end storage
        this.products[0].data.proList.forEach(element => {
          element["quantity"] = 0;
          element["total"] = 0;
          element.price = element.price;
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
    // if (dis < 1) {
    //   kms = 1
    // } else {
    //   kms = dis
    // }
    kms = dis;
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

  getWallet(){
    this.accountService.getAvatar().subscribe(res => {
        let tempArr = [];
        tempArr.push(res);
      this.userWallet = tempArr[0].data.wallet;
      console.log(this.userWallet);
    })//end api get avatar 
  }
} 
