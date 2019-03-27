import { Injectable } from '@angular/core';
import { Constant } from '../common/constant';
import { ApihttpService } from './apihttp.service';

@Injectable()
export class OrderService {

  constructor(
    private constant : Constant,
    private apihttp : ApihttpService
  ) { }

  createOrder(longitudeCus, latitudeCus, cusDescription, prodList, distance){
    let formData: FormData = new FormData();
    formData.append("longitude", longitudeCus);
    formData.append("latitude", latitudeCus);
    formData.append("customerDescription", cusDescription);
    formData.append("proList", prodList);
    formData.append("distance", distance);
    return this.apihttp.post(this.constant.MAP_MEM +
                              this.constant.MAP_API +
                              this.constant.ORDER, formData);
  }

  getOrderStatus(orderID){
    return this.apihttp.get( this.constant.MAP_MEM +
                             this.constant.MAP_API +
                             this.constant.ORDER +
                             "/detail?orderId=" + orderID);
  }
}
