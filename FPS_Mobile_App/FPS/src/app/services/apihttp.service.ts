import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class ApihttpService {
  apiUrl = "http://localhost:8080/";
  constructor(
    private http: HttpClient
  ) {   }

  
  createHeader() {
    let token = localStorage.getItem('token');
    const headers = new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': '' + token + '' });
    return headers
  }

  put(url, data){
    let headers = this.createHeader();
    return this.http.put(this.apiUrl + url, data, {
      headers: headers
    });
  }

  get(url){
    let headers = this.createHeader();
    return this.http.get(this.apiUrl + url, {
      headers: headers
    });
  }

  post(url, data){
    let headers = this.createHeader();
    console.log(data);
    return this.http.post(this.apiUrl + url, data);
  }

  delete(url): Observable<any> {
    let headers = this.createHeader();
    return this.http.delete(this.apiUrl + url, {
      headers: headers
    });
  }

  deletes(url, data): Observable<any> {
    let headers = this.createHeader();
    return this.http.post(this.apiUrl + url, data, {
      headers: headers
    });
  }
}

