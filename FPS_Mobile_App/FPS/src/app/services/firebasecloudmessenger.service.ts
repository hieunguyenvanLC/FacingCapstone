import { Injectable } from '@angular/core';
import { ApihttpService } from './apihttp.service';
import { Platform } from '@ionic/angular';

@Injectable()
export class FirebasecloudmessengerService {

  constructor(
    private platform : Platform,
  ) { }

}
