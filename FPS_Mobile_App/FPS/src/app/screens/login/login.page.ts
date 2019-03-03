import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { HttpClient } from '@angular/common/http';
import { AccountService } from '../../services/account.service';
// import * as $ from 'jquery';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  phonenumber: string;
  password: string;
  error: string;

  constructor(
    private router: Router,
    private accountService : AccountService,
  ) { }

  ngOnInit() {
    this.phonenumber = "azx";
    this.password = "zzz";

  }

  async login() {
    var result =  this.accountService.sendLogin(this.phonenumber, this.password);
    console.log(result);
    if(result === "success"){
      this.router.navigateByUrl("home");
    }
    // console.log("phone is : " + this.phonenumber + " - " + "password is : " + this.password);
    // if (this.phonenumber == "admin" && this.password == "admin") {
    //   this.router.navigateByUrl("home");
    // } else {
    //   this.error = "Username or password is not correct !"
    // }

  }

  signUp() {
    this.router.navigateByUrl("registor");

  }

}
