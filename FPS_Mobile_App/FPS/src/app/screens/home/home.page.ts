import { Component } from '@angular/core';
import { IonSlides } from '@ionic/angular';
import { StoreService } from 'src/app/services/store.service';
import { store } from '@angular/core/src/render3';
import {Store} from 'src/app/models/store.model';
import { TouchSequence } from 'selenium-webdriver';
import { Http } from '@angular/http';
import { NativeStorage } from '@ionic-native/native-storage/ngx';

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

  myAccount : any;

  constructor(
    private storeService : StoreService,
    private nativeStorage: NativeStorage,
  ){

  }

  ngOnInit() {
    console.log("da tao");
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
      }
    )
    console.log("MYACCOUNT");
    this.nativeStorage.getItem("MYACCOUNT").then(data => {
      this.myAccount.push(data);
    });
    
    console.log(this.myAccount.phoneNumber);
    console.log(this.nativeStorage.getItem("MYACCOUNT"));
    console.log("--MYACCOUNT--");
  }


  slidesDidLoad(slides: IonSlides) {
    slides.startAutoplay();
  }

  search(event){
    console.log("search value: " + event.target.value);
  }
  
}
