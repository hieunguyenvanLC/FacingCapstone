import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { AlertController } from '@ionic/angular';
import { HttpClientModule } from '@angular/common/http';
import { Camera, CameraOptions } from '@ionic-native/camera/ngx';
import { AccountService } from '../../services/account.service';
import { Account } from '../../models/account';

//import * as $ from 'jquery';

@Component({
  selector: 'app-registor',
  templateUrl: './registor.page.html',
  styleUrls: ['./registor.page.scss'],
})
export class RegistorPage implements OnInit {
  data = [];
  phoneNumber: string;
  password: string;
  fullname: string;
  ppUsername: string;
  ppPassword: string;


  registerForm: FormGroup;

  myPhoto: any;
  myPhotoBinary: string;

  resArr = [];

  file: any;

  BASE64_MARKER = ';base64,';
  array: any;

  constructor(
    public router: Router,
    public alertController: AlertController,
    public accountService: AccountService,
    private camera: Camera
  ) {
    this.password = "123";
    this.fullname = "thangdp";


  }


  ngOnInit() {
  }

  backToLogin() {
    this.router.navigateByUrl("login");
  }

  //Alert handler
  async presentAlertConfirm() {
    const alert = await this.alertController.create({
      header: 'Next step !',
      message: 'We will need take photo your face for payment !',
      // <strong>text</strong>
      buttons: [
        // {
        //   text: 'Skip',
        //   role: 'cancel',
        //   cssClass: 'secondary',
        //   handler: () => {
        //     //Create without image
        //     this.accountService.sendcreate(this.phoneNumber, this.password, this.fullname).subscribe((res: any) => {
        //       this.data = res;
        //       console.log(res);
        //     }), err => {
        //       console.log(err);
        //     };
        //     this.router.navigateByUrl('login');


        //   }
        // },
        {
          text: 'Okay',
          handler: () => {
            console.log('Confirm Okay ' + this.phoneNumber);
            //this.openCamera();
            //this.router.navigateByUrl('home');
            this.takePhoto();

            //create
            console.log(this.myPhotoBinary);
            // if (this.myPhotoBinary){
            //   this.accountService.sendcreate(this.phoneNumber, this.password, this.fullname, this.myPhotoBinary).subscribe((res: any) => {
            //     this.data.push(res);
            //     console.log(this.data[0].status_code);
            //   }), err => {
            //     console.log(err);
            //   };
            // }






          }
        }
      ]
    });

    await alert.present();
  }


  onSubmit() {
    this.phoneNumber = this.phoneNumber.replace("+", "");
    console.log(this.myPhotoBinary);
    this.accountService.sendcreate(this.phoneNumber, this.password, this.fullname, this.myPhotoBinary, this.ppUsername, this.ppPassword).subscribe((res: any) => {
      this.data.push(res);
      console.log(this.data[0].status_code);
    }), err => {
      console.log(err);
    };
  }



  takePhoto() {
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
      this.myPhotoBinary =  imageData;
      
    //   let byteNumbers = new Array(imageData.length);

    // for (var i = 0; i < imageData.length; i++) 
    //     byteNumbers[i] = imageData.charCodeAt(i);
    
    // let byteArray = new Uint8Array(byteNumbers);

    // this.myPhotoBinary  = new Blob([byteArray], {type: 'image/jpeg'});
      //this.myPhotoBinary = new Blob([imageData], { type: 'image/jpg' });
      // this.myPhotoBinary = new Blob([imageData],{type:'image/jpeg'});
      console.log(imageData);
      console.log(this.myPhotoBinary);
    }, (err) => {
      console.log("error at takephoto :" + err)
    });
  }

  // changeListener($event): void {
  //   this.file = $event.target.files[0];
  //   console.log(this.file);
  // }
}
