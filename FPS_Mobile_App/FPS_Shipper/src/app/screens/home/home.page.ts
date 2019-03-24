import { Component, OnInit } from '@angular/core';
import { OrderService } from 'src/app/services/order.service';
import { LoadingService } from 'src/app/services/loading.service';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit {

  shipperMode = false;
  // longitudeShipper : any;
  // latitudeShipper : any;
  longitudeShp = "106.67927390539103";
  latitudeShp = "10.83767617410066";

  order = [];
  isLoading = false;
  isLoaded = false;

  constructor(
    private orderService: OrderService,
    private loading: LoadingService,
  ) {
    this.shipperMode = false;
  }

  ngOnInit() {

  }

  changeMode() {
    if (this.shipperMode) {
      console.log("onModeShipper");
      //loading 
      this.loading.present().then(() => {

        this.isLoading = true;
        //call api to auto assign order
        this.orderService.onShipMode(this.longitudeShp, this.latitudeShp).subscribe(
          res => {
            console.log(res);
            this.order.push(res);


            if (this.order[0].message === "Success") {
              if (this.order[0].data) {
                this.isLoaded = true;
                this.isLoading = false;
                console.log(this.order[0].data);
              }
            }
          }, () => {
            //handle finish loading
            if (!this.isLoading) {
              this.loading.dismiss();
            }
          }
        ); //end api auto assign order
      });//end loading


    }else{
      //if mode is off
      this.orderService.offShipperMode().subscribe(res => {
        console.log(res);
        this.isLoaded = false;
      });//end api
    }
    console.log(this.shipperMode);
  }
}
