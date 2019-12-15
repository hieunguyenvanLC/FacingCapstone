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
      });
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

   async logout() {
    await this.accountService.logOut().subscribe(res => {
      console.log(res);
      
      this.storage.clear();
      
      this.router.navigateByUrl("login");
    }); //end api log out
  }

  formatNumber(num) {
    return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,')
  }
}
