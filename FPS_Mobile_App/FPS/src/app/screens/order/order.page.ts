import {
  Component,
  OnInit,
  ViewChild,
  ElementRef
} from '@angular/core';

// import {
//   NativeGeocoder,
//   NativeGeocoderReverseResult,
//   NativeGeocoderOptions
// } from '@ionic-native/native-geocoder/ngx';
import { Router } from '@angular/router';
import {
  GoogleMaps,
  GoogleMap,
  MyLocation,
  Marker,
  GoogleMapsAnimation,
  GoogleMapsEvent,
  Environment,
  GoogleMapOptions,
} from '@ionic-native/google-maps';
import { Platform, ToastController, LoadingController } from '@ionic/angular';




@Component({
  selector: 'app-order',
  templateUrl: './order.page.html',
  styleUrls: ['./order.page.scss'],
})
export class OrderPage implements OnInit {

  //@ViewChild('map') mapElement: ElementRef;
  map: GoogleMap;
  loading: any;
  //address: string;

  constructor(
    //private nativeGeocoder: NativeGeocoder,
    public router: Router,
    // public googleMaps: GoogleMaps,
    //private platform : Platform,
    private toastCtrl: ToastController,
    private loadingCtrl: LoadingController,
  ) { }

  ngOnInit() {
    // this.loadMap();
    //await this.platform.ready();
    console.log("anfasda")
    
  }

  ngAfterViewInit(){
    this.loadMap();
  }

  loadMap() {
    // this.map = GoogleMaps.create('map_canvas', {
    //   camera: {
    //     target: {
    //       lat: 43.0741704,
    //       lng: -89.3809802
    //     },
    //     zoom: 18,
    //     tilt: 30
    //   }
    // });

    // Environment.setEnv({
    //   'API_KEY_FOR_BROWSER_RELEASE': 'AIzaSyASMHVlLhJr78esAqJVglJU67r-SD-VBNQ'
    // });

    // this.geolocation.getCurrentPosition().then((resp) => {
      //     let latLng = new google.maps.LatLng(resp.coords.latitude, resp.coords.longitude);
      //     let mapOptions = {
      //       center: latLng,
      //       zoom: 15,
      //       mapTypeId: google.maps.MapTypeId.ROADMAP
      let mapOptions: GoogleMapOptions = {
        camera: {
          target: {
            // "106.67927390539103
            // "10.82767617410066";
            lat: 10.82767617410066,
            lng: 106.67927390539103
          },
          zoom: 18,
          tilt: 30
        }
      };

      this.map = GoogleMaps.create('map', mapOptions);
      
      this.map.getMyLocation().then((location: MyLocation) => {
        console.log(location);
      })
      let marker: Marker = this.map.addMarkerSync({
        snippet: 'You are here',
        title: 'You are here',
        icon: 'red',
        animation: 'DROP',
        position: {
          lat: 10.82767617410066,
          lng: 106.67927390539103
        }
      });
      marker.on(GoogleMapsEvent.MARKER_CLICK).subscribe(() => {
        // alert('clicked');
      });
    // });


  }




  // async onButtonClick() {
  //   this.map.clear();

  //   this.loading = await this.loadingCtrl.create({
  //     message: 'Please wait...'
  //   });
  //   await this.loading.present();

  //   // Get the location of you
  //   this.map.getMyLocation().then((location: MyLocation) => {
  //     this.loading.dismiss();
  //     console.log(JSON.stringify(location, null ,2));

  //     // Move the map camera to the location with animation
  //     this.map.animateCamera({
  //       target: location.latLng,
  //       zoom: 17,
  //       tilt: 30
  //     });

  //     // add a marker
  //     let marker: Marker = this.map.addMarkerSync({
  //       title: '@ionic-native/google-maps plugin!',
  //       snippet: 'This plugin is awesome!',
  //       position: location.latLng,
  //       animation: GoogleMapsAnimation.BOUNCE
  //     });

  //     // show the infoWindow
  //     marker.showInfoWindow();

  //     // If clicked it, display the alert
  //     marker.on(GoogleMapsEvent.MARKER_CLICK).subscribe(() => {
  //       this.showToast('clicked!');
  //     });
  //   })
  //   .catch(err => {
  //     this.loading.dismiss();
  //     this.showToast(err.error_message);
  //   });
  // }

  // async showToast(message: string) {
  //   let toast = await this.toastCtrl.create({
  //     message: message,
  //     duration: 2000,
  //     position: 'middle'
  //   });

  //   toast.present();
  // }
  /*------------------
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
  */


}
