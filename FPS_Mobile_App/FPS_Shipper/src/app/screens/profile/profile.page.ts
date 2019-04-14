import { Component, OnInit } from '@angular/core';
import { NavController, ToastController, ModalController } from '@ionic/angular';
import { Router } from '@angular/router';
import { AccountService } from 'src/app/services/account.service';
import { LoadingService } from 'src/app/services/loading.service';
import { Camera, CameraOptions } from '@ionic-native/camera/ngx';
import { Storage } from '@ionic/storage';
import { Constant } from 'src/app/common/constant';
// import { AddMemberPage } from '../add-member/add-member.page';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {

  userDetail = [];
  status_code = 0;

  myPhoto:any;
  myPhotoBinary: any;

  isLoaded = false;
  isImg = false;

  star1 : any;
  star2 : any;
  star3 : any;
  star4 : any;
  star5 : any;
  starNum : number;
  constructor(
    public navCtrl: NavController,
    public router : Router,
    private accountService : AccountService,
    private loadingService : LoadingService,
    private toastController: ToastController,
    private camera: Camera,
    private storage : Storage,
    private constant : Constant,
    private modalController: ModalController,
  ) { 
    this.userDetail.length = 0;

    this.starNum = 0;
    this.star1 = '';
    this.star2 = '';
    this.star3 = '';
    this.star4 = '';
    this.star5 = '';
  }

  ngOnInit() {
    this.loadingService.present(this.constant.LOADINGMSG).then( () => {
      this.getUser();
    })
    
    
  }

  getUser(){
    this.accountService.getDetailUser().subscribe(
      res => {
        this.userDetail.push(res);
        console.log(this.userDetail[0].data);
        if (this.userDetail[0].data.userImage !== undefined){
          this.isImg = true;
        }
        console.log(this.userDetail[0].data.name);
        this.isLoaded = true;
        this.starNum = parseInt(this.userDetail[0].data.rating);
        switch (parseInt(this.userDetail[0].data.rating)) {
          case 1:
            this.star1 = "warning";
            break;
            case 2:
            this.star1 = "warning";
            this.star2 = "warning";
            break;
            case 3:
            this.star1 = "warning";
            this.star2 = "warning";
            this.star3 = "warning";
            break;
            case 4:
            this.star1 = "warning";
            this.star2 = "warning";
            this.star3 = "warning";
            this.star4 = "warning";
            break;
            case 5:
            this.star1 = "warning";
            this.star2 = "warning";
            this.star3 = "warning";
            this.star4 = "warning";
            this.star5 = "warning";
            break;
        
          default:
            break;
        }
        if (this.isLoaded){
          this.loadingService.dismiss();
        }
        // this.loadingService.dismiss();
      }, error => {
        console.log(error);
      }
    );
    // this.storage.get("ACCOUNT").then(value => {
    //   console.log("---Profile---")
    //   console.log(value);
    //   this.userDetail = value;
    //   if (this.userDetail.avatar !== undefined){
    //     this.isImg = true;
    //   }
    //   console.log(this.userDetail.name);
    //   this.isLoaded = true;
    //   if (this.isLoaded){
    //     this.loadingService.dismiss();
    //   }
    // })
  }

  goToHome(){
    this.router.navigateByUrl("home");
  }

  updateFace(){
    this.takePhoto();

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

      console.log(this.myPhotoBinary);
      // this.accountService.updateImageMember()
    }, (err) => {
      console.log("error at takephoto :" + err)
    });
  }//end take photo

  // async addMember(status, id,name, face){
  //   await this.modalController.create({
  //     animated: true,
  //     component: AddMemberPage,
  //     componentProps: {
  //       myMem: [{
  //         status: status,
  //         id: id,
  //         name: name,
  //         face: face,
  //       }]

  //     }
  //   }).then(modal => {
  //     modal.present();
  //   });
  // }
}
