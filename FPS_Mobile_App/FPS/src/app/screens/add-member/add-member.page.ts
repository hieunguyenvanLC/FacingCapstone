import { Component, OnInit, Input } from '@angular/core';
import { NavParams, ModalController, ToastController, AlertController } from 'node_modules/@ionic/angular';
import { Camera, CameraOptions } from '@ionic-native/camera/ngx';
import { AccountService } from 'src/app/services/account.service';

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
    private accountService : AccountService,
  ) {
    this.faceBinary = '';
    // if (myFace[0].status)
   }

  ngOnInit() {
    console.log(this.myMem)
    console.log(this.myMem[0].status)
    if (this.myMem[0].status !== "add"){
      this.name = this.myMem[0].name;
      this.face = this.myMem[0].face;
    }else{
      this.name = "";
      this.face = "";
    }
  }

  dismissModal() {
    this.modalController.dismiss();
  }

  takePhoto() {
    this.face = "";
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
      this.face = 'data:image/jpeg;base64,' + imageData;
      this.faceBinary = imageData;
      // this.myPhotoBinary = new Blob([imageData],{type:'image/jpeg'});
      //console.log(imageData);
      // console.log(this.myPhotoBinary);
    }, (err) => {
      console.log("error at takephoto :" + err)
    });
  }//end take photo

  addUpdateMemberFace(){
    let myArr = [];
    if (this.myMem[0].status === "add"){
      this.accountService.updateMemberFace('', this.name, this.faceBinary)
      .subscribe(res => {
        myArr.push(res)
        if (myArr[0].message === "Success"){

        }
      });//end api update
    }//end if add
    else{

    }
  }

}
