import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';

import { IonicModule, IonicRouteStrategy } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { OrdermodalPageModule } from './screens/ordermodal/ordermodal.module';

import { AccountService } from './services/account.service';
import { StoreService } from './services/store.service';

import { HttpClientModule } from '@angular/common/http';

import { HttpModule } from '@angular/http';

import { Constant } from './common/constant';
import { OrderService } from './services/order.service';
import { AngularFireModule } from 'angularfire2';
import { AngularFirestoreModule } from 'angularfire2/firestore';

import { Geolocation } from '@ionic-native/geolocation/ngx';

import { Camera } from '@ionic-native/camera/ngx';
import { NativeGeocoder } from '@ionic-native/native-geocoder/ngx';

var firebaseConfig = {
  apiKey: "AIzaSyColKpNZnt8yHEHeQlPVRf-jTYy2j9UvnY",
  authDomain: "fpscustomer-project.firebaseapp.com",
  databaseURL: "https://fpscustomer-project.firebaseio.com",
  projectId: "fpscustomer-project",
  storageBucket: "fpscustomer-project.appspot.com",
  messagingSenderId: "276178642720"
};

import { Firebase } from '@ionic-native/firebase/ngx';
import { FirebasecloudmessengerService } from './services/firebasecloudmessenger.service'
import { LoadingService } from './services/loading.service';
import { GoogleMaps } from '@ionic-native/google-maps/ngx';

import { ToastHandleService } from './services/toasthandle.service';
import { IonicStorageModule } from '@ionic/storage';
import { GoogleApiService } from './services/google-api.service';
import { HTTP } from '@ionic-native/http/ngx';
import { from } from 'rxjs';
import { FCM } from '@ionic-native/fcm/ngx';
import { StorageApiService } from './services/storage-api.service';
import { AlertService } from './services/alert.service';
import { AddMemberPageModule } from './screens/add-member/add-member.module';
import { ImageResizer } from '@ionic-native/image-resizer/ngx';
// import { StarRatingModule } from 'ionic3-star-rating';
// import { IonicRatingModule } from 'ionic-rating';

@NgModule({
  declarations: [AppComponent],
  entryComponents: [],
  imports: [
    BrowserModule,
    IonicModule.forRoot(),
    IonicStorageModule.forRoot(),
    AppRoutingModule,
    OrdermodalPageModule,
    AddMemberPageModule,
    HttpClientModule,
    HttpModule,

  ],
  providers: [
    
    StatusBar,
    SplashScreen,
    AccountService,
    StoreService,
    Firebase,
    Constant,
    FirebasecloudmessengerService,
    Geolocation,
    Camera,
    LoadingService,
    OrderService,
    ToastHandleService,
    GoogleApiService,
    StorageApiService,
    AlertService,
    HTTP,  
    Geolocation,
    NativeGeocoder,
    FCM,
    ImageResizer,
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy },
    
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
