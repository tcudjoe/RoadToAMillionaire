import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./pages/login/login.component";
import {RegisterComponent} from "./pages/register/register.component";
import {ResetPasswordComponent} from "./pages/reset-password/reset-password.component";
import {ConfirmEmailComponent} from "./pages/confirm-email/confirm-email.component";

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
    title: 'RTM | Login'

  },
  {
    path: 'register',
    component: RegisterComponent,
    title: 'RTM | Register'
  },
  {
    path: 'confirm-email',
    component: ConfirmEmailComponent,
    title: 'RTM | Confirm Email'
  },
  {
    path: 'password-reset',
    component: ResetPasswordComponent,
    title: 'RTM | Reset Password'
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule {
}
