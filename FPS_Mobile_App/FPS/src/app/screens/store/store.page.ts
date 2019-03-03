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
  total = 0;
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
  orders = []
  isHaveProduct = false;
  currentModal = null;

  constructor(
    private router: Router,
    private modalController: ModalController,
  ) {
  }

  ngOnInit() {


  }

  addProduct(id) {
    let prod = this.products.find(obj => obj.id == id); // find product in product list
    let prodInOrder;
    if (prod.quantity == 0) {
      prod.quantity++;
      this.total += parseInt(prod.price);
    }
    if (this.orders != []) {
      prodInOrder = this.orders.find(obj => obj.id == id); // find product in order list
      if (prodInOrder != undefined) {
        prodInOrder.quantity++;
        this.total += parseInt(prod.price);
      } else {
        this.orders.push(prod); //add new a product to array
      }
    }
    this.num++;
    if (this.num == 1) {
      this.isHaveProduct = true; // turn on footer
    }
    console.log(this.orders);
  }

  removeProduct(id) {
    let prodInOrder = this.orders.find(obj => obj.id == id);
    if (prodInOrder.id == id) {
      if (prodInOrder.quantity > 0) {
        prodInOrder.quantity--;
        this.total -= parseInt(prodInOrder.price);  
        if (prodInOrder.quantity == 0) {
          const index = this.orders.indexOf(id, 0);
          this.orders.splice(index, 1) // delete an array item in prodInOrder
        }
        if (this.num > 0) {
          this.num--;
          if (this.num == 0) {
            this.isHaveProduct = false; // turn off footer
          }
        }//end if num
      } //end if quantity
    }// end if id
    console.log(this.orders);
  }

  async openOrderModal() {
    await this.modalController.create({
      animated: true,
      component: OrdermodalPage,
      componentProps: { products: this.orders }
    }).then(modal => {
      modal.present();
      this.currentModal = modal;
    });
  }
  
  backToHome() {
    this.router.navigateByUrl("home");
  }

}
