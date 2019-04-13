import {
  AfterContentInit,
  Component,
  OnInit,
  ViewChild,
  ElementRef
} from '@angular/core';
import { Geolocation } from '@ionic-native/geolocation/ngx';
import { NativeGeocoder,  NativeGeocoderOptions } from '@ionic-native/native-geocoder/ngx';
import {
  NativeGeocoderResult
} from '@ionic-native/native-geocoder/ngx'

// import {
//   NativeGeocoder,
//   NativeGeocoderReverseResult,
//   NativeGeocoderOptions
// } from '@ionic-native/native-geocoder/ngx';
import { Router } from '@angular/router';
import {
  GoogleMaps,

  MyLocation,
  Marker,
  GoogleMapsAnimation,
  GoogleMapsEvent,
  Environment,
  GoogleMapOptions,
} from '@ionic-native/google-maps';


import { Platform, ToastController, LoadingController } from '@ionic/angular';
import { from } from 'rxjs';
import { LoadingService } from 'src/app/services/loading.service';
import { GoogleMap } from '@ionic-native/google-maps/ngx';



declare var google;
@Component({
  selector: 'app-order',
  templateUrl: './order.page.html',
  styleUrls: ['./order.page.scss'],
})
export class OrderPage implements OnInit{

   @ViewChild('map') mapElement: ElementRef;
  map: any;
  address:string;

  constructor(
    private geolocation: Geolocation,
    private nativeGeocoder: NativeGeocoder,
    //private nativeGeocoder: NativeGeocoder,
    public router: Router,
    private platform : Platform,
    // public googleMaps: GoogleMaps,
    //private platform : Platform,
    private toastCtrl: ToastController,
    private loadingService: LoadingService,
    // private googleMapcreate : GoogleMaps
  ) { }

  ngOnInit() {
    this.loadMap();
  }
 
  loadMap() {
    this.geolocation.getCurrentPosition().then((resp) => {
      let latLng = new google.maps.LatLng(resp.coords.latitude, resp.coords.longitude);
      console.log(latLng);
      let mapOptions = {
        center: {
 
                  lat: 10.82767617410066,
                  lng: 106.67927390539103
                },
                zoom: 18,
                tilt: 30,
        mapTypeId: google.maps.MapTypeId.ROADMAP
      }
 
      this.getAddressFromCoords(resp.coords.latitude, resp.coords.longitude);
 
      this.map = new google.maps.Map(this.mapElement.nativeElement, mapOptions);
 
      this.map.addListener('tilesloaded', () => {
        console.log('accuracy',this.map);
        this.getAddressFromCoords(this.map.center.lat(), this.map.center.lng())
      });
 
    }) //end geolocation get current
    .catch((error) => {
      console.log('Error getting location', error);
    });
  }
 
  getAddressFromCoords(lattitude, longitude) {
    console.log("getAddressFromCoords "+lattitude+" "+longitude);
    let options: NativeGeocoderOptions = {
      useLocale: true,
      maxResults: 5
    };
 
    this.nativeGeocoder.reverseGeocode(lattitude, longitude, options)
      .then((result: NativeGeocoderResult[]) => {
        this.address = "";
        let responseAddress = [];
        for (let [key, value] of Object.entries(result[0])) {
          if(value.length>0)
          responseAddress.push(value);
 
        }
        responseAddress.reverse();
        for (let value of responseAddress) {
          this.address += value+", ";
        }
        this.address = this.address.slice(0, -2);
      })
      .catch((error: any) =>{ 
        this.address = "Address Not Available!";
      });
 
  }
 

}
