import { Component } from '@angular/core';
import { RegisterRequest } from "../../models/registerRequest/register-request";
import { AuthenticationResponse } from "../../models/authentication-response/authentication-response";
import { VerificationRequest } from "../../models/verificationRequest/verification-request";
import { AuthenticationService } from "../../services/authentication/authentication.service";
import { Router } from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  registerRequest: RegisterRequest = {};
  authResponse: AuthenticationResponse = {};
  message: string = '';
  otpCode = '';

  constructor(
    private authService: AuthenticationService,
    private router: Router
  ) {}

  registerUser() {
    this.message = '';
    this.authService.register(this.registerRequest).subscribe({
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
      },
      error: (error) => {
        // Display error message
        this.message = 'Error: ' + error.message;
      }
    });
  }

  verifyTfa() {
    this.message = '';
    const verifyRequest: VerificationRequest = {
      email: this.registerRequest.email,
      code: this.otpCode
    };
    this.authService.verifyCode(verifyRequest).subscribe({
      next: (response) => {
        // Display success message and redirect to the home page after a delay
        this.message = 'Account created successfully!\n You will be redirected to the home page.';
        setTimeout(() => {
          localStorage.setItem('token', response.accessToken as string);
          this.router.navigate(['/client/dashboard']);
        }, 3000);
      },
      error: (error) => {
        // Display error message
        this.message = 'Error: ' + error.message;
      }
    });
  }
}
