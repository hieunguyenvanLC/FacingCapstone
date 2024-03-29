import { Injectable } from '@angular/core';
import { ToastController } from '@ionic/angular';

@Injectable({
  providedIn: 'root'
})
export class ToastHandleService {

  constructor(
    public toastController: ToastController,
    ) { }

    async presentToast(myMessage) {
      const toast = await this.toastController.create({
        message: myMessage,
        duration: 2000
      });
      toast.present();
    }
  
    async presentToastWithOptions(myMessage) {
      const toast = await this.toastController.create({
        message: myMessage,
        showCloseButton: true,
        position: 'top',
        closeButtonText: 'Done'
      });
      toast.present();
    }
}
