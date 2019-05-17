import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Account } from '../models/account';
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

  sendcreate( txtPhone, txtPassword, txtFullName, face1, face2, face3, ppUsername, ppPassword ): Observable<any> {
    // var headers = new Headers();
    // headers.append("Content-Type", "application/json; chartset=utf-8");
    console.log("face 3 account service");
    console.log(face3);
    let formData :FormData = new FormData();
    formData.append('phoneNumber', txtPhone);
    formData.append('password', txtPassword);
    formData.append('fullName', txtFullName);
    formData.append('face1', face1);
    formData.append('face2', face2);
    formData.append('face3', face3);
    formData.append('payUsername', ppUsername);
    formData.append('payPassword', ppPassword);
    //return this.HttpClient.post(this.createApi , formData);
    return this.apiHttpService.post(this.constant.MAP_ANY + this.constant.MAP_API + this.constant.ACCOUNT,formData);
  }

  updateImageMember(id,image){
    let formData: FormData = new FormData();
    formData.append('revMemId', id);
    formData.append('face', image);
    return this.apiHttpService.put(this.constant.MAP_MEM + 
                                   this.constant.MAP_API +
                                   this.constant.ACCOUNT +
                                   "/face", formData);
  }

  getDetailUser(){
    return this.apiHttpService.get(this.constant.MAP_MEM +
                                   this.constant.MAP_API +
                                   this.constant.ACCOUNT +
                                   "/detail");
  }

  logOut(){
    return this.apiHttpService.get(this.constant.LOGOUT);
  }

  updateMemberFace(memId, memName, face1, face2, face3){
    let formData : FormData = new FormData();
    formData.append("revMemId", memId);
    formData.append("revMemName", memName);
    formData.append("face1", face1);
    formData.append("face2", face2);
    formData.append("face3", face3);
    return this.apiHttpService.put(this.constant.MAP_MEM + 
                                   this.constant.MAP_API + 
                                   this.constant.ACCOUNT +
                                   this.constant.FACE, formData);
  }

  updateMemberDetail(name, email, dob){
    let formData : FormData = new FormData();
    formData.append("name", name);
    formData.append("email", email);
    formData.append("dob", dob);
    return this.apiHttpService.put(this.constant.MAP_MEM + 
                                   this.constant.MAP_API + 
                                   this.constant.ACCOUNT, formData);
  }

  getAvatar(){
    return this.apiHttpService.get(this.constant.MAP_MEM +
                                   this.constant.MAP_API +
                                   this.constant.ACCOUNT +
                                   "/avatar");
  }

  depositToWallet(money_VND){
    let formData : FormData = new FormData();
    formData.append("amount", money_VND);
    return this.apiHttpService.post(this.constant.MAP_MEM + 
                                   this.constant.MAP_API + 
                                   this.constant.ACCOUNT +
                                   '/deposit', formData);
  }

  updateAvatar(avatar){
    let formData : FormData = new FormData();
    formData.append("avatar", avatar);
    return this.apiHttpService.put(this.constant.MAP_MEM + 
                                   this.constant.MAP_API + 
                                   this.constant.ACCOUNT +
                                   '/avatar', formData);
  }

}
