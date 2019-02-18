import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule ,ReactiveFormsModule} from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { RegistorPage } from './registor.page';
import { InternationalPhoneModule } from 'ng4-intl-phone';
const routes: Routes = [
  {
    path: '',
    component: RegistorPage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes),
    InternationalPhoneModule,
    ReactiveFormsModule
  ],
  declarations: [RegistorPage]
})
export class RegistorPageModule {}
