import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'home', loadChildren: './screens/home/home.module#HomePageModule' },
  { path: 'list', loadChildren: './screens/list/list.module#ListPageModule' },
  { path: 'login', loadChildren: './screens/login/login.module#LoginPageModule' },
  { path: 'store', loadChildren: './screens/store/store.module#StorePageModule' },
  { path: 'store/:id', loadChildren: './screens/store/store.module#StorePageModule' },
  { path: 'registor', loadChildren: './screens/registor/registor.module#RegistorPageModule' },
  { path: 'order', loadChildren: './screens/order/order.module#OrderPageModule' },
  { path: 'ordermodal', loadChildren: './screens/ordermodal/ordermodal.module#OrdermodalPageModule' },
  { path: 'profile', loadChildren: './screens/profile/profile.module#ProfilePageModule' },
  { path: 'autocomplete', loadChildren: './screens/autocomplete/autocomplete.module#AutocompletePageModule' },
  { path: 'check-out', loadChildren: './screens/check-out/check-out.module#CheckOutPageModule' },
  { path: 'check-out/:id', loadChildren: './screens/check-out/check-out.module#CheckOutPageModule' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes), HttpClientModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
