import { Component } from '@angular/core';
import { IonSlides } from '@ionic/angular';
import { StoreService } from 'src/app/services/store.service';
import { Store } from 'src/app/models/store.model';
import { TouchSequence } from 'selenium-webdriver';
import { LoadingService } from 'src/app/services/loading.service';
import { ToastHandleService } from 'src/app/services/toasthandle.service';
import { Storage } from '@ionic/storage';
import { AccountService } from 'src/app/services/account.service';
import { AppComponent } from '../../app.component'
import { Constant } from 'src/app/common/constant';
import { Geolocation } from '@ionic-native/geolocation/ngx';
import {
  NativeGeocoder,
  NativeGeocoderReverseResult,
  NativeGeocoderForwardResult,
  NativeGeocoderOptions
} from '@ionic-native/native-geocoder/ngx';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  slideOpts = {
    effect: 'flip'
  };

  searchValue: string;
  storesObj = [];
  stores = [];
  longitude = 106.67927390539103;
  latitude = 10.82767617410066;

  isLoaded = false;
  userDetail = [];
  username: any;

  myAccount: any;

  constructor(
    private storeService: StoreService,
    private loading: LoadingService,
    private toastHandle: ToastHandleService,
    private storage: Storage,
    private accountService: AccountService,
    private appComponent: AppComponent,
    private constant: Constant,
    private geolocation: Geolocation,
    private nativeGeocoder: NativeGeocoder,
  ) {
    console.log('contructor')
    this.storage.get("MYLOCATION").then(value => {
      if (value === null) {
        // console.log("in null, set location");
        // console.log(value);
        // this.appComponent.getLocataion();
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
      }
      // console.log("MYLOCATION IS ")
      // console.log(value);
    });
  }

  ngOnInit() {
    console.log("da tao");
    this.loading.present(this.constant.LOADINGMSG).then(() => {
      this.geolocation.getCurrentPosition().then((resp) => {
        console.log(resp.coords.latitude)
        console.log(resp.coords.longitude)
      this.storeService.getList(resp.coords.longitude, resp.coords.latitude).subscribe(
        res => {
          this.storesObj.push(res);
          this.storesObj[0].data.forEach(element => {
            this.stores.push(element);
          });
          // console.log(this.stores);
          if (this.stores) {
            this.isLoaded = true;
          }
        }, (err) => {
          //error
          this.toastHandle.presentToast("Error to loading store");
          console.log(err);
        }, () => {
          //stop process loading
          if (this.isLoaded) {
            this.loading.dismiss();
          }
        }
      )//end get list store api
      })//end geolocation getcurrent location

    })//end loading process

    //********** getUserDetail ***************/
    // this.accountService.getDetailUser().subscribe(res => {
    //   let tempArr = [];
    //   tempArr.push(res);
    //   console.log(tempArr[0].data);
    // })//end api getUserDetail
    this.getUser();
    // this.storage.get("ACCOUNT").then(value => {
    //   console.log("ACCOUNT-ieikei");

    //   if (value) {
    //     // console.log("not empty");
    //     // console.log(value);
    //     this.username = value.name;
    //     this.appComponent.refreshSlideMenu(value.name, value.avatar, value.extraPoint);
    //   } else {
    //     console.log("empty");
    //   }

    // }); //end call storage for account
    

  }

  getUser() {
    this.accountService.getDetailUser().subscribe(
      res => {
        //this.userDetail.push(res);
        let tempArr = [];
        tempArr.push(res);
        console.log("in get user----")
        console.log(tempArr[0].data);
        this.appComponent.refreshSlideMenu(tempArr[0].data.name, tempArr[0].data.avatar, tempArr[0].data.extraPoint);
      }, error => {
        console.log(error);
      },
      // () => {
      //   if (this.status_code === 1){
      //     this.loading.dismiss();
      //   }else{
      //     //handle error
      //   }
      // }
    );
  }

  slidesDidLoad(slides: IonSlides) {
    slides.startAutoplay();
  }

  search(event) {
    console.log("search value: " + event.target.value);
  }

}
