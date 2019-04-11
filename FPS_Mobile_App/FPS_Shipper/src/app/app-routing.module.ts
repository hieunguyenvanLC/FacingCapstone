import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: 'home', loadChildren: './screens/home/home.module#HomePageModule'},
  {path: 'list', loadChildren: './screens/list/list.module#ListPageModule'},
  { path: 'login', loadChildren: './screens/login/login.module#LoginPageModule' },
  { path: 'registor', loadChildren: './screens/registor/registor.module#RegistorPageModule' },
  { path: 'payment', loadChildren: './screens/payment/payment.module#PaymentPageModule' },
  { path: 'profile', loadChildren: './screens/profile/profile.module#ProfilePageModule' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
