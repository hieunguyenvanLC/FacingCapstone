import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { OrderService } from 'src/app/services/order.service';
import { LoadingService } from 'src/app/services/loading.service';
import { Router } from '@angular/router';
import { Camera, CameraOptions } from '@ionic-native/camera/ngx';
import { ToasthandleService } from 'src/app/services/toasthandle.service';
import { FCM } from '@ionic-native/fcm/ngx';
import { Geolocation } from '@ionic-native/geolocation/ngx';
import { NativeGeocoder, NativeGeocoderReverseResult, NativeGeocoderOptions } from '@ionic-native/native-geocoder/ngx';
import { Marker, GoogleMapsAnimation } from '@ionic-native/google-maps/ngx';

declare var google;
@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit {
  @ViewChild('map') mapElement: ElementRef;
  map: any;
  address: string;

  shipperMode = false;
  stateOrder: number;
  //0 -> finding
  //1 -> finded
  // longitudeShipper : any;
  // latitudeShipper : any;
  longitudeShp = "106.67927390539103";
  latitudeShp = "10.83767617410066";

  order = [];
  isLoading = false;
  isLoaded = false;

  myPhoto: any;
  myPhotoBinary: string;
  tokenFCM: any;



  constructor(
    private orderService: OrderService,
    private loading: LoadingService,
    private router: Router,
    private camera: Camera,
    private toastHandle: ToasthandleService,
    private fcm: FCM,
    private geolocation: Geolocation,
    private nativeGeocoder: NativeGeocoder,
  ) {
    this.shipperMode = false;
    this.stateOrder = 0;

    //firebase
    this.fcm.getToken().then(token => {
      console.log(token);
      this.tokenFCM = token;
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
  }

  ngOnInit() {
    this.loadMap();
  }

  changeMode() {
    if (this.shipperMode) {
      console.log("onModeShipper");
      this.findOrder();
      this.toastHandle.presentToast("Finding order");
      //loading 
      // this.loading.present("Finding order...").then(() => {

      //   this.isLoading = true;
      //   //call api to auto assign order
      //   this.orderService.onShipMode(this.longitudeShp, this.latitudeShp, this.tokenFCM).subscribe(
      //     res => {
      //       console.log("Begin res: ");
      //       console.log(res);
      //       this.order.push(res);


      //       if (this.order[0].message === "Success") {
      //         if (this.order[0].data) {
      //           this.isLoaded = true;
      //           this.isLoading = false;
      //           this.loading.dismiss();
      //           console.log("in if success :"+this.order[0].data);
      //         }
      //       }//end if success
      //       else if (this.order[0].message === "time out"){
      //         // this.isLoaded = true;
      //         this.isLoading = false;
      //         this.loading.dismiss();
      //         this.shipperMode = false;
      //       }//end if time out
      //     }, () => {
      //       //handle finish loading
      //     }
      //   ); //end api auto assign order
      // });//end loading


    } else {
      //if mode is off
      this.orderService.offShipperMode().subscribe(res => {
        console.log("in off: ");
        console.log(res);
        this.isLoaded = false;
      });//end api
      this.stateOrder = 0;
      this.toastHandle.presentToast("Shipper mode is off !");
    }
    console.log(this.shipperMode);
  }

  findOrder() {
    this.stateOrder = 1;
    //call api to auto assign order
    this.orderService.onShipMode(this.longitudeShp, this.latitudeShp, this.tokenFCM).subscribe(
      res => {
        console.log("Begin res: ");
        console.log(res);
        this.order.push(res);


        if (this.order[0].message === "Success") {
          if (this.order[0].data) {
            this.isLoaded = true;
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
  }//end find order

  cancelFindOrder(){
    this.orderService.offShipperMode().subscribe(res => {
      console.log("in off: ");
      console.log(res);
      // this.isLoaded = false;
    });//end api
    this.stateOrder = 0;
  }

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
      this.myPhoto = 'data:image/jpeg;base64,' + imageData;
      this.myPhotoBinary = imageData;
      //this.myPhotoBinary = new Blob([imageData], { type: 'image/jpg' });
      // this.myPhotoBinary = new Blob([imageData],{type:'image/jpeg'});
      console.log(imageData);
      console.log(this.myPhotoBinary);

      //call api
      this.loading.present("Waiting for payment...").then(() => {
        this.orderService.checkOutOrder(this.order[0].data.id, this.myPhotoBinary).subscribe(
          res => {
            console.log(res);
            this.loading.dismiss();
            this.toastHandle.presentToast("Check out success");

          }, (err) => {
            this.loading.dismiss();
            this.toastHandle.presentToast("Check out error");
            console.log("error check out " + err);
          }
        );//end api check out
      })

    }, (err) => {
      console.log("error at takephoto :" + err)
    });
  }

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
        zoom: 18,
        tilt: 30,
        mapTypeId: google.maps.MapTypeId.ROADMAP
      }

      this.getAddressFromCoords(resp.coords.latitude, resp.coords.longitude);

      this.map = new google.maps.Map(this.mapElement.nativeElement, mapOptions);

      this.map.addListener('tilesloaded', () => {
        console.log('accuracy', this.map);
        this.getAddressFromCoords(this.map.center.lat(), this.map.center.lng())
      });

      let marker: google.maps.Marker = new google.maps.Marker({
        title: 'You are here',
        map: this.map,
        position: latLng,

      })





    }) //end geolocation get current
      .catch((error) => {
        console.log('Error getting location', error);
      });
  }//end load map

  getAddressFromCoords(lattitude, longitude) {
    console.log("getAddressFromCoords " + lattitude + " " + longitude);
    let options: NativeGeocoderOptions = {
      useLocale: true,
      maxResults: 5
    };

    this.nativeGeocoder.reverseGeocode(lattitude, longitude, options)
      .then((result: NativeGeocoderReverseResult[]) => {
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
}
