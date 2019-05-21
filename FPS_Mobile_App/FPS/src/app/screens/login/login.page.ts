import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccountService } from '../../services/account.service';
import { ToastController, AlertController } from '@ionic/angular';
import { element } from '../../../../node_modules/protractor';
import { Storage } from '@ionic/storage';
import { StorageApiService } from 'src/app/services/storage-api.service';
import { GoogleApiService } from 'src/app/services/google-api.service';
import { async } from 'q';
import { getHostElement } from '@angular/core/src/render3';
import { LoadingService } from 'src/app/services/loading.service';
import { ToastHandleService } from 'src/app/services/toasthandle.service';
// import { setTimeout } from 'timers';
import { CallNumber } from '@ionic-native/call-number/ngx';


@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  phonenumber: string;
  password: string;
  error: string;
  account = [];
  temp: any;
  // accountDetail = [];

  constructor(
    private router: Router,
    private accountService: AccountService,
    private toastHandle: ToastHandleService,
    private storage: StorageApiService,
    private googleAPI: GoogleApiService,
    private loading: LoadingService,
    private callNumber: CallNumber,
  ) {
    this.phonenumber = '84965142724';
    // this.phonenumber = '84932081194';
    this.password = 'zzz';
    this.error = '';

    this.temp = 1131;
  }

  async ngOnInit() {
    this.googleAPI.getCurrentLocation();
    console.log(this.googleAPI.getCurrentLocation());
  }

  async login() {
    const sleep = (milliseconds) => {
      return new Promise(resolve => setTimeout(resolve, milliseconds))
    }
    await this.storage;
    //this.accountService.logOut();
    this.account.length = 0;
    this.loading.present("Waiting...").then(() => {
      this.accountService.sendLogin(this.phonenumber, this.password).subscribe(res => {
        // console.log(this.phonenumber + "  " + this.password);
        // console.log(res);

        //let body = res.json();  // If response is a JSON use json()

        this.account.push(res);

        if (this.account) {
          //if (role === "ROLE_MEMBER"){
          if (this.account[0].data === "ROLE_MEMBER") {
            this.error = '';
            this.getDetailAccount();
            sleep(1000).then(() => {
              //get storage
              this.getStorage();
            })

            this.loading.dismiss();
            //end api get detail
          } else {
            this.loading.dismiss();
            this.error = "Wrong username or password";
          }
        } else {

        }
      }), //end api login
        err => {
          this.loading.dismiss()
          this.toastHandle.presentToast("Error connection! Please check your connection");
          console.log(err);
        };
    })//end loading
  }


  signUp() {
    this.router.navigateByUrl("registor");
  }

  async getStorage() {
    // await console.log("----- get Storage here---------------")
    this.storage.get("ACCOUNT").then(value => {
      //  console.log(value)
      //  console.log("in get account")
      this.router.navigateByUrl("home");
    }).catch(Err => {
      console.log(Err);
    });


  }


  async getDetailAccount() {
    let result = "Failed";
    let userAccountDetail = [];
    await this.accountService.getDetailUser().subscribe(res => {

      userAccountDetail.push(res);
      this.storage.set("ACCOUNT", userAccountDetail[0].data).then(() => {
        result = "Success";

        //  console.log(result);
      });

    }, () => {

    });





    return await result;
  }

}
