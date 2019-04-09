import { Injectable } from '@angular/core';
import { Constant } from '../common/constant';
import { ApihttpService } from './apihttp.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
// import { Http } from '@angular/http'
import { HTTP } from '@ionic-native/http/ngx';
import { Storage } from '@ionic/storage';
import { Geolocation } from '@ionic-native/geolocation/ngx';


@Injectable({
  providedIn: 'root'
})
export class GoogleApiService {

  constructor(
    private apihttp: ApihttpService,
    private constant: Constant,
    private http: HTTP,
    // private httpNative : HTTP,
    private storage: Storage,
    private geolocation: Geolocation,
  ) { }

  // createHeader() {
  //   // const headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*')
  //   //   .set('Access-Control-Allow-Methods', 'POST, GET, OPTIONS, PUT')
  //   //   .set('Accept', 'application/json')
  //   //   .set('Access-Control-Allow-Headers', 'Content-Type')
  //   //   .set('Content-Type', 'application/json; charset=utf-8');


  //   const headers2 = new HttpHeaders({ 'Content-Type': 'application/json',
  //   'Access-Control-Allow-Origin':'https://maps.googleapis.com/maps/api/directions',
  //   'Access-Control-Allow-Methods':'POST, GET, OPTIONS, PUT',
  //   'Access-Control-Allow-Credentials' :'true',
  //   'Access-Control-Allow-Headers':'Content-Type'});
  //   return headers2;
  // }
  


  getAddressGoogle(originLati, originLongi, desLati, desLongi) {

  // let headers = this.createHeader();
    // console.log(headers);

    
    return this.http.get(this.constant.GOOGLEAPI +
        originLati + "," + originLongi +
       "&destination=" +
         desLati + "," + desLongi +
         "&key=" + this.constant.GOOGLEKEY
         ,{},{}
    );
  }

   getCurrentLocation(){
     this.geolocation.getCurrentPosition().then((resp) => {
      console.log(resp)
      let lati = resp.coords.latitude;
      let longi = resp.coords.longitude;
      this.storage.set("MYLOCATION", { latitude: lati, longitude: longi })
      // resp.coords.latitude
      // resp.coords.longitude
    }).catch((error) => {
      console.log('Error getting location: ', error);
    });
  }
  
  
}
