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

  constructor(
    private modalController: ModalController,
    private camera: Camera,
    private accountService: AccountService,
    private loading: LoadingService,
    private constant: Constant,
    private toastHandle: ToastHandleService,
    private router: Router,
    private imageResizer: ImageResizer,
  ) {
    this.faceBinary = '';
    this.name = "";
    this.face = "";
    // if (myFace[0].status)
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

      /* ------------ RESIZE IMAGE --------------- */
      let options = {
        uri: imageData,
        folderName: 'Protonet',
        quality: 90,
        width: 1280,
        height: 1280
      } as ImageResizerOptions;

      this.imageResizer
        .resize(options)
        .then((filePath) => {
          console.log('FilePath', filePath);
          this.face = 'data:image/jpeg;base64,' + filePath;
          this.faceBinary = filePath;
        })
        .catch(e => console.log(e));

      /* ------------ END RESIZE IMAGE --------------- */
      // imageData is either a base64 encoded string or a file URI
      // If it's base64 (DATA_URL):
      // this.face = 'data:image/jpeg;base64,' + imageData;
      // this.faceBinary = imageData;
      // this.myPhotoBinary = new Blob([imageData],{type:'image/jpeg'});
      //console.log(imageData);
      // console.log(this.myPhotoBinary);
    }, (err) => {
      console.log("error at takephoto :" + err)
    });
  }//end take photo

  addUpdateMemberFace() {
    let myArr = [];
    this.loading.present(this.constant.LOADINGMSG).then(() => {
      if (this.myMem[0].status === "add") {

        this.accountService.updateMemberFace('', this.name, this.faceBinary)
          .subscribe(res => {
            myArr.push(res)
            if (myArr[0].message === "Success") {
              this.loading.dismiss();
              this.modalController.dismiss();
              this.toastHandle.presentToast("Added");
              this.router.navigateByUrl("profile");
            }
          });//end api update

      }//end if add
      else {
        this.accountService.updateMemberFace(this.myMem[0].id, this.name, this.faceBinary)
          .subscribe(res => {
            myArr.push(res)
            if (myArr[0].message === "Success") {
              this.loading.dismiss();
              this.modalController.dismiss();
              this.toastHandle.presentToast("Updated");
              this.router.navigateByUrl("profile");
            }
          })//end api update
      }//end else update
    })//end loading
  }

}
