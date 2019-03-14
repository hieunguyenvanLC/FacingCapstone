import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import {Http, Headers, RequestOptions} from '@angular/http';
import {ApihttpService} from "./apihttp.service";


@Injectable()
export class AccountService {

  constructor(
    public HttpClient : HttpClient,
    private router: Router,
    public http : Http ,
    private apiHttpService : ApihttpService
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
    let formData: FormData = new FormData();
    formData.append('phoneNumber', txtPhone);
    formData.append('password', txtPassword);
    return this.http.post(this.loginApiUrl , formData);
    //return this.apiHttpService.post("login", formData);
  }
///////////////test thu
  createApi ="http://localhost:8080/any/api/account";
  sendcreate( txtPhone, txtPassword, txtFullName ): Observable<any> {
    var headers = new Headers();
    headers.append("Content-Type", "application/json; chartset=utf-8");
    // let headers = this.createHeader();
    // let data11 = {phoneNumber1:'84965142724', password:'123', fullName:'Nguyen Van Hieu'} ;
    let formData :FormData = new FormData();
    formData.append('phoneNumber', txtPhone);
    formData.append('password', txtPassword);
    formData.append('fullName', txtFullName);
    //return this.HttpClient.post(this.createApi , formData);
    console.log(formData);
    return this.apiHttpService.post("any/api/account",formData);
  }

}
