import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { OrderService } from 'src/app/services/order.service';
import { LoadingService } from 'src/app/services/loading.service';
import { Router } from '@angular/router';
import { Camera, CameraOptions } from '@ionic-native/camera/ngx';
import { ToasthandleService } from 'src/app/services/toasthandle.service';
import { FCM } from '@ionic-native/fcm/ngx';
import { Geolocation } from '@ionic-native/geolocation/ngx';
import { NativeGeocoder, NativeGeocoderResult, NativeGeocoderOptions } from '@ionic-native/native-geocoder/ngx';
import { Platform, NavController } from "@ionic/angular";

import {
  GoogleMaps,
  GoogleMap,
  MyLocation,
  Marker,
  GoogleMapsAnimation,
  GoogleMapsEvent,
  Environment,
  GoogleMapOptions,
  Polyline,
  PolylineOptions,
  ILatLng,
} from '@ionic-native/google-maps/ngx';
// import { GoogleMap, GoogleMaps } from '@ionic-native/google-maps/ngx';
import { element } from '@angular/core/src/render3';
import { GoogleApiService } from 'src/app/services/google-api.service';
import { AccountService } from 'src/app/services/account.service';
import { AppComponent } from 'src/app/app.component';
import { CallNumber } from '@ionic-native/call-number/ngx';
declare var google;

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  @ViewChild('map') mapElement: ElementRef;
  map: GoogleMap;
  address: string;

  shipperMode = false;
  stateOrder: number;

  // longitudeShp = "106.67927390539103";
  // latitudeShp = "10.83767617410066";

  order = [];
  isLoading = false;
  isLoaded = false;

  myPhoto: any;
  myPhotoBinary: string;
  tokenFCM: any;
  currentOrder: any;
  currentOrderStatus: any;


  constructor(
    public platform: Platform,
    private orderService: OrderService,
    private loading: LoadingService,
    private router: Router,
    private camera: Camera,
    private toastHandle: ToasthandleService,
    private fcm: FCM,
    private geolocation: Geolocation,
    private nativeGeocoder: NativeGeocoder,
    private GoogleApiService: GoogleApiService,
    private accountService: AccountService,
    private appComponent: AppComponent,
    private callNumber: CallNumber,

  ) {
    this.shipperMode = false;
    this.stateOrder = 0;
    this.order.length = 0;
    this.currentOrder = '';
    this.currentOrderStatus = '';

    //firebase
    this.fcm.getToken().then(token => {
      // console.log(token);
      this.tokenFCM = token;
      console.log(this.tokenFCM)
    });
    this.fcm.onTokenRefresh().subscribe(token => {
      // console.log(token);
    });
    this.fcm.onNotification().subscribe(data => {
      console.log(data);
      if (data.wasTapped) {
        console.log('Received in background');

        //data.order.id
        this.loading.dismiss();
        // this.router.navigate(['check-out', data.order.id]);
      } else {
        this.loading.dismiss();
        console.log('Received in foreground');
        // this.router.navigate([data.landing_page, data.price]);
      }
    });// end fcm

    this.orderService.getCurrentOrder().subscribe(res => {
      console.log("1");
      console.log(res);
      let tempArr = [];
      tempArr.push(res);
      this.currentOrder = tempArr[0].data.orderId;
      this.currentOrderStatus = tempArr[0].data.orderStatus;
    })//end api get current order

  }


  //  async ngOnInit() {
  //    await this.loadMap();
  //   }
  async ionViewDidEnter() {
    // console.log("call ionViewDidLoad");
    await this.platform.ready().then(() => {
      this.loadMap();
    });
    this.getUser();
    console.log(this.currentOrder)
    if (this.currentOrder !== '' && this.currentOrder !== undefined && this.currentOrder !== 0) {
      this.shipperMode = true;
    }
  }

  getUser() {
    this.accountService.getAvatar().subscribe(
      res => {
        //this.userDetail.push(res);
        let tempArr = [];
        tempArr.push(res);
        // console.log("in get avatar user----")
        // console.log(tempArr[0].data);
        this.appComponent.refreshSlideMenu(tempArr[0].data.name, tempArr[0].data.avatar, tempArr[0].data.sumRevenue);
      }, error => {
        console.log(error);
      },
      // () => {
      //   if (this.status_code === 1){
      //     this.loading.dismiss();
      //   }else{
      //     //handle error
      //   }
      // }
    );
  }

  changeMode() {
    if (this.shipperMode) {
      if (this.currentOrder !== '' && this.currentOrder !== undefined && this.currentOrder !== 0) {
        this.loading.present("Loading....").then(() => {
          this.orderService.getOrderDetailById(this.currentOrder).subscribe(res => {
            console.log("Change mode");
            console.log(res);
            this.order.length = 0;
            this.order.push(res);
  
            this.order[0].data["total"] = this.order[0].data.totalPrice + this.order[0].data.shipperEarn
            this.order[0].data["shipperMoney"] = this.order[0].data.priceLevel * this.order[0].data.shipperEarn
            if (this.order[0].message === "Success") {
              if (this.order[0].data) {
                this.isLoaded = true;
                // add routing function here
                this.loading.dismiss();
                this.routingForShipper(this.order[0].data.storeLatitude, this.order[0].data.storeLongitude, false);
                console.log(this.order[0].storeLatitude)
                if (this.order[0].data.status !== 3){
                  this.stateOrder = 1;
                }else{
                  this.stateOrder = 2;
                }
                
                // this.isLoading = false;
                // this.loading.dismiss();
              }
            }//end if success
          })//end api get detail
        })//end loading
      } else {
        this.findOrder();
        this.toastHandle.presentToast("Finding order");
      }


    } else {
      //if mode is off
      this.orderService.offShipperMode().subscribe(res => {

        this.isLoaded = false;
      });//end api
      this.stateOrder = 0;
      this.toastHandle.presentToast("Shipper mode is off !");
    }
  }

  findOrder() {
    this.stateOrder = 1;
    //call api to auto assign order
    this.geolocation.getCurrentPosition().then((resp) => {
      console.log(resp.coords.latitude)
      console.log(resp.coords.longitude)
      this.orderService.onShipMode(resp.coords.longitude, resp.coords.latitude, this.tokenFCM).subscribe(
        res => {
          this.order.length = 0;
          this.order.push(res);
          console.log("in find order")
          console.log(res)
          this.order[0].data["total"] = this.order[0].data.totalPrice + this.order[0].data.shipperEarn
          this.order[0].data["shipperMoney"] = this.order[0].data.priceLevel * this.order[0].data.shipperEarn
          if (this.order[0].message === "Success") {
            if (this.order[0].data) {
              this.isLoaded = true;
              // add routing function here
              this.routingForShipper(this.order[0].data.storeLatitude, this.order[0].data.storeLongitude, false);
              this.stateOrder = 1;
              // this.isLoading = false;
              // this.loading.dismiss();
              console.log("in if success :" + this.order[0].data);
            }
          }//end if success
          else if (this.order[0].message === "time out") {
            this.isLoaded = false;
            // this.isLoading = false;
            //this.loading.dismiss();
            // this.shipperMode = false;
            this.stateOrder = 0;
            this.toastHandle.presentToast("Don't have any order near you! Try find again !");
          }//end if time out
        }, () => {

        }
      ); //end api auto assign order
    })//end geolocation
  }//end find order



  routingForShipper(desLatitude, desLongitude, isTakeBill) {
    // loadMap() {
    // console.log(order[0].data.latitude, order[0].data.longitude);
    this.geolocation.getCurrentPosition().then(respGeo => {
        //resp.coords.latitude, resp.coords.longitude
        this.GoogleApiService.getAddressGoogle(respGeo.coords.latitude, respGeo.coords.longitude,desLatitude, desLongitude).then((resp) => {
          // , order[0].data.latitude, order[0].data.longitude
          console.log("at routing 1")
          console.log("GEO" + respGeo.coords.latitude + " - " + respGeo.coords.longitude)
          console.log("DES" + desLatitude + " - " + desLongitude);
          // get resp
          let positionList = [];
          positionList.length = 0;
          positionList.push(resp);
          // get step
    
          //let steps = [];
    
          let direct = [];
          let steps = JSON.parse(resp.data);
          // console.log("-----------");
          // console.log(steps)
          let stepsChild = steps.routes[0].legs[0].steps;
          // console.log("steps here");
          // console.log(stepsChild);
    
          stepsChild.forEach(element => {
            direct.push(element.start_location);
            direct.push(element.end_location);
          });
          // draw map
          let latLng = new google.maps.LatLng(respGeo.coords.latitude, respGeo.coords.longitude);
          // let latLng = new google.maps.LatLng(10.831481, 106.676775);
          let mapOptions = {
            center: latLng,
    
            zoom: 16.8,
            tilt: 30,
            mapTypeId: google.maps.MapTypeId.ROADMAP
          }
          this.map = null;
          var map1: any;
          map1 = new google.maps.Map(this.mapElement.nativeElement, mapOptions);
          map1.addListener('tilesloaded', () => {
            console.log('accuracy', map1);
            this.getAddressFromCoords(map1.center.lat(), map1.center.lng())
          });
    
          // let marker = new google.maps.Marker({
          //   title: 'Store here',
          //   map: map1,
          //   position: latLng,
    
          // })
          let marker = new google.maps.Marker({
            title: 'You are here',
            map: map1,
            position: latLng,
            icon:{
              url: '../../assets/image/shipper.png',
              size: {
                  width: 30,
                  height: 40
               }
             },
             snippet: 'You are here',
          })

          if (isTakeBill === false){
            console.log("at routing 2")
            let marker1 = new google.maps.Marker({
              title: 'Building',
              map: map1,
              position: new google.maps.LatLng(desLatitude, desLongitude ),
              icon:{
                url: './../../assets/image/building.svg',
                // size: {
                //     width: 30,
                //     height: 40
                //  }
               },
               snippet: '',
            })//end marker building
          }//end if not take bill
          else{
            console.log("at routing 3")
            let marker1 = new google.maps.Marker({
              title: 'Customer',
              map: map1,
              position: new google.maps.LatLng(desLatitude, desLongitude),
              icon:{
                url: '../../../assets/image/customer.png',
                // size: {
                //     width: 30,
                //     height: 40
                //  }
               },
               snippet: '',
            })
          }//end else
    
          // let marker1 = new google.maps.Marker({
          //   title: 'You are here',
          //   map: this.map,
          //   position: new google.maps.LatLng(location[1][0], location[1][1]),
          //   icon:{
          //     url: '../../assets/image/building.svg',
          //     size: {
          //         width: 30,
          //         height: 40
          //      }
          //    },
          //    snippet: 'You are here',
          // })
    
          // draw rooting
          const sleep = (milliseconds) => {
            return new Promise(resolve => setTimeout(resolve, milliseconds))
          }
    
    
          var polyline = new google.maps.Polyline({
            path: direct,
            geodesic: true,
            strokeColor: '#FF0000',
            strokeOpacity: 1.0,
            strokeWeight: 5
          });
          polyline.setMap(map1);
    
    
          this.map = map1;
          // and resp
        });//end getaddress google
      
    })
    

  }

  cancelFindOrder() {
    this.orderService.offShipperMode().subscribe(res => {
      console.log("in off: ");
      console.log(res);
      // this.isLoaded = false;
    });//end api
    this.stateOrder = 0;
  }//end cancel find order

  checkout() {
    const options: CameraOptions = {
      quality: 100,
      destinationType: this.camera.DestinationType.DATA_URL,
      //sourceType: this.camera.PictureSourceType.PHOTOLIBRARY,
      encodingType: this.camera.EncodingType.JPEG,
      mediaType: this.camera.MediaType.PICTURE,
      saveToPhotoAlbum: false,
      correctOrientation: true,
    }

    this.camera.getPicture(options).then((imageData) => {
      // imageData is either a base64 encoded string or a file URI
      // If it's base64 (DATA_URL):

      // this.myPhoto = 'data:image/jpeg;base64,' + imageData;
      // this.myPhotoBinary = imageData;
      // //this.myPhotoBinary = new Blob([imageData], { type: 'image/jpg' });
      // // this.myPhotoBinary = new Blob([imageData],{type:'image/jpeg'});
      // console.log(imageData);
      // console.log(this.myPhotoBinary);

      //call api
      this.loading.present("Waiting for payment...").then(() => {
        this.orderService.checkOutOrder(this.order[0].data.id, imageData).subscribe(
          res => {
            console.log(res);
            this.loading.dismiss();
            let temp = [];
            temp.push(res);
            if (temp[0].status_code !== 1) {
              this.toastHandle.presentToast("Check out error ! Try again !");
            } else {
              this.toastHandle.presentToast("Check out success");
              this.isLoaded = false;
              this.stateOrder = 0;
              this.map = null;
              this.loadMap();
              this.order.length = 0;
            }

          }, (err) => {
            this.loading.dismiss();
            this.toastHandle.presentToast("Check out error");
            console.log("error check out ");
            console.log(err);
          }
        );//end api check out
      })

    }, (err) => {
      console.log("error at takephoto :" + err)
    });
  }//end check out

  takeBill() {
    const options: CameraOptions = {
      quality: 100,
      destinationType: this.camera.DestinationType.DATA_URL,
      //sourceType: this.camera.PictureSourceType.PHOTOLIBRARY,
      encodingType: this.camera.EncodingType.JPEG,
      mediaType: this.camera.MediaType.PICTURE,
      saveToPhotoAlbum: false,
      correctOrientation: true,
    }

    this.camera.getPicture(options).then((imageData) => {
      // imageData is either a base64 encoded string or a file URI
      // If it's base64 (DATA_URL):

      // this.myPhoto = 'data:image/jpeg;base64,' + imageData;
      // this.myPhotoBinary = imageData;
      //this.myPhotoBinary = new Blob([imageData], { type: 'image/jpg' });
      // this.myPhotoBinary = new Blob([imageData],{type:'image/jpeg'});
      // console.log(imageData);
      // console.log(this.myPhotoBinary);

      //call api
      this.loading.present("Waiting...").then(() => {
        this.orderService.takeBill(this.order[0].data.id, imageData).subscribe(
          res => {
            console.log(res);
            this.stateOrder = 2;
            this.toastHandle.presentToast("Take bill success !");
            this.loading.dismiss();
            this.routingForShipper(this.order[0].data.latitude, this.order[0].data.longitude, true);
          }, (err) => {
            this.loading.dismiss();
            this.toastHandle.presentToast("Take bill error !");
            console.log("error take bill ");
            console.log(err);
          }
        );//end api check out
      })

    }, (err) => {
      console.log("error at takephoto :" + err)
    });
  }//end take bill

  //#region *************** BEGIN MAP ****************

  loadMap() {
    this.geolocation.getCurrentPosition().then((resp) => {
      
      let latLng = new google.maps.LatLng(resp.coords.latitude, resp.coords.longitude);
      let mapOptions = {
        center: latLng,
        // {

        //           lat: 10.8027415,
        //           lng: 106.6440769
        //         },
        zoom: 16.8,
        tilt: 30,
        mapTypeId: google.maps.MapTypeId.ROADMAP
      }

      this.getAddressFromCoords(resp.coords.latitude, resp.coords.longitude);

      this.map = new google.maps.Map(this.mapElement.nativeElement, mapOptions);

      // this.map.addListener('tilesloaded', () => {
      //   console.log('accuracy', this.map);
      //   this.getAddressFromCoords(this.map.center.lat(), this.map.center.lng())
      // });
      // for (let i = 0; i < location.length; i++){
      //   let marker = new google.maps.Marker({
      //     position: new google.maps.LatLng(location[i][0], location[i][1]),
      //     title: 'You are here',
      //     map: this.map,
      //     icon:{
      //       url: '../../assets/image/shipper.png',
      //       size: {
      //           width: 30,
      //           height: 40
      //        }
      //      },
      //      snippet: 'You are here',
      //   })
      // }
      let marker = new google.maps.Marker({
        title: 'You are here',
        map: this.map,
        position: latLng,
        icon:{
          url: '../../../assets/image/shipper.png',
          size: {
              width: 30,
              height: 40
           }
         },
         snippet: 'You are here',
      })
      // let location = [
      //   ["10.828232", "106.679107"],
      //   ["10.827958", "106.679623"]
      // ]
      // let marker1 = new google.maps.Marker({
      //   title: 'You are here',
      //   map: this.map,
      //   position: new google.maps.LatLng(location[0][0], location[0][1]),
      //   icon:{
      //     url: '../../assets/image/customer.png',
      //     size: {
      //         width: 30,
      //         height: 40
      //      }
      //    },
      //    snippet: 'You are here',
      // })

      // let marker2 = new google.maps.Marker({
      //   title: 'You are here',
      //   map: this.map,
      //   position: new google.maps.LatLng(location[1][0], location[1][1]),
      //   icon:{
      //     url: '../../assets/image/building.svg',
      //     size: {
      //         width: 30,
      //         height: 40
      //      }
      //    },
      //    snippet: 'You are here',
      // })
      
    }) //end geolocation get current
      .catch((error) => {
        console.log('Error getting location', error);
      });

      
  }


  //end load map

  getAddressFromCoords(lattitude, longitude) {
    console.log("getAddressFromCoords " + lattitude + " " + longitude);
    let options: NativeGeocoderOptions = {
      useLocale: true,
      maxResults: 5
    };

    this.nativeGeocoder.reverseGeocode(lattitude, longitude, options)
      .then((result: NativeGeocoderResult[]) => {
        this.address = "";
        let responseAddress = [];
        for (let [key, value] of Object.entries(result[0])) {
          if (value.length > 0)
            responseAddress.push(value);

        }
        responseAddress.reverse();
        for (let value of responseAddress) {
          this.address += value + ", ";
        }
        this.address = this.address.slice(0, -2);
      })
      .catch((error: any) => {
        this.address = "Address Not Available!";
      });

  }//end get address


  //#endregion END MAP

  callNow() {
    this.callNumber.callNumber(this.order[0].data.buyerPhone, true)
      .then(res => console.log('Launched dialer!', res))
      .catch(err => console.log('Error launching dialer', err));
  }
}
