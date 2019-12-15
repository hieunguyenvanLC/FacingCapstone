import { Injectable } from '@angular/core';
import { ApihttpService } from './apihttp.service';
import { Constant } from '../common/constant';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(
    private apiHttp : ApihttpService,
    private constant: Constant,
  ) { }

  onShipMode(longitudeShipper, latitudeShipper, tokenDevice){
    // let formData: FormData = new FormData();
    // formData.append("longitude", longitudeShipper);
    // formData.append("latitude", latitudeShipper);

    return this.apiHttp.get(this.constant.MAP_SHP +
                            this.constant.MAP_API +
                            this.constant.ORDER +
                            "?longitude=" + longitudeShipper +
                            "&latitude=" + latitudeShipper +
                            "&shipperToken=" + tokenDevice);
  }

  offShipperMode(){
    return this.apiHttp.delete(this.constant.MAP_SHP +
                            this.constant.MAP_API +
                            this.constant.ORDER);
  }

  checkOutOrder(orderID, face){
    let formData: FormData = new FormData();
    formData.append("orderId", orderID);
    formData.append("face", face);

    return this.apiHttp.put(this.constant.MAP_ANY +
                            this.constant.MAP_API +
                            this.constant.ORDER +
                            this.constant.CHECKOUT, formData);
  }

  getListOrder(){
    return this.apiHttp.get(this.constant.MAP_SHP + 
                            this.constant.MAP_API +
                            this.constant.ORDER + 
                            "/history");
  }

  getOrderDetailById(orderID){
    return this.apiHttp.get( this.constant.MAP_SHP +
                             this.constant.MAP_API +
                             this.constant.ORDER +
                             "/detail?orderId=" + orderID);
  }

  takeBill(orderId, bill){
    let formData : FormData = new FormData();
    formData.append("orderId", orderId);
    formData.append("bill", bill);

    return this.apiHttp.put(this.constant.MAP_ANY +
                            this.constant.MAP_API +
                            this.constant.ORDER +
                            "/bill", formData);
  }

  getCurrentOrder(){
    return this.apiHttp.get(this.constant.MAP_LOG +
                            this.constant.MAP_API +
                            this.constant.ORDER +
                            "/current");
  }
}
