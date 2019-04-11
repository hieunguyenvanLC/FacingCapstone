import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AccountService } from '../../services/account.service';
import { LoadingService } from 'src/app/services/loading.service';
import { ToasthandleService } from 'src/app/services/toasthandle.service';
import { StorageApiService } from 'src/app/services/storage-api.service';


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

  constructor(
    private router: Router,
    private accountService: AccountService,
    private loading : LoadingService,
    private toastHandle: ToasthandleService,
    private storage: StorageApiService,
  ) { 
    this.phonenumber = '84098734455';
    this.password = 'zzz';
  }

  async ngOnInit() {
    // this.googleAPI.getCurrentLocation();
    // console.log(this.googleAPI.getCurrentLocation());
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
