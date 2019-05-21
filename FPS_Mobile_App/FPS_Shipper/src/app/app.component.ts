import { Component } from '@angular/core';

import { Platform } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';

import { Storage } from '@ionic/storage';
import { Geolocation } from '@ionic-native/geolocation/ngx';
import { AccountService } from './services/account.service';
import { Router } from '@angular/router';

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
      title: 'Order history',
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
  sumRevenue: any;

  constructor(
    private platform: Platform,
    private splashScreen: SplashScreen,
    private statusBar: StatusBar,
    private storage: Storage,
    private geolocation: Geolocation,
    private accountService: AccountService,
    private router: Router,
  ) {
    this.initializeApp();
  }

  initializeApp() {
    this.platform.ready().then(() => {
      this.statusBar.styleDefault();
      this.splashScreen.hide();

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
  }

  refreshSlideMenu(name, avatar, sumRevenue) {
    this.isLoaded = true;
    this.username = name;
    this.sumRevenue = sumRevenue;
    if (avatar) {
      this.isLoadImg = true;
      this.avatar = avatar;
    }
    // console.log("refresh menu")
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

  formatNumber(num) {
    return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,')
  }
}
