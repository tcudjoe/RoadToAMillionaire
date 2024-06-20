import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LayoutComponent} from "./components/layout/layout.component";
import {HomeComponent} from "./pages/home/home.component";
import {authGuard} from "../auth/services/authGuard";

const routes: Routes = [{
  path: '', component: LayoutComponent,
  children: [
    {
      path: '',
      redirectTo: 'dashboard',
      pathMatch: 'full',
      title: 'Road to a Millionaire | Home',
    },
    {
      path: 'dashboard',
      component: HomeComponent,
      title: 'RTM | Dashboard',
      canActivate: [authGuard]
    }

  ]
}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClientRoutingModule { }
