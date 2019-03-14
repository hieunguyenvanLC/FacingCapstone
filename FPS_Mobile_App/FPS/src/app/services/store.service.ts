import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http } from '@angular/http';
import { Observable } from 'rxjs';
import { Store } from '../models/store.model';
import { ApihttpService } from './apihttp.service';
import { Constant } from '../common/constant';


@Injectable()
export class StoreService {

  constructor(
    private httpClient : HttpClient,
    public http : Http,
    private apihttp : ApihttpService,
    private constant : Constant,
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
  getList(longitude, latitude){
    return this.apihttp.get(this.constant.MAP_MEM + 
                            this.constant.MAP_API + 
                            this.constant.STORE + 
                            "?longitude=" + longitude + "&latitude=" + latitude);
  }

  //get store by id
  getStorebyid(id: number){
    let headers = this.createHeader();
    return this.apihttp.get(this.constant.MAP_MEM + 
                            this.constant.MAP_API + 
                            this.constant.STORE + "/detail?storeId=" + id);
  }

}
