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
  { path: 'ordermodal', loadChildren: './screens/ordermodal/ordermodal.module#OrdermodalPageModule' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes), HttpClientModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
