import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-check-out',
  templateUrl: './check-out.page.html',
  styleUrls: ['./check-out.page.scss'],
})
export class CheckOutPage implements OnInit {

  orderId : any;

  constructor(
    private route: ActivatedRoute,
    private orderService : OrderService,

  ) { 
    this.orderId = this.route.snapshot.params['id'];

    //get order by id
    this.orderService.getOrderDetailById(this.orderId).subscribe(res => {
      console.log(res)

    })//end get order by id
  }

  ngOnInit() {
  }

  

}
