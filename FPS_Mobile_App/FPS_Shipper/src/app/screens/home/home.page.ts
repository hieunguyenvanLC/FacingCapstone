import { Component, OnInit } from '@angular/core';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit {

  shipperMode= false;
  // longitudeShipper : any;
  // latitudeShipper : any;
  longitudeShp = "106.67927390539103";
  latitudeShp = "10.83767617410066";

  order = [];

  constructor(
    private orderService : OrderService,
  ){
    this.shipperMode= false;
  }

  ngOnInit(){
    
  }

  async onMode(){
    if (this.shipperMode){
      console.log("onModeShipper");
        this.orderService.onShipMode(this.longitudeShp, this.latitudeShp).subscribe(
          res => {
            console.log("123123123");
            console.log(res);
            this.order.push(res);
  
            if (this.order[0].data){
              if (this.order[0].data !== "cancel" && this.order[0].data !== "time out"){
                console.log(this.order[0].data);
                
              }
            }
          }
        );
      
    }
    console.log(this.shipperMode);
  }
}
