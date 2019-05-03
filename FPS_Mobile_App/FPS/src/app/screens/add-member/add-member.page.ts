import { Component, OnInit, Input } from '@angular/core';
import { NavParams, ModalController, ToastController, AlertController } from 'node_modules/@ionic/angular';
import { Camera, CameraOptions } from '@ionic-native/camera/ngx';
import { AccountService } from 'src/app/services/account.service';
import { LoadingService } from 'src/app/services/loading.service';
import { Constant } from 'src/app/common/constant';
import { ToastHandleService } from 'src/app/services/toasthandle.service';
import { Router } from '@angular/router';
import { ImageResizer, ImageResizerOptions } from '@ionic-native/image-resizer/ngx';

@Component({
  selector: 'app-add-member',
  templateUrl: './add-member.page.html',
  styleUrls: ['./add-member.page.scss'],
})
export class AddMemberPage implements OnInit {

  @Input() myMem: any;

  name: string;
  face: any;
  faceBinary: any;

  face1: any;
  face1_Binary: any;

  face2: any;
  face2_Binary: any;

  face3: any;
  face3_Binary: any;
  temp = 0;

  constructor(
    private modalController: ModalController,
    private camera: Camera,
    private accountService: AccountService,
    private loading: LoadingService,
    private constant: Constant,
    private toastHandle: ToastHandleService,
    private router: Router,
    private imageResizer: ImageResizer,
    public alertController: AlertController,
  ) {
    this.faceBinary = '';
    this.name = "";
    this.face = "";
    // if (myFace[0].status)
    this.face1 = '';
    this.face1_Binary = '';

    this.face2 = '';
    this.face2_Binary = '';

    this.face3 = '';
    this.face3_Binary = '';

    this.temp = 0;

    
  }

  ngOnInit() {
    console.log(this.myMem)
    console.log(this.myMem[0].id)
    if (this.myMem[0].status !== "add") {
      this.name = this.myMem[0].name;
      this.face = this.myMem[0].face;
      
      // this.faceBinary = this.myMem[0].face;
    } else {
      this.name = "";
      this.face = "";
      this.myMem[0].title = "New member's face";
    }
  }

  dismissModal() {
    this.modalController.dismiss();
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
      this.face = "";
      this.faceBinary = "";
      this.face = 'data:image/jpeg;base64,' + imageData;
      this.faceBinary = imageData;
    }, (err) => {
      console.log("error at takephoto :" + err)
    });
  }//end take photo

  addUpdateMemberFace() {
    let myArr = [];
    this.loading.present(this.constant.LOADINGMSG).then(() => {
      if (this.myMem[0].status === "add") {

        this.accountService.updateMemberFace('', this.name, this.face1_Binary, this.face2_Binary, this.face3_Binary)
          .subscribe(res => {
            myArr.push(res)
            if (myArr[0].message === "Success") {
              // this.loading.dismiss();
              this.modalController.dismiss();
              this.toastHandle.presentToast("Added");
              this.accountService.getDetailUser().subscribe(res => {
                this.loading.dismiss();
              })
              // this.router.navigateByUrl("profile");
            }
          });//end api update

      }//end if add
      else {
        //update front
        if (this.myMem[0].title === "Edit front side"){
          this.accountService.updateMemberFace(this.myMem[0].id, this.name, this.faceBinary, '', '')
          .subscribe(res => {
            myArr.push(res)
            if (myArr[0].message === "Success") {
              this.loading.dismiss();
              this.modalController.dismiss();
              this.toastHandle.presentToast("Updated");
              // this.router.navigateByUrl("profile");
              this.accountService.getDetailUser().subscribe(res => {
                this.loading.dismiss();
              })
            }
          })//end api update
        }//end update front
        //update left
        else if (this.myMem[0].title === "Edit left side"){
          this.accountService.updateMemberFace(this.myMem[0].id, this.name,'', this.faceBinary, '')
          .subscribe(res => {
            myArr.push(res)
            if (myArr[0].message === "Success") {
              this.loading.dismiss();
              this.modalController.dismiss();
              this.toastHandle.presentToast("Updated");
              // this.router.navigateByUrl("profile");
              this.accountService.getDetailUser().subscribe(res => {
                this.loading.dismiss();
              })
            }
          })//end api update
        }//end update left
        //update right
        else{
          this.accountService.updateMemberFace(this.myMem[0].id, this.name,'','', this.faceBinary)
          .subscribe(res => {
            myArr.push(res)
            if (myArr[0].message === "Success") {
              this.loading.dismiss();
              this.modalController.dismiss();
              this.toastHandle.presentToast("Updated");
              // this.router.navigateByUrl("profile");
              this.accountService.getDetailUser().subscribe(res => {
                this.loading.dismiss();
              })
            }
          })//end api update
        }
        //end update right
        
      }//end else update
    })//end loading
  }

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
