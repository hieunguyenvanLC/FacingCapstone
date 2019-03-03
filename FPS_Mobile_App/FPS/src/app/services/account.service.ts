import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Account } from '../models/account.model';
import { Observable } from 'rxjs';

@Injectable()
export class AccountService {

  constructor(
    public http : HttpClient,
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
  // get():Observable<any>{
  //   return this.http.get(this.apiUrl);
  // }
}
