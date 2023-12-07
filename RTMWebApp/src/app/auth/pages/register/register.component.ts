import {Component} from '@angular/core';
import {RegisterRequest} from "../../models/registerRequest/register-request";
import {AuthenticationResponse} from "../../models/authentication-response/authentication-response";
import {AuthenticationService} from "../../services/authentication/authentication.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  registerRequest: RegisterRequest = {}
  authResponse: AuthenticationResponse = {}
  message: string = "";

  constructor(
    private authService: AuthenticationService,
    private router: Router) {
  }

  registerUser() {
    this.message = '';
    this.authService.register(this.registerRequest)
      .subscribe({
        next: (response: AuthenticationResponse) => {
          if (response) {
            this.authResponse = response
          } else {
            this.message = "Account created successfully! You will be redirected to the login page."
            setTimeout(() => {
                this.router.navigate(['/auth/login'])
              },
              3000)
          }
        }
      })
  }
}
