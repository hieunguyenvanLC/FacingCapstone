import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'
import { FormGroup, FormControl, Validators, FormBuilder } from '../../../node_modules/@angular/forms';
import { AlertController } from '@ionic/angular';
import { Camera, CameraOptions } from '@ionic-native/camera/ngx';

@Component({
  selector: 'app-registor',
  templateUrl: './registor.page.html',
  styleUrls: ['./registor.page.scss'],
})
export class RegistorPage implements OnInit {

  phoneNumber : number;

  

  
  constructor(
    public router: Router,
    public alertController: AlertController,
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
          handler: (blah) => {
            console.log('Confirm Cancel: blah');
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

  //end Aler handler

  //Open Camera
  // openCamera(){
  //   const options: CameraOptions = {
  //     quality: 100,
  //     destinationType: this.camera.DestinationType.FILE_URI,
  //     encodingType: this.camera.EncodingType.JPEG,
  //     mediaType: this.camera.MediaType.PICTURE
  //   }

  //   this.camera.getPicture(options).then((imageData) => {
  //     // imageData is either a base64 encoded string or a file URI
  //     // If it's base64 (DATA_URL):
  //     let base64Image = 'data:image/jpeg;base64,' + imageData;
  //    }, (err) => {
  //     // Handle error
  //    });
  // }
  
  //end open camera

}
