import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Account } from '../models/account';
import { Observable } from 'rxjs';
import * as $ from 'jquery';
import { Router } from '@angular/router';
import {Http, Headers, RequestOptions} from '@angular/http';



@Injectable()
export class AccountService {

  constructor(
    public HttpClient : HttpClient,
    private router: Router,
    public http : Http

  ) { }

  // apiUrl="http://localhost:8080/any/account/";

  // create(account : Account){

  //   return this.http.post(this.apiUrl, account );
  // }
  apiUrl = "http://localhost:8080/any/api/csrf";

  // createHeader() {
  //   let token = localStorage.getItem('token');
  //   // const headers = new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': '' + token + '' });
  //   const headers = new HttpHeaders({ 'Content-Type': 'application/json'});
  //   return headers
  // }
  get(): Observable<any> {
    // let headers = this.createHeader();
    return this.http.get(this.apiUrl);
  }

  loginApiUrl = "http://localhost:8080/login";

  sendLogin(txtPhone, txtPassword){
    // let headers = this.createHeader();
    // let data11 = {phoneNumber:'azx', password:'zzz'} ;
    // return this.http.post(this.loginApiUrl , data11);
    let formData: FormData = new FormData();
    formData.append('phoneNumber', txtPhone);
    formData.append('password', txtPassword);
    return this.http.post(this.loginApiUrl , formData);
    // $.ajax({
    //         type: 'POST',
    //         url: "http://localhost:8080/login",
    //         data: {phoneNumber: txtPhone, password: txtPassword},
    //         dataType: 'json',
    //         success: function (response) {
    //           console.log(response);
    //             // if (response === ROL_ADM) {
    //             //
    //             //     // document.location.href = "/adm/";
    //             // } else if (response === ROL_MEM || response === ROL_SHP) {
    //             //     // fpsShowMsg("Unable to access")
    //             // } else if (response === "Error") {
    //             //     // fpsShowMsg("Wrong username or password");
    //             // } else {
    //             //     // fpsShowMsg("Error when login");
    //             // }
    //         },
    //         error: function () {
    //           console.log(Error);
    //             // console.log(response);
    //             // fpsShowMsg("Error when login");
    //         }
    //     })
  }

  createApi ="http://localhost:8080/any/api/account";
  sendcreate( txtPhone, txtPassword, txtFullName ): Observable<any> {
    var headers = new Headers();
    headers.append("Content-Type", "application/json; chartset=utf-8");

    // let headers = this.createHeader();
    // let data11 = {phoneNumber1:'84965142724', password:'123', fullName:'Nguyen Van Hieu'} ;
    let formData: FormData = new FormData();
    formData.append('phoneNumber', txtPhone);
    formData.append('password', txtPassword);
    formData.append('fullName', txtFullName);
    // console.log(account);
    return this.HttpClient.post(this.createApi , formData);
  }

}
