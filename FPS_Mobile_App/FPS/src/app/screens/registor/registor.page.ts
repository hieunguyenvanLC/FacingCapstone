import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { AlertController } from '@ionic/angular';
import { HttpClientModule } from '@angular/common/http';
import { Camera, CameraOptions } from '@ionic-native/camera/ngx';
import { AccountService } from '../../services/account.service';
import { Account } from '../../models/account';
import { ToastHandleService } from 'src/app/services/toasthandle.service';

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
    private camera: Camera,
    private toastHandle: ToastHandleService,
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
      console.log(this.data[0].message);
      if (this.data[0].message === "Success") {
        this.toastHandle.presentToast("Create success !");
      }
      this.router.navigateByUrl("login");
    }), err => {
      this.toastHandle.presentToast("Create error !");
      console.log(err);
    };
  }



  takePhoto() {
    if (this.myPhoto) {
      console.log("photo is exist, set null");
      this.myPhoto = '';
    }
    console.log("my photo: " +this.myPhoto);
    if (this.myPhotoBinary){
      console.log("photoBinary is exist, set null");
      this.myPhotoBinary = '';
    }
    console.log("my photoBinary: " + this.myPhotoBinary);
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
      // this.myPhotoBinary = new Blob([imageData],{type:'image/jpeg'});
      //console.log(imageData);
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
