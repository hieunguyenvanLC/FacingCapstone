import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { HttpClient } from '@angular/common/http';
import * as $ from 'jquery';

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
  ) { }

  ngOnInit() {
    this.phonenumber = "admin";
    this.password = "admin";

    $('body').addClass('df');
  }

  async login() {
    console.log("phone is : " + this.phonenumber + " - " + "password is : " + this.password);
    if (this.phonenumber == "admin" && this.password == "admin") {
      this.router.navigateByUrl("home");
    } else {
      this.error = "Username or password is not correct !"
    }

  }

  signUp() {
    this.router.navigateByUrl("registor");
  }

}
