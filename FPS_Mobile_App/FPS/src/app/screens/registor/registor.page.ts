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
data :any;
  phoneNumber: string;
  password: string;
  fullname: string;


  registerForm: FormGroup;

  constructor(
    public router: Router,
    public alertController: AlertController,
    public accountService : AccountService,
    //private camera: Camera
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
        {
          text: 'Skip',
          role: 'cancel',
          cssClass: 'secondary',
          handler: () => {
            //Create without image

            this.router.navigateByUrl('login');


          }
        }, {
          text: 'Okay',
          handler: () => {
            console.log('Confirm Okay ' + this.phoneNumber);
            //this.openCamera();
            //this.router.navigateByUrl('home');
          }
        }
      ]
    });

    await alert.present();
  }


  onSubmit(){
    this.accountService.sendcreate(this.phoneNumber, this.password, this.fullname).subscribe((res:any) => {
      this.data = res;
      console.log(res);
      }),err=>{
    console.log(err);
    };
    }
}
