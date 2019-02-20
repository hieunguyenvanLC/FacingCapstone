import { Component, OnInit, Input } from '@angular/core';
import { NavParams } from '../../../node_modules/@ionic/angular';

@Component({
  selector: 'app-ordermodal',
  templateUrl: './ordermodal.page.html',
  styleUrls: ['./ordermodal.page.scss'],
})
export class OrdermodalPage implements OnInit {
  @Input() value: number;
  constructor(navParams: NavParams) {
    // componentProps can also be accessed at construction time using NavParams
  }
  ngOnInit() {
  }

}
