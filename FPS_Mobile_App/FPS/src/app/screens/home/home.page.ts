import { Component } from '@angular/core';
import { IonSlides } from '@ionic/angular';
import { StoreService } from 'src/app/services/store.service';
import { store } from '@angular/core/src/render3';
import {Store} from 'src/app/models/store.model';
import { TouchSequence } from 'selenium-webdriver';
import { Http } from '@angular/http';

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
  stores= [];
  longitude = 106.67927390539103;
  latitude = 10.82767617410066;

  constructor(
    private storeService : StoreService
  ){

  }

  ngOnInit() {
    this.storeService.getList(this.longitude, this.latitude).subscribe(
      res => {
        //this.stores = Array.prototype.slice.call(data.toString);
        this.stores.push(res);
        console.log(res);
        console.log(this.stores[0].data);
      }
    )
  }


  slidesDidLoad(slides: IonSlides) {
    slides.startAutoplay();
  }

  search(event){
    console.log("search value: " + event.target.value);
  }
  
}
