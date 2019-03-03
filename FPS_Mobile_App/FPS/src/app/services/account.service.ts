import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Account } from '../models/account.model';
import { Observable } from 'rxjs';
import * as $ from 'jquery';
import { Router } from '@angular/router';


@Injectable()
export class AccountService {

  constructor(
    public http : HttpClient,
    private router: Router,

  ) { }

  //apiUrl="http://localhost:8080/any/account/";

  // create(account : Account){

  //   return this.http.post(this.apiUrl, account );
  // }
  apiUrl = "http://localhost:8080/any/api/csrf";

  createHeader() {
    let token = localStorage.getItem('token');
    const headers = new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': '' + token + '' });
    return headers
  }
  get(): Observable<any> {
    let headers = this.createHeader();
    return this.http.get(this.apiUrl, {
      headers: headers
    });
  }

  sendLogin(txtPhone, txtPassword) {
    console.log(txtPhone +"          pass:"+ txtPassword);
    var  fpsBackEnd =  "http://localhost:8080";
    var result = '';
         $.ajax({
           type: 'POST',
           url: fpsBackEnd+ "/login",
           data: {phoneNumber: txtPhone, password: txtPassword},
           dataType: 'json',
           success: function (response) {
               if (response === "ROLE_ADMIN") {
                 console.log("admin");
                 return "success";


               } else if (response === "ROLE_MEMBER" || response === "ROLE_SHIPPER") {
                    console.log("member");
                      return "success";
               } else if (response === "Error") {
                    console.log("Wrong username or password");
                     return "fail";
               } else {
                  console.log("Wrong username or password");
                   return "fail";
               }
           },
           error: function () {
               // console.log(response);
                 console.log("error function");
                  return "fail";
           }
       })

   }
  // get():Observable<any>{
  //   return this.http.get(this.apiUrl);
  // }
}
