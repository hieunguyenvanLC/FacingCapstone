import { Component, OnInit, Input } from '@angular/core';
import { NavParams, ModalController } from 'node_modules/@ionic/angular';

@Component({
  selector: 'app-ordermodal',
  templateUrl: './ordermodal.page.html',
  styleUrls: ['./ordermodal.page.scss'],
})
export class OrdermodalPage implements OnInit {
  
  //"value" passed in componentProps
  @Input() products: any;
  @Input() subTotal: any;
  @Input() latitudeStore: any;
  @Input() longitudeStore: any;
  @Input() addressStore: any;

  deliveryFees : number ;
  total : number;


  constructor (
    private navParams: NavParams,
    private modalController: ModalController
  ) {
    // componentProps can also be accessed at construction time using NavParams
  }
  ngOnInit() {
    this.deliveryFees = 35000;
    this.total = this.deliveryFees + this.subTotal;
  }
  dismissModal() {
    this.modalController.dismiss();
  }


  checkout() {
    for (let i = 0; i < this.products.length; i++) {
      const element = this.products[i];
      if (element != undefined) {
        if (i == this.products.length - 1) {
          //this.prodList += element.id + "x" + element.quantity;
        } else {
          //this.prodList += element.id + "x" + element.quantity + "n";
        }
      }


    }
    // this.orderService.createOrder(this.longutudeCus, this.latitudeCus,  "", this.prodList)
    //                  .subscribe(data => {
    //                    console.log(data);
    //                  });


    

  }
}
