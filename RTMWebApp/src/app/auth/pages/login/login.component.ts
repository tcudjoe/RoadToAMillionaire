import {Component} from '@angular/core';
import {RegisterRequest} from "../../models/registerRequest/register-request";
import {AuthenticationResponse} from "../../models/authentication-response/authentication-response";
import {AuthenticationService} from "../../services/authentication/authentication.service";
import {Router} from "@angular/router";
import {AuthenticationRequest} from "../../models/authenticationRequest/authentication-request";
import {VerificationRequest} from "../../models/verificationRequest/verification-request";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  authenticationRequest: AuthenticationRequest = {role: ""};
  authResponse: AuthenticationResponse = {};
  otpCode: any;

  constructor(
    private authService: AuthenticationService,
    private router: Router,
  ) {
  }

  authenticate() {
    this.authService.login(this.authenticationRequest)
      .subscribe({
        next: (response) => {
          this.authResponse = response;
          if (!this.authResponse.mfaEnabled) {
            localStorage.setItem('token', response.accessToken as string)

            if (this.authenticationRequest.role == 'USER') {
              this.router.navigate(['/client/dashboard']);
            } else if (this.authenticationRequest.role == 'ADMIN') {
              this.router.navigate(['/admin/dashboard'])
            } else if (this.authenticationRequest.role == 'MANAGER') {
              this.router.navigate(['/manager/dashboard'])
            } else {
              this.router.navigate(['/home'])
            }
          }
        }
      })
  }

  verifyCode() {
    const verifyRequest: VerificationRequest = {
      email: this.authenticationRequest.email,
      code: this.otpCode
    };
    this.authService.verifyCode(verifyRequest).subscribe({
      next: (response) => {
        // Display success message and redirect to the home page after a delay
        setTimeout(() => {
          localStorage.setItem('token', response.accessToken as string);
          if (this.authenticationRequest.role == 'USER') {
            this.router.navigate(['/client/dashboard']);
          } else if (this.authenticationRequest.role == 'ADMIN') {
            this.router.navigate(['/admin/dashboard'])
          } else if (this.authenticationRequest.role == 'MANAGER') {
            this.router.navigate(['/manager/dashboard'])
          } else {
            this.router.navigate(['/home'])
          }
        });
      },

    });
  }
}
