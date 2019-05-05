import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LoadingService } from 'src/app/services/loading.service';
import { ToastHandleService } from 'src/app/services/toasthandle.service';
import { Constant } from 'src/app/common/constant';
import { OrderService } from 'src/app/services/order.service';
import { NativeGeocoder, NativeGeocoderOptions, NativeGeocoderResult } from '@ionic-native/native-geocoder/ngx';
import { GoogleApiService } from 'src/app/services/google-api.service';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.page.html',
  styleUrls: ['./order-detail.page.scss'],
})
export class OrderDetailPage implements OnInit {

  orderId: number;
  myOrder = [];
  isLoaded = false;
  total: any;
  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private loading: LoadingService,
    private toastHandle: ToastHandleService,
    private constant: Constant,
    private orderService: OrderService,
    private nativeGeocoder: NativeGeocoder,
    private googleAPI: GoogleApiService,
  ) {
    this.orderId = 0;
    this.myOrder.length = 0;
    this.activatedRoute.paramMap
      .subscribe(param => {
        this.orderId = parseInt(param.get('id'));
        // this.getOrderbyID();
      })
  }

  async ngOnInit() {
    const sleep = (milliseconds) => {
      return new Promise(resolve => setTimeout(resolve, milliseconds))
    }
    await sleep(1000);
    this.loading.present("Waiting...").then(() => {
      // this.prepareForData();
      this.getOrderbyID()
    })//end loading
  }

  // async  prepareForData() {
  //   // if (this.myOrder.length !== 0) {
  //   console.log("3");
  //   console.log(this.myOrder);
  //   console.log(this.myOrder[0].data.latitude);
  //   // this.storage.get("MYLOCATION").then(value => {
  //   // this.geolocation.getCurrentPosition().then((resp) => {
  //   //   let lati = resp.coords.latitude;
  //   //   let longi = resp.coords.longitude;
  //   await this.googleAPI.getAddressGoogle(this.myOrder[0].data.latitude, this.myOrder[0].data.longitude, this.myOrder[0].data.storeLatitude, this.myOrder[0].data.storeLongitude)
  //     .then((res) => {
  //       console.log(res)

  //       let myArr = JSON.parse(res.data);
  //       console.log("myArr");
  //       console.log(myArr);
  //       let currentAddress = myArr.routes[0].legs[0].start_address;
  //       let duration = myArr.routes[0].legs[0].duration.text.replace(" phút", "");
  //       duration = parseInt(duration) + 15;
  //       duration += " phút";
  //       let distance = myArr.routes[0].legs[0].distance.text.split(" ", 1);
  //       // let shpEarn = this.calculateShpEarn(parseInt(this.distance));
  //       this.myOrder.forEach(element => {
  //         element["currentAddress"] = currentAddress;
  //         element["duration"] = duration;
  //         element["distance"] = distance;
  //       });
  //       this.total = this.myOrder[0].data.totalPrice + this.myOrder[0].data.shipperEarn;
  //       this.isLoaded = true;
  //       this.loading.dismiss();
  //     })//end google api
  //   // });//end storage
  //   // }//end if check myorder.length

  // }

  async getOrderbyID() {
    //get order by id
    await this.orderService.getOrderDetailById(this.orderId).subscribe(res => {
      this.myOrder.push(res);
      console.log(this.myOrder)
      this.myOrder[0].data["total"] = this.myOrder[0].data.totalPrice + this.myOrder[0].data.shipperEarn
      if (this.myOrder) {
        console.log(this.myOrder[0].data.address);
        this.isLoaded = true;
        this.loading.dismiss();
      }//end if check myOrder

    })//end api get order by id
  }//end get order by id


  back() {
    this.router.navigateByUrl("order-history")
  }

}
