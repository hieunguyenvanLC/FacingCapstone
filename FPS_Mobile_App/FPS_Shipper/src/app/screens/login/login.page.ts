import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AccountService } from '../../services/account.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  phonenumber: string;
  password: string;
  error: string;

  account = [];

  constructor(
    private router: Router,
    private accountService: AccountService,
  ) { }

  ngOnInit() {
    this.phonenumber = '84098734455';
    this.password = 'zzz';

  }

  public result = '';

  async login() {
    this.accountService.sendLogin(this.phonenumber, this.password).subscribe(res => {
      //console.log(this.phonenumber + "  " + this.password);
      console.log(res);
      //let body = res.json();  // If response is a JSON use json()
      this.account.push(res);
      //console.log(this.account[0]._body);
      // console.log(this.account[0].data.length);
      // let role;
      // let accountID;

      // for (let index = 0; index < this.account[0].data.length; index++) {
      //   const element = this.account[0].data[index];
      //   if (index === this.account[0].data.length - 1) {
      //     accountID = element.replace("Account ID: ", "");
      //   } else {
      //     role = element.replace("Role: ", "");
      //   }

      // }
      // console.log("role : " + role + "// ID :" + accountID);
      if (this.account) {
        if (this.account[0].data === "ROLE_SHIPPER") {
          //console.log("vo home")
          //this.router.navigateByUrl("home");
          this.router.navigateByUrl("home");
        } else {
          this.error = "Wrong username or password";
        }
      }
      console.log(res);
    }), err => {
      console.log(err);
    };;

  }



}
