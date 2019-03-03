import { Component } from '@angular/core';
import { IonSlides } from '@ionic/angular';

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

  slidesDidLoad(slides: IonSlides) {
    slides.startAutoplay();
  }

  search(event){
    console.log("search value: " + event.target.value);
  }
}
