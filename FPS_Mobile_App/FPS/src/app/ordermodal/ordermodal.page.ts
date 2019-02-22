import { Component, OnInit, Input } from '@angular/core';
import { NavParams, ModalController } from '../../../node_modules/@ionic/angular';

@Component({
  selector: 'app-ordermodal',
  templateUrl: './ordermodal.page.html',
  styleUrls: ['./ordermodal.page.scss'],
})
export class OrdermodalPage implements OnInit {
  
  @Input() products: any;
  constructor (
    private navParams: NavParams,
    private modalController: ModalController
  ) {
    // componentProps can also be accessed at construction time using NavParams
  }
  ngOnInit() {
  }
  dismissModal() {
    this.modalController.dismiss();
  }
}
