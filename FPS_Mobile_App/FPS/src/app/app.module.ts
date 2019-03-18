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

var firebaseConfig = {
  apiKey: "AIzaSyColKpNZnt8yHEHeQlPVRf-jTYy2j9UvnY",
  authDomain: "fpscustomer-project.firebaseapp.com",
  databaseURL: "https://fpscustomer-project.firebaseio.com",
  projectId: "fpscustomer-project",
  storageBucket: "fpscustomer-project.appspot.com",
  messagingSenderId: "276178642720"
};

import { Firebase } from '@ionic-native/firebase';
import { FirebasecloudmessengerService } from './services/firebasecloudmessenger.service'

@NgModule({
  declarations: [AppComponent],
  entryComponents: [],
  imports: [
    BrowserModule,
    IonicModule.forRoot(),
    AppRoutingModule,
    OrdermodalPageModule,
    HttpClientModule,
    HttpModule,
    AngularFireModule.initializeApp(firebaseConfig),
    AngularFirestoreModule,
  ],
  providers: [
    StatusBar,
    SplashScreen,
    AccountService,
    StoreService,
    OrderService,
    Constant,
    FirebasecloudmessengerService,
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
