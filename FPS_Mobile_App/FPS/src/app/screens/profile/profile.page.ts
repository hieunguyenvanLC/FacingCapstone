import { Component, OnInit } from '@angular/core';
import { NavController, ToastController } from '@ionic/angular';
import { Router } from '@angular/router';
import { AccountService } from 'src/app/services/account.service';
import { LoadingService } from 'src/app/services/loading.service';
import { Camera, CameraOptions } from '@ionic-native/camera/ngx';
import { Storage } from '@ionic/storage';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {

  userDetail : any;
  status_code = 0;

  myPhoto:any;
  myPhotoBinary: any;

  isLoaded = false;
  isImg = false;

  constructor(
    public navCtrl: NavController,
    public router : Router,
    private accountService : AccountService,
    private loadingService : LoadingService,
    private toastController: ToastController,
    private camera: Camera,
    private storage : Storage,
  ) { }

  ngOnInit() {
    this.loadingService.present().then( () => {
      this.getUser();
    })
    
    
  }

  getUser(){
    // this.accountService.getDetailUser().subscribe(
    //   res => {
    //     this.userDetail.push(res);
    //     console.log(this.userDetail[0].data);
    //     this.status_code = this.userDetail[0].status_code;

    //   }, error => {
    //     console.log(error);
    //   },
    //   () => {
    //     if (this.status_code === 1){
    //       this.loadingService.dismiss();
    //     }else{
    //       //handle error
    //     }
    //   }
    // );
    this.storage.get("ACCOUNT").then(value => {
      console.log("---Profile---")
      console.log(value);
      this.userDetail = value;
      if (this.userDetail.avatar !== undefined){
        this.isImg = true;
      }
      console.log(this.userDetail.name);
      this.isLoaded = true;
      if (this.isLoaded){
        this.loadingService.dismiss();
      }
    })
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
  }
}
