import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-store',
  templateUrl: './store.page.html',
  styleUrls: ['./store.page.scss'],
})
export class StorePage implements OnInit {

  constructor(
    private route: Router,
  ) {

  }

  ngOnInit() {
    console.log("init");
    
  }

  onPageLoaded() {
    //not work 
    console.log("loaded");
    setTimeout(() => {
      var div_native, nonePadding, arr;
      div_native = document.getElementsByClassName("item-native");

      nonePadding = "none_padding";

      arr = div_native.className.split(" ");
      if (arr.indexOf(nonePadding) == -1) {
        div_native.className += " " + nonePadding;
      }
    }, 1000)
  }

  backToHome() {
    this.route.navigateByUrl("home");
  }

}
