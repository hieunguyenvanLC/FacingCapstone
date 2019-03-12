import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http } from '@angular/http';
import { Observable } from 'rxjs';
import { Store } from '../models/store.model';


@Injectable()
export class StoreService {

  constructor(
    private httpClient : HttpClient,
    public http : Http
  ) { }

  storeUrl = "http://localhost:8080/adm/api/store";

  // getList(){
  //   return this.http.get(this.storeUrl);
  // }

  createHeader() {
    let token = localStorage.getItem('token');
    // const headers = new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': '' + token + '' });
    const headers = new HttpHeaders({ 'Content-Type': 'application/json'});
    return headers;
  }
  getList(){
    let headers = this.createHeader();
    return this.httpClient.get<Store[]>(this.storeUrl);
  }

  //get store by id
  getStorebyid(id: number){
    let headers = this.createHeader();
    return this.httpClient.get(this.storeUrl + "/detail?storeId=" + id);
  }

}
