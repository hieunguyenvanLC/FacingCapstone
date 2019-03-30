import { Component } from '@angular/core';

import { Platform, NavController } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';
import { Router } from '@angular/router';
import { AccountService } from './services/account.service';
import { Storage } from '@ionic/storage';
import { Geolocation } from '@ionic-native/geolocation/ngx';

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
      title: 'List',
      url: '/list',
      icon: 'list'
    },
    {
      title: 'Login',
      url: '/login',
      icon: 'log-in'
    },
    {
      title: 'Store',
      url: '/store',
      icon: 'basket'
    },
    {
      title: 'Order',
      url: '/order',
      icon: 'basket'
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
    // private fcm: FCM,
    private accountService: AccountService,
    private storage: Storage,
    private geolocation: Geolocation,
  ) {
    this.initializeApp();
  }

  initializeApp() {
    this.platform.ready().then(() => {
      this.statusBar.styleDefault();
      this.splashScreen.hide();

      //fireBase
      // this.fcm.getToken().then(token => {
      //   console.log(token);
      // });// end fcm
      // this.storage.clear().then(() => {
        console.log(11111111)
        this.geolocation.getCurrentPosition().then((resp) => {
          console.log(resp)
          let lati = resp.coords.latitude;
          let longi = resp.coords.longitude;
          this.storage.set("MYLOCATION", { latitude: lati, longitude: longi })
          // resp.coords.latitude
          // resp.coords.longitude
        }).catch((error) => {
          console.log('Error getting location: ', error);
        });
      });

    // });
  }

  refreshSlideMenu(name, avatar, extraPoint) {
    this.isLoaded = true;
    this.username = name;
    this.extraPoint = extraPoint;
    if (avatar) {
      this.isLoadImg = true;
      this.avatar = avatar;
    }
    console.log("refresh menu")
  }

  goToEditProgile() {
    this.router.navigateByUrl("profile");
  }

  logout() {

  }
}
