import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccountService } from '../../services/account.service';
import { ToastController } from '@ionic/angular';
import { element } from '../../../../node_modules/protractor';
import { Storage } from '@ionic/storage';
import { StorageApiService } from 'src/app/services/storage-api.service';
import { GoogleApiService } from 'src/app/services/google-api.service';
import { async } from 'q';
import { getHostElement } from '@angular/core/src/render3';
// import { setTimeout } from 'timers';

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
  // accountDetail = [];

  constructor(
    private router: Router,
     private  accountService: AccountService,
    private toastCtrl: ToastController,
   private storage: StorageApiService,
    private googleAPI: GoogleApiService
  ) { 
    this.phonenumber = '222';
    this.password = 'zzz';
    this.error = '';
    
    
  }

  async ngOnInit() {

    setTimeout(()=>{
       this.googleAPI.getCurrentLocation();
       console.log(this.googleAPI.getCurrentLocation());
      this.getStorage();
      
    },300) 
    
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
    
    
    
    await this.storage;
     //this.accountService.logOut();
   this.account.length = 0;
    //  this.accountDetail.length =0;
    //  console.log("truoc khi login"+this.account);
     this.accountService.sendLogin(this.phonenumber, this.password).subscribe(res => {
      console.log(this.phonenumber + "  " + this.password);
      console.log(res);
     
      //let body = res.json();  // If response is a JSON use json()
  
      this.account.push(res);

       if (this.account) {
        //if (role === "ROLE_MEMBER"){
        if (this.account[0].data === "ROLE_MEMBER") {
          this.error = '';

          
             this.getDetailAccount().then(value => {
               console.log("value tra ve")
               console.log(value);
               
                setTimeout(()=>{
                  
                  this.getStorage();
                  
                },1300) 
               
               
                
             });
            //end api get detail
             
          
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

  async getStorage(){
      await console.log("----- get Storage here---------------")
      this.storage.get("ACCOUNT").then(value => {
               console.log(value)
               console.log("in get account")
               this.router.navigateByUrl("home");
             }).catch(Err => {
                 console.log(Err);
             });
             
           
  }


  async getDetailAccount(){
    let result = "Failed";
    let userAccountDetail = [];
    await this.accountService.getDetailUser().subscribe(res => {
     
      userAccountDetail.push(res);
      console.log(userAccountDetail);
      
      console.log("---Get detail account here----")
     
      console.log(userAccountDetail[0].data);

      

    }, () => {

    });

     setTimeout(()=>{
         this.storage.set("ACCOUNT", userAccountDetail[0].data).then(()=>{
        console.log("------------Truoc khi tra result------------------")
        console.log(result);
        console.log("------------Sau khi tra result------------------")
         result = "Success";

         console.log(result);
      });
    },500);
    
     return await result;
  }

}
