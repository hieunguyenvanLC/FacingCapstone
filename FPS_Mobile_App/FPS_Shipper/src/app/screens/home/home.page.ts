import { Component, OnInit } from '@angular/core';
import { OrderService } from 'src/app/services/order.service';
import { LoadingService } from 'src/app/services/loading.service';
import { Router } from '@angular/router';
import { Camera, CameraOptions } from '@ionic-native/camera/ngx';
import { ToasthandleService } from 'src/app/services/toasthandle.service';
import { FCM } from '@ionic-native/fcm/ngx';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit {

  shipperMode = false;
  // longitudeShipper : any;
  // latitudeShipper : any;
  longitudeShp = "106.67927390539103";
  latitudeShp = "10.83767617410066";

  order = [];
  isLoading = false;
  isLoaded = false;

  myPhoto: any;
  myPhotoBinary: string;
  tokenFCM : any;

  constructor(
    private orderService: OrderService,
    private loading: LoadingService,
    private router : Router,
    private camera : Camera,
    private toastHandle : ToasthandleService,
    private fcm : FCM,
  ) {
    this.shipperMode = false;

    //firebase
    this.fcm.getToken().then(token => {
      console.log(token);
      this.tokenFCM = token;
    });
    // this.fcm.onTokenRefresh().subscribe(token => {
    //   console.log(token);
    // });
    this.fcm.onNotification().subscribe(data => {
      console.log(data);
      if (data.wasTapped) {
        console.log('Received in background');
        
        //data.order.id
        this.loading.dismiss();
        this.router.navigate(['check-out', data.order.id]);
      } else {
        console.log('Received in foreground');
        // this.router.navigate([data.landing_page, data.price]);
      }
    });// end fcm
  }

  ngOnInit() {

  }

  changeMode() {
    if (this.shipperMode) {
      console.log("onModeShipper");
      //loading 
      this.loading.present().then(() => {

        this.isLoading = true;
        //call api to auto assign order
        this.orderService.onShipMode(this.longitudeShp, this.latitudeShp, this.tokenFCM).subscribe(
          res => {
            console.log("Begin res: ");
            console.log(res);
            this.order.push(res);


            if (this.order[0].message === "Success") {
              if (this.order[0].data) {
                this.isLoaded = true;
                this.isLoading = false;
                this.loading.dismiss();
                console.log("in if success :"+this.order[0].data);
              }
            }//end if success
            else if (this.order[0].message === "time out"){
              // this.isLoaded = true;
              this.isLoading = false;
              this.loading.dismiss();
              this.shipperMode = false;
            }//end if time out
          }, () => {
            //handle finish loading
            // if (!this.isLoading) {
            //   this.loading.dismiss();
            // }
          }
        ); //end api auto assign order
      });//end loading


    }else{
      //if mode is off
      this.orderService.offShipperMode().subscribe(res => {
        console.log("in off: ");
        console.log(res);
        this.isLoaded = false;
      });//end api
    }
    console.log(this.shipperMode);
  }

  checkout(){
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
      this.orderService.checkOutOrder(this.order[0].data.id, this.myPhotoBinary).subscribe(
        res => {
          console.log(res);
          this.toastHandle.presentToast("Check out success");
        }, (err) => {
          this.toastHandle.presentToast("Check out error");
          console.log("error check out " + err);
        }
      );
    }, (err) => {
      console.log("error at takephoto :" + err)
    });
  }
}
