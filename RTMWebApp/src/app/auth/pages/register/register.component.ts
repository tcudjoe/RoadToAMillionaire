import {Component} from '@angular/core';
import {RegisterRequest} from "../../models/registerRequest/register-request";
import {AuthenticationResponse} from "../../models/authentication-response/authentication-response";
import {VerificationRequest} from "../../models/verificationRequest/verification-request";
import {AuthenticationService} from "../../services/authentication/authentication.service";
import {Router} from "@angular/router";
import {isEmpty} from "rxjs";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  message: string = '';
  otpCode = '';
  registrationStep: number = 1;
  registerRequest: RegisterRequest = {};
  authResponse: AuthenticationResponse = {};
  protected readonly isEmpty = isEmpty;

  constructor(
    private authService: AuthenticationService,
    private router: Router
  ) {
  }


  nextStep() {
    this.message = '';
    console.log(`MfaEnabled = ${this.registerRequest.mfaEnabled}, ImageUri = ${this.authResponse.secretImageUri}`)
    this.registrationStep++;
  }

  prevStep() {
    this.message = '';
    this.registrationStep--;
  }

  registerUser() {
    this.message = '';

    if (this.registrationStep === 1) {
      this.nextStep()
    }
    this.authService.register(this.registerRequest).subscribe({
      next: (response: AuthenticationResponse) => {
        if (response) {
          this.authResponse = response
        } else {
          if (this.authResponse.mfaEnabled) {
            this.message = "Redirecting to enable 2FA authentication"
          } else {
            this.message = "Account created successfully! You will be redirected to the login page."
            // setTimeout(() => {
            //     this.router.navigate(['/auth/login'])
            //   },
            //   3000)
          }
        }
      },
      error: (error) => {
        // Display error message
        this.message = 'Error: ' + error.message;
      }
    });
  }

  enableTwoFactorAuthentication() {
    this.registerRequest.mfaEnabled = true;
    this.registerUser()
    this.nextStep();
  }

  navigate() {
    this.router.navigate(['/auth/login'])
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
        this.message = 'Account created successfully!\n You will be redirected to the login page.';
        setTimeout(() => {
          localStorage.setItem('token', response.accessToken as string);
          this.router.navigate(['/auth/login']);
        }, 3000);
      },
      error: (error) => {
        // Display error message
        this.message = 'Error: ' + error.message;
      }
    });
  }
}
