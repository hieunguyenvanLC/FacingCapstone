import { Injectable } from '@angular/core';
import { ActionSheetController, AlertController } from '@ionic/angular';
import { PayPal, PayPalPayment, PayPalConfiguration } from '@ionic-native/paypal/ngx';
import { Constant } from '../common/constant';
import { PaypalService } from './paypal.service';
import { AlertService } from './alert.service';
import { AppComponent } from '../app.component';
import { LoadingService } from './loading.service';
import { AccountService } from './account.service';
import { ProfilePageModule } from '../screens/profile/profile.module';
import { ProfilePage } from '../screens/profile/profile.page';

@Injectable({
  providedIn: 'root'
})
export class ActionSheetHandleService {

  constructor(
    public actionSheetController: ActionSheetController,
    private payPal: PayPal,
    private constant: Constant,
    private paypalService: PaypalService,
    private alertController: AlertController,
    private appComponent: AppComponent,
    private loading: LoadingService,
    private accountService: AccountService,
  ) { }

  async paymentActionSheet(money_VND, money_USD, description, userAccount) {
    let formatMoney_VND = this.appComponent.formatNumber(money_VND);
    const actionSheet = await this.actionSheetController.create({
      header: 'Choose your card',
      buttons: [{
        text: `Paypal`,
        role: 'destructive',
        icon: '',
        cssClass: '',
        handler: () => {
          this.presentAlertWithMsg('', formatMoney_VND + ' VND will be exchanged to ' + money_USD + ' $ for this payment !', money_VND, money_USD, description, userAccount)

        }//end handle
      },
      {
        text: 'Cancel',
        icon: 'close',
        role: 'cancel',
        handler: () => {
          console.log('Cancel clicked');
        }
      }
      ]
    });
    await actionSheet.present();
  }

  async dismiss() {
    return this.actionSheetController.dismiss();
  }

  async presentAlertWithMsg(header, msg, money_VND, money_USD, description, userAccount) {
    const alert = await this.alertController.create({
      header: header,
      message: msg,
      buttons: [
        {
          text: 'OK',
          handler: () => {
            this.payPal.init({
              PayPalEnvironmentProduction: 'YOUR_PRODUCTION_CLIENT_ID',
              PayPalEnvironmentSandbox: this.constant.PAYPAL_SANDBOX_KEY,
            }).then(() => {
              // Environments: PayPalEnvironmentNoNetwork, PayPalEnvironmentSandbox, PayPalEnvironmentProduction
              this.payPal.prepareToRender('PayPalEnvironmentSandbox', new PayPalConfiguration({
                // Only needed if you get an "Internal Service Error" after PayPal login!
                //payPalShippingAddressOption: 2 // PayPalShippingAddressOptionPayPal
              })).then(() => {
                let payment = new PayPalPayment(money_USD, 'USD', 'Add ' + money_USD + ' $ to ' + userAccount + ' wallet', 'sale');
                this.payPal.renderSinglePaymentUI(payment).then((res) => {
                  // Successfully paid
                  console.log(res);
                  let tempArr = [];
                  tempArr.push(res.response);
                  let orderID = tempArr[0].id;
                  console.log(orderID);
                  // console.log(res);
                  this.loading.present("Loading...").then(() => {
                    this.accountService.depositToWallet(money_VND).subscribe(res => {
                      console.log(res)
                      this.loading.dismiss();
                    })//end api save money
                  })//end load ding
                  // Example sandbox response
                  //
                  // {
                  //   "client": {
                  //     "environment": "sandbox",
                  //     "product_name": "PayPal iOS SDK",
                  //     "paypal_sdk_version": "2.16.0",
                  //     "platform": "iOS"
                  //   },
                  //   "response_type": "payment",
                  //   "response": {
                  //     "id": "PAY-1AB23456CD789012EF34GHIJ",
                  //     "state": "approved",
                  //     "create_time": "2016-10-03T13:33:33Z",
                  //     "intent": "sale"
                  //   }
                  // }
                }, () => {
                  // Error or render dialog closed without being successful
                });
              }, () => {
                // Error in configuration
              });
            }, () => {
              // Error in initialization, maybe PayPal isn't supported or something else
            });//end paypal
          }
        },
        {
          text: 'Cancel',
          role: 'cancel',
          cssClass: 'secondary',
          handler: (blah) => {
          }
        },
      ]
    });

    await alert.present();
  }
}
