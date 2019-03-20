import {
  Component,
  OnInit,
  ViewChild,
  ElementRef
} from '@angular/core';

import { Geolocation } from '@ionic-native/geolocation/ngx';
import {
  NativeGeocoder,
  NativeGeocoderReverseResult,
  NativeGeocoderOptions
} from '@ionic-native/native-geocoder/ngx';
import { Router } from '@angular/router';

@Component({
  selector: 'app-order',
  templateUrl: './order.page.html',
  styleUrls: ['./order.page.scss'],
})
export class OrderPage implements OnInit {

  @ViewChild('map') mapElement: ElementRef;
  map: any;
  address: string;

  constructor(
    private geolocation: Geolocation,
    private nativeGeocoder: NativeGeocoder,
    public router: Router,
    // public googleMaps: GoogleMaps,
  ) { }

  ngOnInit() {
    //this.loadMap();
  }

  // loadMap() {
  //   this.geolocation.getCurrentPosition().then((resp) => {
  //     let latLng = new google.maps.LatLng(resp.coords.latitude, resp.coords.longitude);
  //     let mapOptions = {
  //       center: latLng,
  //       zoom: 15,
  //       mapTypeId: google.maps.MapTypeId.ROADMAP
  //     }

  //     this.getAddressFromCoords(resp.coords.latitude, resp.coords.longitude);

  //     this.map = new google.maps.Map(this.mapElement.nativeElement, mapOptions);

  //     let marker: google.maps.Marker = new google.maps.Marker({
  //       map: this.map,
  //       position: latLng
  //     })

  //     this.map.addListener('tilesloaded', () => {
  //       console.log('accuracy', this.map);
  //       this.getAddressFromCoords(this.map.center.lat(), this.map.center.lng())
  //     });

  //   }).catch((error) => {
  //     console.log('Error getting location', error);
  //   });
  //   // let map: GoogleMap = this.googleMaps.create(this.mapElement.nativeElement);
  //   // map.one(GoogleMapsEvent.MAP_READY).then((data: any) => {
  //   //   let coordinates: LatLng = new LatLng(33.6396965, -84.4304574);
  //   //   let position = {
  //   //     target: coordinates,
  //   //     zoom: 17
  //   //   };
  //   //   map.animateCamera(position);
  //   //   let markerOptions: MarkerOptions = {
  //   //     position: coordinates,
  //   //     icon: "assets/images/icons8-Marker-64.png",
  //   //     title: 'Our first POI'
  //   //   };
  //   //   const marker = map.addMarker(markerOptions)
  //   //     .then((marker: Marker) => {
  //   //       marker.showInfoWindow();
  //   //   });
  //   // })
  // }

  // getAddressFromCoords(lattitude, longitude) {
  //   console.log("getAddressFromCoords " + lattitude + " " + longitude);
  //   let options: NativeGeocoderOptions = {
  //     useLocale: true,
  //     maxResults: 5
  //   };

  //   this.nativeGeocoder.reverseGeocode(lattitude, longitude, options)
  //     .then((result: NativeGeocoderReverseResult[]) => {
  //       this.address = "";
  //       let responseAddress = [];
  //       for (let [key, value] of Object.entries(result[0])) {
  //         if (value.length > 0)
  //           responseAddress.push(value);

  //       }
  //       responseAddress.reverse();
  //       for (let value of responseAddress) {
  //         this.address += value + ", ";
  //       }
  //       this.address = this.address.slice(0, -2);
  //     })
  //     .catch((error: any) => {
  //       this.address = "Address Not Available!";
  //       console.log("error at reverse: " + error);
  //     });

  // }
}
