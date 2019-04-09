import { Component, OnInit, Input } from '@angular/core';
import { NavParams, ModalController, ToastController, AlertController } from 'node_modules/@ionic/angular';
import { OrderService } from '../../services/order.service';
import { Router } from '@angular/router';
import { Storage } from '@ionic/storage';
// import { AutocompletePage } from '../autocomplete/autocomplete.page';
import { FCM } from '@ionic-native/fcm/ngx';
import { LoadingService } from 'src/app/services/loading.service';
import { Firebase } from '@ionic-native/firebase/ngx';
import { AlertService } from 'src/app/services/alert.service';

@Component({
  selector: 'app-ordermodal',
  templateUrl: './ordermodal.page.html',
  styleUrls: ['./ordermodal.page.scss'],
})
export class OrdermodalPage implements OnInit {

  //"value" passed in componentProps
  // @Input() products: any;
  // @Input() subTotal: any;
  // @Input() latitudeStore: any;
  // @Input() longitudeStore: any;
  // @Input() addressStore: any;
  // @Input() shpEarn: any;
  // @Input() duration: any;
  @Input() myOrder: any;

  prodList = "";
  distance = "1.8";

  total: number;

  temp = [];

  orderId: number;
  orderStatus: any;

  tokenFCM: any;

  constructor(
    private navParams: NavParams,
    private modalController: ModalController,
    private orderService: OrderService,
    public router: Router,
    public toastController: ToastController,
    private storage: Storage,
    private modalCtrl: ModalController,
    private fcm: FCM,
    private loading: LoadingService,
    private firebase: Firebase,
    public alertController: AlertController,
    private alertHandle: AlertService,

  ) {
    // componentProps can also be accessed at construction time using NavParams
  }
  ngOnInit() {

    console.log(this.myOrder)
    this.total = this.myOrder[0].shpEarn + this.myOrder[0].subTotal;

    //firebase
    this.fcm.getToken().then(token => {
      // console.log(token);
      this.tokenFCM = token;
    });

    this.fcm.onTokenRefresh().subscribe(token => {
      // console.log(token);
    });

    this.fcm.onNotification().subscribe(data => {
      // console.log("vo day");
      // console.log(data);

      if (data.wasTapped) {
        console.log('Received in background 1');
        this.dismissModal();
        this.loading.dismiss();
        this.alertHandle.dissmissAlert();
        this.router.navigate(['check-out', data.orderId]);
        //data.order.id

      } else {
        console.log('Received in background 3');
        this.dismissModal();
        this.loading.dismiss();
        this.alertHandle.dissmissAlert();
        this.router.navigate(['check-out', data.orderId]);
      }
    });// end fcm


    this.firebase.onNotificationOpen()
      .subscribe(data => {
        console.log('Received in background 2');
        this.dismissModal();
        this.loading.dismiss();
        this.alertHandle.dissmissAlert();
        this.router.navigate(['check-out', data.orderId]);
      });



    console.log(this.myOrder[0].latitudeCus);
    console.log(this.myOrder[0].longitudeCus);
    console.log(this.myOrder[0].distance);
  }//end onInit

  dismissModal() {
    this.modalController.dismiss();
  }

  async checkout() {
    console.log(this.myOrder[0].products.length)
    for (let i = 0; i < this.myOrder[0].products.length; i++) {
      const element = this.myOrder[0].products[i];
      if (element != undefined) {
        if (i == this.myOrder[0].products.length - 1) {
          this.prodList += element.id + "x" + element.quantity;
        } else {
          this.prodList += element.id + "x" + element.quantity + "n";
        }
      }


    }
    //this.router.navigateByUrl("order");
    console.log("cb create")
    await this.orderService.createOrder(this.myOrder[0].longitudeCus, this.myOrder[0].latitudeCus, "", this.prodList, this.myOrder[0].distance, this.tokenFCM)
      .subscribe(data => {
        console.log(data);
        console.log("in create order ----");
        this.temp.push(data);

        //if not success, toast for error
        if (this.temp[0].message !== "Success") {
          this.presentToast("Error check out ! Try again !");
        } else {
          //handle success api create order
          //this.loading.present("Finding shipper for your order...");
          //this.presentToast("Order success ! Finding shipper...");
          this.alertHandle.presentAler();
          console.log(this.temp[0].data);

          //get id order
          this.orderId = this.temp[0].data;
          console.log(this.orderId);

          //this.router.navigateByUrl("order");

          //-----get status order
          if (this.orderId) {
            // this.orderService.getOrderStatus(this.orderId).subscribe(res => {
            //   if (!this.temp) {
            //     // console.log("in !temp");
            //     // this.temp = [];
            //     // this.temp.push(res);
            //     // console.log(this.temp[0].data);
            //   }else{
            //     console.log("in temp");
            //     this.temp = [];
            //     this.temp.push(res);

            //     console.log(this.temp[0].data);
            //     //start if status
            //     if (this.temp[0].data.status !== undefined){
            //       this.orderStatus = this.temp[0].data.status;
            //       console.log("order status - " + this.orderStatus + " - " + this.temp[0].data.status);



            //     // while(this.orderStatus === 1){
            //     //   console.log("in while loop");
            //     //   setTimeout(()=> {
            //     //     console.log("in while");
            //     //     this.orderService.getOrderStatus(this.orderId).subscribe(res => {
            //     //       this.temp = [];
            //     //       this.temp.push(res);
            //     //       if (this.temp[0].data.status === 2){
            //     //         this.orderStatus = this.temp[0].data.status;
            //     //         console.log("in set interval - " + this.orderStatus);
            //     //       }
            //     //     });
            //     //   }, 3*1000);
            //     // }

            //     // setInterval(() => {
            //     //   console.log("set interval");
            //     //   this.orderService.getOrderStatus(this.orderId).subscribe(res => {
            //     //     this.temp = [];
            //     //     this.temp.push(res);
            //     //     if (this.temp[0].data.status === 2){
            //     //       this.orderStatus = this.temp[0].data.status;
            //     //       console.log("in set interval - " + this.orderStatus);
            //     //       return;
            //     //     }
            //     //   });
            //     // }, 5*1000)

            //   }// end if status


            //   }
            // });
            console.log("done request status !");
          }
          //-----end get status order

        }

        console.log("--end create order");
      });

    //get user status


  }//end checkout

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

  async presentAlertConfirm() {
    // let countDown = Date.now() + 15*1000;
    // console.log(Date.now())
    // console.log(countDown - Date.now());
    const alert = await this.alertController.create({
      header: '',
      message: `<ion-list lines="none">
                <ion-item>
                <ion-label>Finding your shipper</ion-label>
                <ion-spinner name="dots"></ion-spinner>
                </ion-item>
                </ion-list>`,
      buttons: [
        // {
        //   text: 'Cancel',
        //   role: 'cancel',

        //   cssClass: 'secondary',
        //   handler: (blah) => {
        //     console.log('Confirm Cancel: blah');
        //     return false;
        //   }
        // },
        {
          text: 'Cancel',
          handler: () => {
            console.log('Confirm Okay');
            // return false;
          }
        }
      ]
    });

    await alert.present();
    alert.dismiss();
  }

  async showAddressModal() {
    // let modal = this.modalCtrl.create(AutocompletePage);
    // let me = this;
    // await modal.onDidDismiss(data => {
    //   this.address.place = data;
    // });
    // modal.present();
    // this.modalController.create({
    //   animated: true,
    //   component: AutocompletePage,
    //   componentProps: {
    //     mydata: this.myOrder[0].currentAddress
    //   }
    // }).then(modal => {
    //   modal.present();
    // })
    // animated: true,
    //   component: OrdermodalPage,
    //   componentProps: {
    //     myOrder: [{
    //       products: this.orders,
    //       latitudeStore: this.products[0].data.latitude, //latitude store
    //       longitudeStore: this.products[0].data.longitude, //longitude store
    //       addressStore: this.products[0].data.address + ", " + this.products[0].data.distStr,
    //       subTotal: this.total,
    //       shpEarn: this.shpEarn,
    //       duration: this.duration,
    //       currentAddress: this.currentAddress,
    //     }]

    //   }
    // }).then(modal => {
    //   modal.present();
    //   this.currentModal = modal;
    // });
  }

}
