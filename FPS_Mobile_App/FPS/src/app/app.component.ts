import { Component } from '@angular/core';

import { Platform, NavController } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';
import { Router } from '@angular/router';
import { AccountService } from './services/account.service';
import { Storage } from '@ionic/storage';
import { Geolocation } from '@ionic-native/geolocation/ngx';
import { GoogleApiService } from './services/google-api.service';
import { StorageApiService } from './services/storage-api.service';
import { FCM } from '@ionic-native/fcm/ngx';
import { LoadingService } from './services/loading.service';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html'
})
export class AppComponent {
  public appPages = [
    {
      title: 'Home',
      url: '/home',
      icon: 'home'
    },
    {
      title: 'Order History',
      url: '/order-history',
      icon: 'list'
    },
    {
      title: 'Log out',
      url: '/logout',
      icon: 'log-out'
    }
  ];

  isLoaded = false;
  isLoadImg = false;
  username: any;
  avatar: any;
  extraPoint: any;

  constructor(
    private platform: Platform,
    private splashScreen: SplashScreen,
    private statusBar: StatusBar,
    public navCtrl: NavController,
    private router: Router,
    private fcm: FCM,
    private accountService: AccountService,
    private storage: StorageApiService,
    private geolocation: Geolocation,
    private googleAPI : GoogleApiService,
    private loading : LoadingService,
  ) {
    this.initializeApp();
    // this.getLocataion();
  }

  initializeApp() {
    this.platform.ready().then(() => {
      this.statusBar.styleDefault();
      this.splashScreen.hide();

      //fireBase
      // this.fcm.getToken().then(token => {
      //   console.log(token);
      // });// end fcm
      // this.fcm.onNotification().subscribe(data => {
      //   console.log(data);
      //   if (data.wasTapped) {
      //     console.log('Received in background');
          
      //     //data.order.id
      //     this.loading.dismiss();
      //     this.router.navigate(['check-out', data.order.id]);
      //   } else {
      //     console.log('Received in foreground');
      //     // this.router.navigate([data.landing_page, data.price]);
      //     this.loading.dismiss();
      //     this.router.navigate(['check-out', data.order.id]);
      //   }
      // });// end fcm
      // this.storage.clear().then(() => {
        console.log(11111111)
        // this.geolocation.getCurrentPosition().then((resp) => {
        //   console.log(resp)
        //   let lati = resp.coords.latitude;
        //   let longi = resp.coords.longitude;
        //   this.storage.set("MYLOCATION", { latitude: lati, longitude: longi })
        //   // resp.coords.latitude
        //   // resp.coords.longitude
        // }).catch((error) => {
        //   console.log('Error getting location: ', error);
        // });
       
      });

    // });
  }

  refreshSlideMenu(name, avatar, status) {
    this.isLoaded = true;
    this.username = name;
    this.extraPoint = status;
    if (avatar) {
      this.isLoadImg = true;
      this.avatar = avatar;
    }
    console.log("refresh menu")
  }

  goToEditProgile() {
    this.router.navigateByUrl("profile");
  }

  // getLocataion(){
  //   this.geolocation.getCurrentPosition().then((resp) => {
  //     console.log(resp)
  //     let lati = resp.coords.latitude;
  //     let longi = resp.coords.longitude;
  //     this.storage.set("MYLOCATION", { latitude: lati, longitude: longi })
  //     // resp.coords.latitude
  //     // resp.coords.longitude
  //   }).catch((error) => {
  //     console.log('Error getting location: ', error);
  //   });
  // }

   async logout() {
    await this.accountService.logOut().subscribe(res => {
      console.log(res);
      
      this.storage.clear();
      
      this.router.navigateByUrl("login");
    }); //end api log out
  }
}
