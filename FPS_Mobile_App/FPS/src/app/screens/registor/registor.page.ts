import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { AlertController } from '@ionic/angular';
import { HttpClientModule } from '@angular/common/http';
import { Camera, CameraOptions } from '@ionic-native/camera/ngx';
import { AccountService } from '../../services/account.service';
import { Account } from '../../models/account';
import { ToastHandleService } from 'src/app/services/toasthandle.service';
import { LoadingService } from 'src/app/services/loading.service';
import { AlertService } from 'src/app/services/alert.service';

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

  face1: any;
  face1_Binary: any;

  face2: any;
  face2_Binary: any;

  face3: any;
  face3_Binary: any;

  resArr = [];

  file: any;
  temp = 0;
  BASE64_MARKER = ';base64,';
  array: any;

  constructor(
    public router: Router,
    public alertController: AlertController,
    public accountService: AccountService,
    private camera: Camera,
    private toastHandle: ToastHandleService,
    private loading : LoadingService,
    private alertService : AlertService,
  ) {
    this.password = "";
    this.fullname = "";

    this.face1 = '';
    this.face1_Binary = '';

    this.face2 = '';
    this.face2_Binary = '';

    this.face3 = '';
    this.face3_Binary = '';

    this.temp = 0;
  }


  ngOnInit() {
  }

  backToLogin() {
    this.router.navigateByUrl("login");
  }



  onSubmit() {
    this.phoneNumber = this.phoneNumber.replace("+", "");
    // console.log(this.myPhotoBinary);
    this.loading.present('Creating...').then( () => {
      this.accountService.sendcreate(this.phoneNumber, this.password, this.fullname, this.face1_Binary, this.face2_Binary, this.face3_Binary).subscribe((res: any) => {
        console.log("face 3 Binary *****************");
        console.log(this.face3_Binary);
        console.log("face 3 Binary ******************");
        this.data.push(res);
        console.log(this.data[0].message);
        if (this.data[0].message === "Success") {
          this.toastHandle.presentToast("Create success !");
          this.loading.dismiss();
        }
        this.router.navigateByUrl("login");
        this.alertService.presentAlertWithMsg("Notification", "Your account is verifying");
      }), err => {
        this.loading.dismiss();
        this.toastHandle.presentToast("Create error !");
        console.log(err);
      };
    }
      
    )//end loading
  }





  // changeListener($event): void {
  //   this.file = $event.target.files[0];
  //   console.log(this.file);
  // }

  //#region ********* FACE 1

  async presentAlertConfirm() {
    const alert = await this.alertController.create({
      header: 'Step 1',
      message: 'We will need take photo your <strong>front face</strong> for payment !',
      // <strong>text</strong>
      buttons: [
        {
          text: 'Okay',
          handler: async () => {

            console.log('Confirm Okay ' + this.temp);
            await this.takePhoto_1();
            await this.loading.presentWithtime('Loading...', 3*1000);
            await setTimeout( () => this.presentAlertConfirm_2(), 3000);
            // await this.presentAlertConfirm_2();
          }
        }
      ]
    });
    await alert.present();
    // await this.presentAlertConfirm_2();
  }//end alert 1

  takePhoto_1() {
    if (this.face1) {
      console.log("photo is exist, set null");
      this.face1 = '';
    }
    if (this.face1_Binary) {
      console.log("photoBinary is exist, set null");
      this.face1_Binary = '';
    }
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
      this.face1 = 'data:image/jpeg;base64,' + imageData;
      this.face1_Binary = imageData;
      this.temp = 1;
    }, (err) => {
      console.log("error at takephoto front :" + err)
    });

  }//end take photo

  //#endregion face 1

  //#region ********* FACE 2
  async presentAlertConfirm_2() {
    const alert = await this.alertController.create({
      header: 'Step 2',
      message: 'We will need take photo your <strong>left face</strong> for payment !',
      // <strong>text</strong>
      buttons: [
        {
          text: 'Okay',
          handler: async () => {

            console.log('Confirm Okay ' + this.temp);
            await this.takePhoto_2();
            await this.loading.presentWithtime('Loading...', 3*1000);
            await setTimeout( () => this.presentAlertConfirm_3(), 3000);
            // await this.presentAlertConfirm_3();
            //create
            // console.log(this.myPhotoBinary);
          }
        }
      ]
    });
    await alert.present();
  }//end alert 1

  takePhoto_2() {
    if (this.face2) {
      console.log("photo 2 is exist, set null");
      this.face1 = '';
    }
    if (this.face2_Binary) {
      console.log("photoBinary 2 is exist, set null");
      this.face2_Binary = '';
    }
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
      this.face2 = 'data:image/jpeg;base64,' + imageData;
      this.face2_Binary = imageData;
      this.temp = 2;
    }, (err) => {
      console.log("error at takephoto front :" + err)
    });
  }//end take photo
  //#endregion face 2

  //#region ************* FACE 3
  async presentAlertConfirm_3() {
    const alert = await this.alertController.create({
      header: 'Step 3',
      message: 'We will need take photo your <strong>right face</strong> for payment !',
      // <strong>text</strong>
      buttons: [
        {
          text: 'Okay',
          handler: () => {

            console.log('Confirm Okay ' + this.temp);
            this.takePhoto_3();
            

            //create
            // console.log(this.myPhotoBinary);
          }
        }
      ]
    });
    await alert.present();
  }//end alert 1

  takePhoto_3() {
    if (this.face3) {
      console.log("photo 3 is exist, set null");
      this.face3 = '';
    }
    if (this.face3_Binary) {
      console.log("photoBinary 3 is exist, set null");
      this.face3_Binary = '';
    }
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
      this.face3 = 'data:image/jpeg;base64,' + imageData;
      this.face3_Binary = imageData;
    }, (err) => {
      console.log("error at takephoto front :" + err)
    });
  }//end take photo
  //#endregion face 3

  //Edit face
  TakePhotoForEdit(side){
    if (side == "front"){
      this.face1 = '';
      this.face1_Binary = '';
    }else if (side == "left"){
      this.face2 = '';
      this.face2_Binary = '';
    }else if (side == "right"){
      this.face3 = '';
      this.face3_Binary = '';
    }

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
      if (side == "front"){
        this.face1 = 'data:image/jpeg;base64,' + imageData;
        this.face1_Binary = imageData;
      }else if (side == "left"){
        this.face2 = 'data:image/jpeg;base64,' + imageData;
        this.face2_Binary = imageData;
      }else if (side == "right"){
        this.face3 = 'data:image/jpeg;base64,' + imageData;
        this.face3_Binary = imageData;
      }
    }, (err) => {
      console.log("error at takephoto front :" + err)
    });
  }
}
