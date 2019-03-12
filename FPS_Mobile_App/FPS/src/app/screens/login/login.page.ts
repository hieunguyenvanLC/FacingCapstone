import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AccountService } from '../../services/account.service';
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
    private accountService: AccountService,
  ) { }

  ngOnInit() {
    this.phonenumber = 'azx';
    this.password = 'zzz';

  }

  public result = '';

  async login() {
    this.accountService.sendLogin(this.phonenumber, this.password).subscribe(res => {
      console.log(this.phonenumber + "  " + this.password);
      console.log(res);
      let body = res.json();  // If response is a JSON use json()
      if (body) {
        if (body !== "Error") {
          this.router.navigateByUrl("home");
        }
      } else {
        this.error = "Wrong username or password";
      }
      console.log(res);
    }), err => {
      console.log(err);
    };;

  }


  signUp() {
    this.router.navigateByUrl("registor");

  }

}
