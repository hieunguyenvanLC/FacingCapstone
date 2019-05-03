import { Component, OnInit } from '@angular/core';
import { NavController, ToastController, ModalController } from '@ionic/angular';
import { Router } from '@angular/router';
import { AccountService } from 'src/app/services/account.service';
import { LoadingService } from 'src/app/services/loading.service';
import { Camera, CameraOptions } from '@ionic-native/camera/ngx';
import { Storage } from '@ionic/storage';
import { Constant } from 'src/app/common/constant';
import { AddMemberPage } from '../add-member/add-member.page';
import { ToastHandleService } from 'src/app/services/toasthandle.service';

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

  fullName : any;
  email : any;
  cusDOB : any;



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
    private toastHandle : ToastHandleService,
  ) { 
    this.userDetail.length = 0;
    // this.fullName = "";
    // this.email = "";
    // this.cusDOB = "";
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
        if (this.userDetail[0].data.avatar !== undefined){
          this.isImg = true;
        }
        console.log(this.userDetail[0].data.name);

          this.fullName = this.userDetail[0].data.name;
          this.email = this.userDetail[0].data.email;
          if (this.userDetail[0].data.dob !== undefined || this.userDetail[0].data.dob !== null){
            this.cusDOB = new Date(this.userDetail[0].data.dob).toLocaleDateString()
          }
        
        this.isLoaded = true;
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

  async addMember(status,id,name,face,title){
    await this.modalController.create({
      animated: true,
      component: AddMemberPage,
      componentProps: {
        myMem: [{
          status: status,
          id: id,
          name: name,
          face: face,
          title: title,
        }]

      }
    }).then(modal => {
      modal.present();
    });
  } //end add member

  saveProfile(){
    console.log(this.fullName);
    console.log(this.email);
    console.log(this.cusDOB);
    if (this.cusDOB === "Invalid Date"){
      this.toastHandle.presentToast("Date of birth cannot null !");
    }else{
      let newDob = new Date(this.cusDOB).getTime();
      // let longDob = newDob.;
      this.loadingService.present("Waiting...").then(()=> {
        this.accountService.updateMemberDetail(this.fullName, this.email, newDob).subscribe(
          res => {
            let temp = [];
            temp.push(res);
            if (temp[0].message == "Success"){
              this.toastHandle.presentToast("Updated !");
              this.loadingService.dismiss();
            }
          })//end api update member detail
      })//end loading
    }//end if cusDOB
  }//end save profile
}
