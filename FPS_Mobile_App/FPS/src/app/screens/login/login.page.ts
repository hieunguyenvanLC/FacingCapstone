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
    // this.phonenumber = 'azx';
    this.phonenumber = '222';
    this.password = 'zzz';

  }

  async login() {
    this.accountService.sendLogin(this.phonenumber, this.password).subscribe(res => {
      console.log(this.phonenumber + "  " + this.password);
      console.log(res);
      //let body = res.json();  // If response is a JSON use json()
      this.account.push(res);
      if (this.account) {
        if (this.account[0].data !== "Error") {
          this.router.navigateByUrl("home");
        }
      } else {
        this.error = "Wrong username or password";
      }
    }), err => {
      console.log(err);
    };;

  }


  signUp() {
    this.router.navigateByUrl("registor");

  }

}
