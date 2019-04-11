import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';

import { IonicModule, IonicRouteStrategy } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';

import { HttpClientModule } from '@angular/common/http';
import { HttpModule} from '@angular/http';
import { Constant } from './common/constant';

import { AccountService } from './services/account.service';
import { OrderService } from './services/order.service';
import { LoadingService } from './services/loading.service';
import { Camera } from '@ionic-native/camera/ngx';

import { Geolocation } from '@ionic-native/geolocation/ngx';
import { NativeGeocoder } from '@ionic-native/native-geocoder/ngx';
import { FCM } from '@ionic-native/fcm/ngx';

import { IonicStorageModule } from '@ionic/storage';
import { HTTP } from '@ionic-native/http/ngx';
import { GoogleMaps } from '@ionic-native/google-maps/ngx';
import { StorageApiService } from './services/storage-api.service';

@NgModule({
  declarations: [AppComponent],
  entryComponents: [],
  imports: [
    BrowserModule,
    IonicModule.forRoot(),
    IonicStorageModule.forRoot(),
    AppRoutingModule,
    HttpClientModule,
    HttpModule
  ],
  providers: [
    StatusBar,
    SplashScreen,

    Constant,

    AccountService,
    OrderService,
    LoadingService,
    StorageApiService,
    HTTP,
    Camera,
    Geolocation,
    NativeGeocoder,
    FCM,
    Storage,
    GoogleMaps,
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
