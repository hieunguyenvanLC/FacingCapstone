import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Constant } from './../common/constant';
@Injectable({
  providedIn: 'root'
})
export class ApihttpService {
  constructor(
    private http: HttpClient,
    private constant: Constant,
  ) {   }

  
  createHeader() {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json'});

    return headers
  }

  put(url, data){
    let headers = this.createHeader();
    return this.http.put(this.constant.APIURL + url, data);
  }

  get(url){
    let headers = this.createHeader();
    return this.http.get(this.constant.APIURL + url , { withCredentials: true }
    );
  }
  // , {
  //   headers: headers
  // }

  post(url, data){
    let headers = this.createHeader();
    console.log(data);
    return this.http.post(this.constant.APIURL + url, data, { withCredentials: true });
  }

  delete(url): Observable<any> {
    let headers = this.createHeader();
    return this.http.delete(this.constant.APIURL + url, { withCredentials: true });
  }

  deletes(url, data): Observable<any> {
    let headers = this.createHeader();
    return this.http.post(this.constant.APIURL + url, data, { withCredentials: true });
  }
}

