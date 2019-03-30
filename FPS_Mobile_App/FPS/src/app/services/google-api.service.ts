import { Injectable } from '@angular/core';
import { Constant } from '../common/constant';
import { ApihttpService } from './apihttp.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http } from '@angular/http'
import { HTTP } from '@ionic-native/http/ngx';


@Injectable({
  providedIn: 'root'
})
export class GoogleApiService {

  constructor(
    private apihttp: ApihttpService,
    private constant: Constant,
    private http: HttpClient,
  ) { }

  createHeader() {
    // const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*')
    //   .set('Access-Control-Allow-Methods', 'POST, GET, OPTIONS, PUT')
    //   .set('Accept', 'application/json')
    //   .set('Access-Control-Allow-Headers', 'Content-Type')
    //   .set('Content-Type', 'application/json; charset=utf-8');


    const headers2 = new HttpHeaders({ 'Content-Type': 'application/json',
    'Access-Control-Allow-Origin':'https://maps.googleapis.com/maps/api/directions',
    'Access-Control-Allow-Methods':'POST, GET, OPTIONS, PUT',
    'Access-Control-Allow-Credentials' :'true',
    'Access-Control-Allow-Headers':'Content-Type'});
    return headers2;
  }
  


  getAddressGoogle(originLati, originLongi, desLati, desLongi) {

  let headers = this.createHeader();
    console.log(headers);
    return this.http.get(this.constant.GOOGLEAPI +
        originLati + "," + originLongi +
       "&destination=" +
         desLati + "," + desLongi +
         "&key=" + this.constant.GOOGLEKEY, {headers: headers}
    );
  }
  
  
}
