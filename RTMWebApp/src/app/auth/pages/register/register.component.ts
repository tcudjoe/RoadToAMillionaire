import {Component} from '@angular/core';
import {AuthenticationService} from "../../services/authentication/authentication.service";
import {Router} from "@angular/router";
import {isEmpty} from "rxjs";
import {RegisterPayload} from "../../models/RegisterPayload";
import {FormBuilder} from "@angular/forms";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  message: string = '';
  otpCode = '';
  registrationStep: number = 1;
  registerPayload: RegisterPayload;
  protected readonly isEmpty = isEmpty;

  constructor(
    private authService: AuthenticationService,
    private router: Router,
    private formBuilder: FormBuilder,
    private toastr: ToastrService
  ) {
    this.formBuilder.group({
      email: '',
      firstName: '',
      lastName: '',
      password: '',
      confirmPassword: '',
    });
    this.registerPayload = {
      email: '',
      firstName: '',
      lastName: '',
      password: '',
      confirmPassword: '',
    };
  }

  nextStep() {
    this.message = '';
    this.registrationStep++;
  }

  prevStep() {
    this.message = '';
    this.registrationStep--;
  }

  registerUser() {
    this.message = '';

    this.authService.register(this.registerPayload).subscribe(data => {
      this.toastr.success(' You\'re being redirected to the login page...', 'You have been successfully logged out!', {
        timeOut: 2750
      });
      setTimeout(() => {
        this.router.navigate(['/auth/login'])
      }, 3000);
    }, err => {
      this.toastr.error("Something went wrong with logging out! Please try again or contact the admin...")
    });
  }

  navigate() {
    this.router.navigate(['/auth/login'])
  }
}
