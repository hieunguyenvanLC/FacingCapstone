import { Component, OnInit, Input } from '@angular/core';
import { NavParams, ModalController, ToastController } from 'node_modules/@ionic/angular';
import { OrderService } from '../../services/order.service';
import { Router } from '@angular/router';

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

  longutudeCus = "106.67927390539103";
  latitudeCus = "10.82767617410066";
  prodList = "";
  distance = "1.8";

  deliveryFees: number;
  total: number;

  temp = [];

  orderId: number;
  orderStatus: any;


  constructor(
    private navParams: NavParams,
    private modalController: ModalController,
    private orderService: OrderService,
    public router: Router,
    public toastController: ToastController,
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

   async checkout() {
    //custo
    for (let i = 0; i < this.products.length; i++) {
      const element = this.products[i];
      if (element != undefined) {
        if (i == this.products.length - 1) {
          this.prodList += element.id + "x" + element.quantity;
        } else {
          this.prodList += element.id + "x" + element.quantity + "n";
        }
      }


    }
    //this.router.navigateByUrl("order");
    await this.orderService.createOrder(this.longutudeCus, this.latitudeCus, "", this.prodList, this.distance)
      .subscribe(data => {
        console.log(data);
        console.log("in create order ----");
        this.temp.push(data);

        //if not success, toast for error
        if (this.temp[0].message !== "Success") {
          this.presentToast("Error check out ! Try again !");
        } else {
          //handle success api create order
          this.presentToast("Order success ! Finding shipper...");
          console.log(this.temp[0].data);

          //get id order
          this.orderId = this.temp[0].data;
          console.log(this.orderId);
          
          //this.router.navigateByUrl("order");

          //-----get status order
          if (this.orderId) {
            this.orderService.getOrderStatus(this.orderId).subscribe(res => {
              if (!this.temp) {
                // console.log("in !temp");
                // this.temp = [];
                // this.temp.push(res);
                // console.log(this.temp[0].data);
              }else{
                console.log("in temp");
                this.temp = [];
                this.temp.push(res);
                
                console.log(this.temp[0].data);
                //start if status
                if (this.temp[0].data.status !== undefined){
                  this.orderStatus = this.temp[0].data.status;
                  console.log("order status - " + this.orderStatus + " - " + this.temp[0].data.status);
                
                
                
                // while(this.orderStatus === 1){
                //   console.log("in while loop");
                //   setTimeout(()=> {
                //     console.log("in while");
                //     this.orderService.getOrderStatus(this.orderId).subscribe(res => {
                //       this.temp = [];
                //       this.temp.push(res);
                //       if (this.temp[0].data.status === 2){
                //         this.orderStatus = this.temp[0].data.status;
                //         console.log("in set interval - " + this.orderStatus);
                //       }
                //     });
                //   }, 3*1000);
                // }

                // setInterval(() => {
                //   console.log("set interval");
                //   this.orderService.getOrderStatus(this.orderId).subscribe(res => {
                //     this.temp = [];
                //     this.temp.push(res);
                //     if (this.temp[0].data.status === 2){
                //       this.orderStatus = this.temp[0].data.status;
                //       console.log("in set interval - " + this.orderStatus);
                //       return;
                //     }
                //   });
                // }, 5*1000)

              }// end if status
               
                
              }
            });
            console.log("done request status !");
          }
          //-----end get status order

        }

        console.log("--end create order");
      });

    //get user status
    

  }

  //for loading finding shipper
  async presentLoading() {
    const loadingController = document.querySelector('ion-loading-controller');
    await loadingController.componentOnReady();

    const loadingElement = await loadingController.create({
      message: 'Finding shipper...',
      duration: 2000,
    });
    loadingElement.present();
  }

  //toast for notification
  async presentToast(myMessage) {
    const toast = await this.toastController.create({
      message: myMessage,
      duration: 2000
    });
    toast.present();
  }
}
