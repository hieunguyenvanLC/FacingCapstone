import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccountService } from '../../services/account.service';
import { Firebase } from '@ionic-native/firebase/ngx'
import { ToastController } from '@ionic/angular';
import { element } from '../../../../node_modules/protractor';

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
    private firebase: Firebase,
    private toastCtrl : ToastController,
  ) { }

  ngOnInit() {
    // this.phonenumber = 'azx';
    this.phonenumber = '222';
    this.password = 'zzz';

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

  }

  async login() {
    this.accountService.sendLogin(this.phonenumber, this.password).subscribe(res => {
      //console.log(this.phonenumber + "  " + this.password);
      console.log(res.toString());
      //let body = res.json();  // If response is a JSON use json()
      this.account.push(res);
      console.log(this.account[0].data.length);
      let role;
      let accountID;
      
      for (let index = 0; index < this.account[0].data.length; index++) {
        const element = this.account[0].data[index];
        if (index === this.account[0].data.length -1){
          accountID = element.replace("Account ID: ", "");
        }else{
          role = element.replace("Role: ", "");
        }
        
      }
      console.log("role : " + role + "// ID :" + accountID);
      if (this.account) {
        if (role === "ROLE_MEMBER") {
          //console.log("vo home")
          this.router.navigateByUrl("home");
        }else{
          this.error = "Wrong username or password";
        }
      } else {
        
      }
    }), err => {
      console.log(err);
    };;


  }


  signUp() {
    this.router.navigateByUrl("registor");
  }



}
