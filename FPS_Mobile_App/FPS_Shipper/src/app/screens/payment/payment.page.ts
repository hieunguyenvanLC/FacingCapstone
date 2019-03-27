import { Component, OnInit } from '@angular/core';
import { Camera, CameraOptions } from '@ionic-native/camera/ngx';


@Component({
  selector: 'app-payment',
  templateUrl: './payment.page.html',
  styleUrls: ['./payment.page.scss'],
})
export class PaymentPage implements OnInit {

  myPhoto: any;
  myPhotoBinary: string;

  constructor(
    private camera : Camera,
  ) { }

  ngOnInit() {
  }

  payment() {
    //use camera to take image
    this.takePhoto();
    //api for payment
  }

  takePhoto(){
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
    }, (err) => {
      console.log("error at takephoto :" + err)
    });

  }

  callAPIPaypal() {

  }
}
