import { Component } from '@angular/core';
import { IonSlides } from '@ionic/angular';
import { StoreService } from 'src/app/services/store.service';
import { store } from '@angular/core/src/render3';
import {Store} from 'src/app/models/store.model';
import { TouchSequence } from 'selenium-webdriver';
import { Http } from '@angular/http';
import { LoadingService } from 'src/app/services/loading.service';
import { ToastHandleService } from 'src/app/services/toasthandle.service';
import { Storage } from '@ionic/storage';
import { AccountService } from 'src/app/services/account.service';
import { AppComponent } from '../../app.component'

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
  storesObj= [];
  stores = [];
  longitude = 106.67927390539103;
  latitude = 10.82767617410066;

  isLoaded = false;
  userDetail = [];
  status_code = 0;
  username : any;

  myAccount : any;

  constructor(
    private storeService : StoreService,
    private loading : LoadingService,
    private toastHandle: ToastHandleService,
    private storage: Storage,
    private accountService : AccountService,
    private appComponent : AppComponent,
  ){

  }

  ngOnInit() {
    console.log("da tao");
    this.loading.present().then( () => {
      this.storeService.getList(this.longitude, this.latitude).subscribe(
        res => {
          //this.stores = Array.prototype.slice.call(data.toString);
          //console.log(res[0].data);
          this.storesObj.push(res);
          console.log(res);
          console.log(this.storesObj[0].data);
          this.storesObj[0].data.forEach(element => {
            this.stores.push(element);
          });
          console.log(this.stores);
          if (this.stores){
            this.isLoaded = true;
          }
        }, (err) => {
          //error
          this.toastHandle.presentToast("Error to loading store");
          console.log(err);
        }, () => {
          //stop process loading
          if (this.isLoaded){
            this.loading.dismiss();
          }
        }
      )//end get list store api
      
    })//end loading process
    

    this.storage.get("ACCOUNT").then(value => {
      console.log("ACCOUNT-");
      if (value){
        console.log("not empty");
        console.log(value);
        this.username = value.name;
        this.appComponent.refreshSlideMenu(value.name, value.avatar, value.extraPoint);
      }else{
        console.log("empty");
      }
      
    });
    this.storage.get("MYLOCATION").then(value => {
      console.log("MYLOCATION IS ")
      console.log(value);
    });

    //refresh slide menu
    // if (this.username){
    //   this.appComponent.refreshSlideMenu(this.username);
    // } 
    
  }

  getUser(){
    this.accountService.getDetailUser().subscribe(
      res => {
        this.userDetail.push(res);
        console.log(this.userDetail[0].data);
        this.status_code = this.userDetail[0].status_code;

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

  search(event){
    console.log("search value: " + event.target.value);
  }
  
}
