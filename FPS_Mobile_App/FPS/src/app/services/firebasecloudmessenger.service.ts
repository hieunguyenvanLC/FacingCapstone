import { Injectable } from '@angular/core';
import { ApihttpService } from './apihttp.service';
//import { Firebase } from '@ionic-native/firebase';
import { Platform } from '@ionic/angular';
import { AngularFirestore } from 'angularfire2/firestore';
import { Firebase } from '@ionic-native/firebase/ngx';

@Injectable()
export class FirebasecloudmessengerService {

  constructor(
    private firebaseNative : Firebase,
    private afs : AngularFirestore,
    private platform : Platform,
  ) { }

  async getToken(){
    let token;

    if (this.platform.is("android")){
      token = await this.firebaseNative.getToken();
      //await this.firebaseNative.grantPermission(); //show popup allow for notification
    }

    if (this.platform.is("android")){
      token = await this.firebaseNative.getToken();
      await this.firebaseNative.grantPermission(); //show popup allow for notification
    }

    //Is not cordova == web pwa
    if (!this.platform.is("cordova")){
      //TODO add PWA support with angularfire2
    }

    return this.saveTokenFireStore(token);
  }

  saveTokenFireStore(token){
    //if null -> stop
    if (!token) return;

    //collect device for send notification for every devices that user has registered
    const devicesRef = this.afs.collection("devices"); 

    const docData = {
      token,
      userID: "testUser", //in real life use auth UID
    }

    return devicesRef.doc(token).set(docData);

  }

  listenToNotifications(){
    return this.firebaseNative.onNotificationOpen();
  }
}
