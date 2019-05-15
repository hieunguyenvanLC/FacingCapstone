import { Injectable } from '@angular/core';
import { Constant } from '../common/constant';
import { ApihttpService } from './apihttp.service';

@Injectable()
export class OrderService {

  constructor(
    private constant : Constant,
    private apihttp : ApihttpService
  ) { }

  createOrder(longitudeCus, latitudeCus, cusDescription, prodList, distance, tokenFCM, buyerAddress){
    let formData: FormData = new FormData();
    formData.append("longitude", longitudeCus);
    formData.append("latitude", latitudeCus);
    formData.append("customerDescription", cusDescription);
    formData.append("proList", prodList);
    formData.append("distance", distance);
    formData.append("deviceToken", tokenFCM);
    formData.append("buyerAddress", buyerAddress);
    return this.apihttp.post(this.constant.MAP_MEM +
                              this.constant.MAP_API +
                              this.constant.ORDER, formData);
  }

  getOrderDetailById(orderID){
    return this.apihttp.get( this.constant.MAP_MEM +
                             this.constant.MAP_API +
                             this.constant.ORDER +
                             "/detail?orderId=" + orderID);
  }
  
  cancelOrder(orderId, longitude, latitude){
    let formData : FormData = new FormData();
    formData.append("orderId", orderId);
    formData.append("longitude", longitude);
    formData.append("latitude", latitude);

    return this.apihttp.deletes(this.constant.MAP_MEM + 
                                this.constant.MAP_API + 
                                this.constant.ORDER, formData);
  }

  rateOrder(orderId,rating){
    let formData : FormData = new FormData();
    formData.append("orderId", orderId);
    formData.append("rating", rating);

    return this.apihttp.put(this.constant.MAP_MEM + 
                            this.constant.MAP_API +
                            this.constant.ORDER +
                            "/rate", formData);
  }

  getListOrder(){
    return this.apihttp.get(this.constant.MAP_MEM + 
                            this.constant.MAP_API +
                            this.constant.ORDER + 
                            "/history");
  }

  getCurrentOrder(){
    return this.apihttp.get(this.constant.MAP_LOG +
                            this.constant.MAP_API +
                            this.constant.ORDER +
                            "/current");
  }
}
