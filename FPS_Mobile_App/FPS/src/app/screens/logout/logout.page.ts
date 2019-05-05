import { Component, OnInit } from '@angular/core';
import { AccountService } from 'src/app/services/account.service';
import { Router } from '@angular/router';
import { StorageApiService } from 'src/app/services/storage-api.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.page.html',
  styleUrls: ['./logout.page.scss'],
})
export class LogoutPage implements OnInit {

  constructor(
    private accountService : AccountService,
    private router : Router,
    private storage: StorageApiService,
  ) { 
    this.accountService.logOut().subscribe(res => {
      console.log(res);
      
      this.storage.clear();
      
      this.router.navigateByUrl("login");
    })
  }

  ngOnInit() {
  }

}
