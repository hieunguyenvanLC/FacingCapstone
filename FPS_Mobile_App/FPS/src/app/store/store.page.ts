import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import {
  ToastController,
  ModalController
} from '@ionic/angular';
import { OrdermodalPage } from '../ordermodal/ordermodal.page';

@Component({
  selector: 'app-store',
  templateUrl: './store.page.html',
  styleUrls: ['./store.page.scss'],
})
export class StorePage implements OnInit {
  num = 0;
  products = [
    {
      id: "AL1",
      img: "../../assets/image/trasua1.jpg",
      name: "Trà sữa chân trâu đường đen - size M (500ml)",
      price: "100000",
      quantity: 0
    },
    {
      id: "AL2",
      img: "../../assets/image/trasua1.jpg",
      name: "Trà sữa chân trâu đường đen - size L (750ml)",
      price: "500000",
      quantity: 0
    },
    {
      id: "AL3",
      img: "../../assets/image/trasua1.jpg",
      name: "Trà sữa chân trâu đường đen - size XL (1050ml)",
      price: "100000",
      quantity: 0
    },
    {
      id: "AL4",
      img: "../../assets/image/trasua1.jpg",
      name: "Trà sữa chân trâu đường đen - size XXL (1200ml)",
      price: "100000",
      quantity: 0
    },
    {
      id: "AL5",
      img: "../../assets/image/trasua1.jpg",
      name: "Trà sữa chân trâu đường đen - size S (500ml)",
      price: "100000",
      quantity: 0
    }
  ]
  isHaveProduct = false;
  constructor(
    private router: Router,
    public toastController: ToastController,
    private modalController: ModalController,

  ) {
  }

  ngOnInit() {


  }

  addProduct(id) {
    let prod = this.products.find(obj => obj.id == id);
    prod.quantity++;
    this.num++;
    if (this.num == 1) {
      // this.presentToastWithOptions();
      this.isHaveProduct = true;
    }
  }

  removeProduct(id) {
    let prod = this.products.find(obj => obj.id == id);
    if (prod.id == id) {
      if (prod.quantity > 0) {
        prod.quantity--;
        if (this.num > 0) {
          this.num--;
          if (this.num == 0) {
            // this.toastController.dismiss();
            this.isHaveProduct = false;
          }
        }
      }
    };

  }

  async openOrderModal() {
    const modal = await this.modalController.create({
      component: OrdermodalPage,
      componentProps: { value: 123 }
    });
    return await modal.present();
  }

  backToHome() {
    this.router.navigateByUrl("home");
  }

}
