import { Component, OnInit } from '@angular/core';
import { OrderService } from 'src/app/services/order.service';
import { LoadingService } from 'src/app/services/loading.service';
import { ToasthandleService } from 'src/app/services/toasthandle.service';
import { Constant } from 'src/app/common/constant';
import { isLoaded } from 'google-maps';
import { Router } from '@angular/router';

@Component({
  selector: 'app-order-history',
  templateUrl: './order-history.page.html',
  styleUrls: ['./order-history.page.scss'],
})
export class OrderHistoryPage implements OnInit {

  listOrder = [];
  isLoaded = false;

  constructor(
    private orderService: OrderService,
    private loading: LoadingService,
    private toastHandle: ToasthandleService,
    private constant: Constant,
    private router : Router,
  ) {
    this.listOrder = [];

    
  }

  ngOnInit() {
    this.loading.present(this.constant.LOADINGMSG).then(() => {
      this.orderService.getListOrder().subscribe(
        res => {
          console.log(res);
          this.isLoaded = true;
          this.listOrder.push(res);
          let date : Date = new Date();
          console.log(date.toLocaleDateString())
          
          this.listOrder[0].data.forEach(element => {
            element["total"] = element.totalPrice + element.shipperEarn;
            // date = new Date(element.createTime);
            element["createDate"] = new Date(element.createTime).toLocaleDateString();
          });

          console.log(this.listOrder[0].data)
          this.loading.dismiss();
        }
      )//end api get list order
    })//end loading
  }

  prepareData(){
    
  }

  back(){
    this.router.navigateByUrl("home");
  }
}
