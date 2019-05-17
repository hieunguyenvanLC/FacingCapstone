import { Injectable } from '@angular/core';
import { ApihttpService } from './apihttp.service';
import { Constant } from '../common/constant';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { HTTP } from '@ionic-native/http/ngx';

@Injectable({
  providedIn: 'root'
})
export class PaypalService {

  constructor(
    private apiHttpService: ApihttpService,
    public constant: Constant,
    private http: HttpClient,
  ) {

    // basicAuth = base64encode(`${ PAYPAL_CLIENT }:${ PAYPAL_SECRET }`);
  }




  createHeader() {
    const headers = new HttpHeaders();
    headers.append("Content-Type", "application/json");
    headers.append("Authorization", "Bearer A21AAEPgqtVhCZPbQuWZFFDE6QEZ97pMm6D7hlxqG2WPsL-cjWxqSUg0R-zDB9OoFK_XOLGbaz5Vd93ER6aEj8sE5fXA6fAlw");
    return headers
  }

  getOrderDetail(orderID) {
    let header = this.createHeader();
    return this.http.get("https://api.sandbox.paypal.com/v2/checkout/orders/" + orderID, {
      headers : header,
    })
  }
}
