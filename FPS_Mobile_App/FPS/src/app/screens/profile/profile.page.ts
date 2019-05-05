import { Component, OnInit } from '@angular/core';
import { NavController, ToastController } from '@ionic/angular';
import { Router } from '@angular/router';
import { AccountService } from 'src/app/services/account.service';
import { LoadingService } from 'src/app/services/loading.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {

  userDetail = [];
  status_code = 0;

  constructor(
    public navCtrl: NavController,
    public router : Router,
    private accountService : AccountService,
    private loadingService : LoadingService,
    private toastController: ToastController,
  ) { }

  ngOnInit() {
    this.loadingService.present().then( () => {
      this.getUser();
    })
    
    
  }

  getUser(){
    this.accountService.getDetailUser().subscribe(
      res => {
        this.userDetail.push(res);
        console.log(this.userDetail[0].data);
        this.status_code = this.userDetail[0].status_code;

      }, error => {
        console.log(error);
      },
      () => {
        if (this.status_code === 1){
          this.loadingService.dismiss();
        }else{
          //handle error
        }
      }
    );
  }

  goToHome(){
    this.router.navigateByUrl("home");
  }
}
