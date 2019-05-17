import { Component, OnInit } from '@angular/core';
import { NavController, ToastController, ModalController, ActionSheetController, AlertController } from '@ionic/angular';
import { Router } from '@angular/router';
import { AccountService } from 'src/app/services/account.service';
import { LoadingService } from 'src/app/services/loading.service';
import { Camera, CameraOptions } from '@ionic-native/camera/ngx';
import { Storage } from '@ionic/storage';
import { Constant } from 'src/app/common/constant';
import { AddMemberPage } from '../add-member/add-member.page';
import { ToastHandleService } from 'src/app/services/toasthandle.service';
import { ActionSheetHandleService } from 'src/app/services/action-sheet-handle.service';
import { AppModule } from 'src/app/app.module';
import { AppComponent } from 'src/app/app.component';
import { PayPal, PayPalPayment, PayPalConfiguration } from '@ionic-native/paypal/ngx';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage {

  userDetail = [];
  status_code = 0;

  myPhoto: any;
  myPhotoBinary: any;

  isLoaded = false;
  isImg = false;

  fullName: any;
  email: any;
  cusDOB: any;

  newMoney: any;

  constructor(
    public navCtrl: NavController,
    public router: Router,
    private accountService: AccountService,
    private loadingService: LoadingService,
    private toastController: ToastController,
    private camera: Camera,
    private storage: Storage,
    private constant: Constant,
    private modalController: ModalController,
    private toastHandle: ToastHandleService,
    private actionSheetHandle: ActionSheetHandleService,
    private appComponent: AppComponent,
    public actionSheetController: ActionSheetController,
    private payPal: PayPal,
    private loading: LoadingService,
    private alertController: AlertController,
  ) {
    this.userDetail.length = 0;
    // this.fullName = "";
    // this.email = "";
    // this.cusDOB = "";
    this.newMoney = '';

  }

  // ngOnInit() {
  //   this.loadingService.present(this.constant.LOADINGMSG).then(() => {
  //     this.getUser();
  //   })


  // }

  async ionViewDidEnter() {
    this.loadingService.present(this.constant.LOADINGMSG).then(() => {
      this.getUser();
    })
  }

  getUser() {
    this.accountService.getDetailUser().subscribe(
      res => {
        this.userDetail.push(res);
        console.log(this.userDetail[0].data);
        if (this.userDetail[0].data.avatar !== undefined) {
          this.isImg = true;
        }
        console.log(this.userDetail[0].data.name);

        this.fullName = this.userDetail[0].data.name;
        this.email = this.userDetail[0].data.email;
        if (this.userDetail[0].data.dob !== undefined || this.userDetail[0].data.dob !== null) {
          this.cusDOB = new Date(this.userDetail[0].data.dob).toLocaleDateString()
        }

        this.isLoaded = true;
        if (this.isLoaded) {
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

  goToHome() {
    this.router.navigateByUrl("home");
  }

  takePhoto(num) {
    //1 is take from libary
    //2 is take from camera
    let options: CameraOptions = {};
    if (num == 1){
      options = {
        quality: 100,
        destinationType: this.camera.DestinationType.DATA_URL,
        sourceType: this.camera.PictureSourceType.PHOTOLIBRARY,
        encodingType: this.camera.EncodingType.JPEG,
        mediaType: this.camera.MediaType.PICTURE,
        saveToPhotoAlbum: false,
        correctOrientation: true,
      }
    }//end if num = 1
    else{
      options = {
        quality: 100,
        destinationType: this.camera.DestinationType.DATA_URL,
        encodingType: this.camera.EncodingType.JPEG,
        mediaType: this.camera.MediaType.PICTURE,
        saveToPhotoAlbum: false,
        correctOrientation: true,
      }
    }//end else
    // const options: CameraOptions = {
    //   quality: 100,
    //   destinationType: this.camera.DestinationType.DATA_URL,
    //   sourceType: this.camera.PictureSourceType.PHOTOLIBRARY,
    //   encodingType: this.camera.EncodingType.JPEG,
    //   mediaType: this.camera.MediaType.PICTURE,
    //   saveToPhotoAlbum: false,
    //   correctOrientation: true,
    // }

    this.camera.getPicture(options).then((imageData) => {
      // imageData is either a base64 encoded string or a file URI
      // If it's base64 (DATA_URL):
      this.myPhoto = 'data:image/jpeg;base64,' + imageData;
      this.myPhotoBinary = imageData;

      console.log(this.myPhotoBinary);
      this.loading.present('Loading...').then(() =>{
        this.accountService.updateAvatar(this.myPhotoBinary).subscribe(res => {
          console.log(res);
          this.updateAvatar();
          this.loading.dismiss();
        })//end update avatar
      })//end loading
      
      // this.accountService.updateImageMember()
    }, (err) => {
      console.log("error at takephoto :" + err)
    });
  }//end take photo

  async addMember(status, id, name, face, title) {
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

  saveProfile() {
    console.log(this.fullName);
    console.log(this.email);
    console.log(this.cusDOB);
    if (this.cusDOB === "Invalid Date") {
      this.toastHandle.presentToast("Date of birth cannot null !");
    } else {
      let newDob = new Date(this.cusDOB).getTime();
      // let longDob = newDob.;
      this.loadingService.present("Waiting...").then(() => {
        this.accountService.updateMemberDetail(this.fullName, this.email, newDob).subscribe(
          res => {
            let temp = [];
            temp.push(res);
            if (temp[0].message == "Success") {
              this.toastHandle.presentToast("Updated !");
              this.loadingService.dismiss();
            }
          })//end api update member detail
      })//end loading
    }//end if cusDOB
  }//end save profile

  async addMoney() {
    let USD = 23255.814
    let money_USD = (this.newMoney / USD).toFixed(2);
    console.log(money_USD);
    await this.paymentActionSheet(this.newMoney, money_USD, '', this.userDetail[0].data.phone)
    
  }

  async presentEditAvaActionSheet() {
    const actionSheet = await this.actionSheetController.create({
      header: 'Edit avatar',
      buttons: [{
        text: 'Photo from library',
        role: 'destructive',
        icon: 'photos',
        handler: () => {
          this.takePhoto(1);
        }
      },
      {
        text: 'Take picture',
        icon: 'aperture',
        handler: () => {
          this.takePhoto(2);
        }
      },
      {
        text: 'Cancel',
        icon: 'close',
        role: 'cancel',
        handler: () => {
          console.log('Cancel clicked');
        }
      }]
    });
    await actionSheet.present();
  }

  async updateWallet(){
    await this.accountService.getAvatar().subscribe(res => {
      let tempArr = [];
      tempArr.push(res)
      this.userDetail[0].data.wallet = tempArr[0].data.wallet;
      this.newMoney = '';
      // this.loadingService.dismiss();
    })//end api get ava
  }

  async updateAvatar(){
    await this.accountService.getAvatar().subscribe(res => {
      let tempArr = [];
      tempArr.push(res)
      this.userDetail[0].data.avatar = tempArr[0].data.avatar;
      // this.newMoney = '';
      // this.loadingService.dismiss();
    })//end api get ava
  }

  async paymentActionSheet(money_VND, money_USD, description, userAccount) {
    let formatMoney_VND = this.appComponent.formatNumber(money_VND);
    const actionSheet = await this.actionSheetController.create({
      header: 'Choose your card',
      buttons: [{
        text: `Paypal`,
        role: 'destructive',
        icon: '',
        cssClass: '',
        handler: () => {
          this.presentAlertWithMsg('', formatMoney_VND + ' VND will be exchanged to ' + money_USD + ' $ for this payment !', money_VND, money_USD, description, userAccount)

        }//end handle
      },
      {
        text: 'Cancel',
        icon: 'close',
        role: 'cancel',
        handler: () => {
          console.log('Cancel clicked');
        }
      }
      ]
    });
    await actionSheet.present();
  }

  async dismiss() {
    return this.actionSheetController.dismiss();
  }

  async presentAlertWithMsg(header, msg, money_VND, money_USD, description, userAccount) {
    const alert = await this.alertController.create({
      header: header,
      message: msg,
      buttons: [
        {
          text: 'OK',
          handler: () => {
            this.payPal.init({
              PayPalEnvironmentProduction: 'YOUR_PRODUCTION_CLIENT_ID',
              PayPalEnvironmentSandbox: this.constant.PAYPAL_SANDBOX_KEY,
            }).then(() => {
              // Environments: PayPalEnvironmentNoNetwork, PayPalEnvironmentSandbox, PayPalEnvironmentProduction
              this.payPal.prepareToRender('PayPalEnvironmentSandbox', new PayPalConfiguration({
                // Only needed if you get an "Internal Service Error" after PayPal login!
                //payPalShippingAddressOption: 2 // PayPalShippingAddressOptionPayPal
              })).then(() => {
                let payment = new PayPalPayment(money_USD, 'USD', 'Add ' + money_USD + ' $ to ' + userAccount + ' wallet', 'sale');
                this.payPal.renderSinglePaymentUI(payment).then((res) => {
                  // Successfully paid
                  console.log(res);
                  let tempArr = [];
                  tempArr.push(res.response);
                  let orderID = tempArr[0].id;
                  console.log(orderID);
                  // console.log(res);
                  this.loading.present("Loading...").then(() => {
                    this.accountService.depositToWallet(money_VND).subscribe(res => {
                      console.log(res)
                      this.updateWallet();
                      this.loading.dismiss();
                    })//end api save money
                  })//end load ding
                  // Example sandbox response
                  //
                  // {
                  //   "client": {
                  //     "environment": "sandbox",
                  //     "product_name": "PayPal iOS SDK",
                  //     "paypal_sdk_version": "2.16.0",
                  //     "platform": "iOS"
                  //   },
                  //   "response_type": "payment",
                  //   "response": {
                  //     "id": "PAY-1AB23456CD789012EF34GHIJ",
                  //     "state": "approved",
                  //     "create_time": "2016-10-03T13:33:33Z",
                  //     "intent": "sale"
                  //   }
                  // }
                }, () => {
                  // Error or render dialog closed without being successful
                });
              }, () => {
                // Error in configuration
              });
            }, () => {
              // Error in initialization, maybe PayPal isn't supported or something else
            });//end paypal
          }
        },
        {
          text: 'Cancel',
          role: 'cancel',
          cssClass: 'secondary',
          handler: (blah) => {
          }
        },
      ]
    });

    await alert.present();
  }
}
