import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderService } from 'src/app/services/order.service';
import { GoogleApiService } from 'src/app/services/google-api.service';
import { async } from '@angular/core/testing';
import { LoadingService } from 'src/app/services/loading.service';
import { Storage } from '@ionic/storage';
import { Geolocation } from '@ionic-native/geolocation/ngx';
import { FCM } from '@ionic-native/fcm/ngx';
import { CallNumber } from '@ionic-native/call-number/ngx';
import { Firebase } from '@ionic-native/firebase/ngx';

@Component({
  selector: 'app-check-out',
  templateUrl: './check-out.page.html',
  styleUrls: ['./check-out.page.scss'],
})
export class CheckOutPage implements OnInit {

  orderId: any;
  myOrder = [];
  isLoaded = false;
  total : any;
   constructor(
    private route: ActivatedRoute,
    private orderService: OrderService,
    private googleAPI: GoogleApiService,
    private loading: LoadingService,
    private storage: Storage,
    private geolocation: Geolocation,
    private fcm: FCM,
    private router : Router,
    private callNumber: CallNumber,
    private firebase: Firebase,
  ) {
    this.myOrder.length = 0;
    this.orderId = this.route.snapshot.params['id'];
    
    this.fcm.onNotification().subscribe(data => {
      // console.log("vo day");
      // console.log(data);

      if (data.wasTapped) {
        console.log('Received in rating background 1');
        //this.dismissModal();
        //this.loading.dismiss();
        //this.alertHandle.dissmissAlert();
        if (data.orderId !== '' && data.orderId !== undefined && data.orderId !== 0){
          this.router.navigate(['rating', data.orderId]);
        }
        
        //data.order.id

      } else {
        console.log('Received in rating background 3');
        //this.dismissModal();
        //this.loading.dismiss();
        //this.alertHandle.dissmissAlert();
        //this.currentOrder !== '' && this.currentOrder !== undefined && this.currentOrder !== 0
        if (data.orderId !== '' && data.orderId !== undefined && data.orderId !== 0){
          this.router.navigate(['rating', data.orderId]);
        }
      }
    });// end fcm

    this.firebase.onNotificationOpen()
      .subscribe(data => {
        console.log('Received in rating background 2');
       //this.dismissModal();
        //this.loading.dismiss();
        //this.alertHandle.dissmissAlert();
        if (data.orderId !== '' && data.orderId !== undefined && data.orderId !== 0){
          this.router.navigate(['rating', data.orderId]);
        }
      });
    console.log("1");
    this.getOrderbyID();
  }

  
  async ngOnInit() {
    const sleep = (milliseconds) => {
      return new Promise(resolve => setTimeout(resolve, milliseconds))
    }
    await sleep(1000);
    this.loading.present("Waiting...").then(() => {
      console.log("2");
      // this.prepareForData();
      this.getOrderbyID();
    })//end loading
  }

  async  prepareForData() {
    // if (this.myOrder.length !== 0) {
      console.log("3");
      console.log(this.myOrder);
      console.log(this.myOrder[0].data.latitude);
      // this.storage.get("MYLOCATION").then(value => {
      // this.geolocation.getCurrentPosition().then((resp) => {
      //   let lati = resp.coords.latitude;
      //   let longi = resp.coords.longitude;
      await  this.googleAPI.getAddressGoogle(this.myOrder[0].data.latitude, this.myOrder[0].data.longitude, this.myOrder[0].data.storeLatitude, this.myOrder[0].data.storeLongitude)
          .then((res) => {
            console.log(res)

            let myArr = JSON.parse(res.data);
            console.log("myArr");
            console.log(myArr);
            let currentAddress = myArr.routes[0].legs[0].start_address;
            let duration = myArr.routes[0].legs[0].duration.text.replace(" phút", "");
            duration = parseInt(duration) + 15;
            duration += " phút";
            let distance = myArr.routes[0].legs[0].distance.text.split(" ", 1);
            // let shpEarn = this.calculateShpEarn(parseInt(this.distance));
            this.myOrder.forEach(element => {
              element["currentAddress"] = currentAddress;
              element["duration"] = duration;
              element["distance"] = distance;
            });
            this.total = this.myOrder[0].data.totalPrice + this.myOrder[0].data.shipperEarn;
            this.isLoaded = true;
            this.loading.dismiss();
          })//end google api
      // });//end storage
    // }//end if check myorder.length

  }

  async getOrderbyID(){
    //get order by id
    await this.orderService.getOrderDetailById(this.orderId).subscribe(res => {
      this.myOrder.push(res);
      this.myOrder[0].data["total"] = this.myOrder[0].data.totalPrice + this.myOrder[0].data.shipperEarn
      if (this.myOrder) {
        console.log(this.myOrder[0].data.address);
        this.isLoaded = true;
        this.loading.dismiss();
      }//end if check myOrder

    })//end api get order by id
  }//end get order by id

  callNow(){
    this.callNumber.callNumber(this.myOrder[0].data.shipperPhone, true)
      .then(res => console.log('Launched dialer!', res))
      .catch(err => console.log('Error launching dialer', err));
  }//end call now
}
