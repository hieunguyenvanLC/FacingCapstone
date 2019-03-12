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

  constructor(
    private storeService : StoreService
  ){

  }

  ngOnInit() {
    this.storeService.getList().subscribe(
      res => {
        //this.stores = Array.prototype.slice.call(data.toString);
        this.stores.push(res);
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
