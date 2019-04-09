import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OrderService } from 'src/app/services/order.service';
import { GoogleApiService } from 'src/app/services/google-api.service';
import { async } from '@angular/core/testing';
import { LoadingService } from 'src/app/services/loading.service';
import { Storage } from '@ionic/storage';
import { Geolocation } from '@ionic-native/geolocation/ngx';

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
  ) {
    this.myOrder.length = 0;
    this.orderId = this.route.snapshot.params['id'];
    

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
      this.prepareForData();
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
    console.log("4");
    await this.orderService.getOrderDetailById(this.orderId).subscribe(res => {
      console.log("5");
      console.log(res)
      this.myOrder.push(res);
      console.log("-----------");
      if (this.myOrder) {
        console.log("6");
      }//end if check myOrder

    })//end get order by id
  }

}
