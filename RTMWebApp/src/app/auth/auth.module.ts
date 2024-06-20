import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './pages/login/login.component';
import { ResetPasswordComponent } from './pages/reset-password/reset-password.component';
import { RegisterComponent } from './pages/register/register.component';
import {AuthRoutingModule} from "./auth-routing.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { ConfirmEmailComponent } from './pages/confirm-email/confirm-email.component';



@NgModule({
  declarations: [
    LoginComponent,
    ResetPasswordComponent,
    RegisterComponent,
    ConfirmEmailComponent
  ],
    imports: [
        CommonModule,
        AuthRoutingModule,
        FormsModule,
        ReactiveFormsModule
    ]
})
export class AuthModule { }
