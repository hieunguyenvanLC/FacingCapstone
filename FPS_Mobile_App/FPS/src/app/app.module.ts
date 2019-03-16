import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';

import { IonicModule, IonicRouteStrategy } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { OrdermodalPage } from './screens/ordermodal/ordermodal.page';
import { OrdermodalPageModule } from './screens/ordermodal/ordermodal.module';

import { AccountService } from './services/account.service';
import { HttpClientModule } from '@angular/common/http';
import { HttpModule} from '@angular/http';
import { StoreService } from './services/store.service';
import { Constant } from './common/constant';
import { OrderService } from './services/order.service';


@NgModule({
  declarations: [AppComponent],
  entryComponents: [],
  imports: [
    BrowserModule,
    IonicModule.forRoot(),
    AppRoutingModule,
    OrdermodalPageModule,
    HttpClientModule,
    HttpModule
  ],
  providers: [
    StatusBar,
    SplashScreen,

    AccountService,
    StoreService,
    OrderService,

    Constant,
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy  }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
