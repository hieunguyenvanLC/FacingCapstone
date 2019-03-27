import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import {Http, Headers, RequestOptions} from '@angular/http';
import {ApihttpService} from "./apihttp.service";
import {Constant} from './../common/constant';


@Injectable()
export class AccountService {

  constructor(
    public HttpClient : HttpClient,
    private router: Router,
    public http : Http ,
    private apiHttpService : ApihttpService,
    public constant : Constant,
  ) { }


  sendLogin(txtPhone, txtPassword){
    let formData: FormData = new FormData();
    formData.append('phoneNumber', txtPhone);
    formData.append('password', txtPassword);
    //return this.http.post(this.loginApiUrl , formData);
    return this.apiHttpService.post(this.constant.LOGIN, formData);
  }

  sendcreate( txtPhone, txtPassword, txtFullName, image ): Observable<any> {
    // var headers = new Headers();
    // headers.append("Content-Type", "application/json; chartset=utf-8");
    let formData :FormData = new FormData();
    formData.append('phoneNumber', txtPhone);
    formData.append('password', txtPassword);
    formData.append('fullName', txtFullName);
    formData.append('face', image);
    //return this.HttpClient.post(this.createApi , formData);
    return this.apiHttpService.post(this.constant.MAP_ANY + this.constant.MAP_API + this.constant.ACCOUNT,formData);
  }

  updateImageMember(image){
    return this.apiHttpService.put(this.constant.MAP_MEM + 
                                   this.constant.MAP_API +
                                   this.constant.ACCOUNT +
                                   "/face", image);
  }

  getDetailUser(){
    return this.apiHttpService.get(this.constant.MAP_MEM +
                                   this.constant.MAP_API +
                                   this.constant.ACCOUNT +
                                   "/detail");
  }

}
