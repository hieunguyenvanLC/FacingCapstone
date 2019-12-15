import { Component, OnInit, QueryList, ViewChildren } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AccountService } from '../../services/account.service';
import { LoadingService } from 'src/app/services/loading.service';
import { ToasthandleService } from 'src/app/services/toasthandle.service';
import { StorageApiService } from 'src/app/services/storage-api.service';
import { IonRouterOutlet, MenuController, Platform } from '@ionic/angular';


@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  @ViewChildren(IonRouterOutlet) routerOutlets: QueryList<IonRouterOutlet>;

  lastTimeBackPress = 0;
  timePeriodToExit = 2000;
    
  phonenumber: string;
  password: string;
  error: string;

  account = [];

  constructor(
    private menu: MenuController,
    private platform: Platform,
    private router: Router,
    private accountService: AccountService,
    private loading : LoadingService,
    private toastHandle: ToasthandleService,
    private storage: StorageApiService,
  ) { 
    this.phonenumber = '84965142724';
    // this.phonenumber = '84098734455';
    this.password = 'zzz';

    this.backButtonEvent();
  }

  async ngOnInit() {
    // this.googleAPI.getCurrentLocation();
    // console.log(this.googleAPI.getCurrentLocation());
  }

  async login() {
    // const sleep = (milliseconds) => {
    //   return new Promise(resolve => setTimeout(resolve, milliseconds))
    // }
    //await this.storage;
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
        if (this.account[0].data === "ROLE_SHIPPER") {
          this.error = '';
          // this.getDetailAccount();
          // sleep(1000).then(() => {
            //get storage
            // this.getStorage();
          // })
          this.router.navigateByUrl('home');
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

  backButtonEvent() {
    this.platform.backButton.subscribe(async () => {
        // // close action sheet
        // try {
        //     const element = await this.actionSheetCtrl.getTop();
        //     if (element) {
        //         element.dismiss();
        //         return;
        //     }
        // } catch (error) {
        // }

        // // close popover
        // try {
        //     const element = await this.popoverCtrl.getTop();
        //     if (element) {
        //         element.dismiss();
        //         return;
        //     }
        // } catch (error) {
        // }

        // // close modal
        // try {
        //     const element = await this.modalCtrl.getTop();
        //     if (element) {
        //         element.dismiss();
        //         return;
        //     }
        // } catch (error) {
        //     console.log(error);

        // }

        // // close side menua
        // try {
        //     const element = await this.menu.getOpen();
        //     if (element !== null) {
        //         this.menu.close();
        //         return;

        //     }

        // } catch (error) {

        // }

        this.routerOutlets.forEach((outlet: IonRouterOutlet) => {
            if (outlet && outlet.canGoBack()) {
                outlet.pop();

            } else if (this.router.url === '/login') {
                if (new Date().getTime() - this.lastTimeBackPress < this.timePeriodToExit) {
                    // this.platform.exitApp(); // Exit from app
                    navigator['app'].exitApp(); // work for ionic 4

                } else {
                    // this.toast.show(
                    //     `Press back again to exit App.`,
                    //     '2000',
                    //     'center')
                    //     .subscribe(toast => {
                    //         // console.log(JSON.stringify(toast));
                    //     });
                    this.toastHandle.presentToast("Press back again to exit App.");
                    this.lastTimeBackPress = new Date().getTime();
                }
            }
        });
    });
}


}
