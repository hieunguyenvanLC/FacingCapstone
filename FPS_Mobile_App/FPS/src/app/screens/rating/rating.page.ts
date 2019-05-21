import { Component, OnInit } from '@angular/core';
import { Events } from '@ionic/angular';
import { Router, ActivatedRoute } from '@angular/router';
import { OrderService } from 'src/app/services/order.service';
import { LoadingService } from 'src/app/services/loading.service';
import { Constant } from 'src/app/common/constant';
import { AccountService } from 'src/app/services/account.service';

@Component({
  selector: 'app-rating',
  templateUrl: './rating.page.html',
  styleUrls: ['./rating.page.scss'],
})
export class RatingPage implements OnInit {

  rate: any;
  orderId: any;
  face: any;
  name: any;
  constructor(
    public events: Events,
    private router: Router,
    private route: ActivatedRoute,
    private orderService: OrderService,
    private accountService: AccountService,
    private loading: LoadingService,
    private constant: Constant,
  ) {
    this.rate = 0;
    this.orderId = this.route.snapshot.params['id'];
    // this.orderId = 115;

    this.face = "";
    this.name = "";

  }

  ngOnInit() {
    document.getElementById("rate2").nodeValue = "checked";
    this.loading.present(this.constant.LOADINGMSG).then(() => {
      this.orderService.getFaceResult(this.orderId).subscribe(res => {
        console.log(res);
        let tempArr = [];
        tempArr.push(res);
        this.face = tempArr[0].data.face;

        if (tempArr[0].data.name === "default") {
          this.accountService.getAvatar().subscribe(res => {
            let tempArr2 = [];
            tempArr2.push(res)
            this.name = tempArr2[0].data.name;
          })//end api get avater 
        }//end if = default
        else {
          this.name = tempArr[0].data.name;
        }//end else
        this.loading.dismiss();
      })
    })//end loading
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
