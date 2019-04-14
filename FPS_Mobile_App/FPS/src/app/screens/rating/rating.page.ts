import { Component, OnInit } from '@angular/core';
import { Events } from '@ionic/angular';
import { Router, ActivatedRoute } from '@angular/router';
import { OrderService } from 'src/app/services/order.service';
import { LoadingService } from 'src/app/services/loading.service';
import { Constant } from 'src/app/common/constant';

@Component({
  selector: 'app-rating',
  templateUrl: './rating.page.html',
  styleUrls: ['./rating.page.scss'],
})
export class RatingPage implements OnInit {

  rate: any;
  orderId: any;
  constructor(
    public events: Events,
    private router: Router,
    private route: ActivatedRoute,
    private orderService: OrderService,
    private loading: LoadingService,
    private constant: Constant,
  ) {
    this.rate = 0;
    this.orderId = this.route.snapshot.params['id'];

    
  }

  ngOnInit() {
    document.getElementById("rate2").nodeValue = "checked";
  }

  starmark(rateStar) {
    this.rate = rateStar;
  }

  submitRate() {
    console.log(this.rate);
    this.loading.present(this.constant.LOADINGMSG).then(() =>
      this.orderService.rateOrder(this.orderId, this.rate).subscribe(res => {
        console.log(res);
        this.loading.dismiss();
        this.router.navigateByUrl("home");
      })//end api rate
    )//end loading

  }

}
