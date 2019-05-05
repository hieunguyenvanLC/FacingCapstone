import { Injectable } from '@angular/core';
import { AlertController } from '@ionic/angular';

@Injectable({
  providedIn: 'root'
})
export class AlertService {

  constructor(
    public alertController: AlertController,
  ) { }

  async presentAlert1Button(header, msg, btnText, role) {
    const alert = await this.alertController.create({
      header: 'Confirm!',
      message: 'Message <strong>text</strong>!!!',
      buttons: [
        {
          text: 'Cancel',
          role: 'cancel',
          cssClass: 'secondary',
          handler: (blah) => {
            console.log('Confirm Cancel: blah');
          }
        }, {
          text: 'Okay',
          handler: () => {
            console.log('Confirm Okay');
          }
        }
      ]
    });

    await alert.present();
  }

  async presentAler(){
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
  }

  async presentAlertWithMsg(header, msg){
    const alert = await this.alertController.create({
      header: header,
      message: msg,
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
          text: 'OK',
          handler: () => {
            // console.log('Confirm Okay');
            // return false;
          }
        }
      ]
    });

    await alert.present();
  }

  async dissmissAlert(){
    return await this.alertController.dismiss();
  }
}
