import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccountService } from '../../services/account.service';
import { ToastController } from '@ionic/angular';
import { element } from '../../../../node_modules/protractor';
import { Storage } from '@ionic/storage';
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
  accountDetail = [];

  constructor(
    private router: Router,
    private accountService: AccountService,
    private toastCtrl: ToastController,
    private storage: StorageApiService,
  ) { 
    this.phonenumber = '222';
    this.password = 'zzz';
    this.error = '';
    
  }

  ngOnInit() {
    // this.phonenumber = 'azx';
    //this.phonenumber = '222';
    //this.password = 'zzz';
    //#region Firebase test
    /*
    //get token from firebase cloud messenger
    // this.firebase.getToken()
    //   .then(token => console.log(`The token is ${token}`)) // save the token server-side and use it to push notifications to this device
    //   .catch(error => console.error('Error getting token', error));


    // this.firebase.onNotificationOpen()
    //   .subscribe(data => {
    //     console.log(`User opened a notification ${data}`);
    //     const toast = this.toastCtrl.create({
    //       message: data.body,
    //       duration: 3000,
    //     }).then(() => toast.present());
      
    //   });

    // this.firebase.onTokenRefresh()
    //   .subscribe((token: string) => console.log(`Got a new token ${token}`));
    */
    //#endregion
  }

  async login() {
     //this.accountService.logOut();
     this.account.length = 0;
     this.accountDetail.length =0;
     console.log("truoc khi login"+this.account);
    this.accountService.sendLogin(this.phonenumber, this.password).subscribe(res => {
      console.log(this.phonenumber + "  " + this.password);
      console.log(res);
     
      //let body = res.json();  // If response is a JSON use json()
      console.log(this.account);
      this.account.push(res);
      console.log(this.account[0].data);
      console.log(this.account[0].status_code);


      if (this.account) {
        //if (role === "ROLE_MEMBER"){
        if (this.account[0].data === "ROLE_MEMBER") {
          this.error = '';
          //console.log("vo home")
          // this.storage.set("ACCOUNT", [{id: "tu_tu_roi_biet",phonenumber: this.phonenumber}])

          this.accountService.getDetailUser().subscribe(res => {
            this.accountDetail.push(res);
            console.log("---USERDETAIL HERE----")
            console.log(this.accountDetail[0].data);
            
            this.storage.set("ACCOUNT", this.accountDetail[0].data);
            console.log("-----// USERDETAIL HERE \\------")
            console.log("----- get USERDETAIL HERE")
            this.storage.get("ACCOUNT").then(value => {
              console.log(value)
              console.log("in get account")
            })
            console.log("----- // get USERDETAIL HERE")
          }, 
          () => {
            // this.storage.get("ACCOUNT").then(value => {
            //   if (value){
            //     this.storage.remove("ACCOUNT").then(() => {
                  
            //     });
            //   }
            // });
            
          });//end api get detail

          this.router.navigateByUrl("home");

        } else {
          this.error = "Wrong username or password";
        }
      } else {

      }
    }), err => {
      console.log(err);
    };


  }


  signUp() {
    this.router.navigateByUrl("registor");
  }



}
