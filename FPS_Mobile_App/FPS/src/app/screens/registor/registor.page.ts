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
  data= [];
  phoneNumber: string;
  password: string;
  fullname: string;


  registerForm: FormGroup;

  myPhoto: any;
  myPhotoBinary: any;

  resArr = [];

  constructor(
    public router: Router,
    public alertController: AlertController,
    public accountService: AccountService,
    private camera: Camera
  ) {


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
  this.accountService.sendcreate(this.phoneNumber, this.password, this.fullname, this.myPhotoBinary).subscribe((res: any) => {
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
    encodingType: this.camera.EncodingType.JPEG,
    mediaType: this.camera.MediaType.PICTURE
  }

  this.camera.getPicture(options).then((imageData) => {
    // imageData is either a base64 encoded string or a file URI
    // If it's base64 (DATA_URL):

    this.myPhoto = 'data:image/jpeg;base64,' + imageData;
    this.myPhotoBinary = new Blob([imageData], {type: 'image/png'});
    console.log(this.myPhotoBinary);
  }, (err) => {
    console.log("error at takephoto :" + err)
  });
}
}
