import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../../services/authentication/authentication.service";
import {Router} from "@angular/router";
import {FormControl, FormGroup} from "@angular/forms";
import {LoginPayload} from "../../models/login-payload";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  fieldTextType?: boolean;
  loginPayload: LoginPayload = {
    username: '',
    password: ''
  };

  constructor(
    private authService: AuthenticationService,
    private router: Router,
  ) {
    this.loginForm = new FormGroup({
      username: new FormControl(),
      password: new FormControl()
    })
  }


  ngOnInit() {

  }

  toggleFieldTextType() {
    this.fieldTextType = !this.fieldTextType;
  }

  // authenticate() {
  //   this.authService.login(this.authenticationRequest)
  //     .subscribe({
  //       next: (response) => {
  //         this.authResponse = response;
  //         if (!this.authResponse.mfaEnabled) {
  //           localStorage.setItem('token', response.accessToken as string)
  //
  //           if (this.authenticationRequest.role === 'USER') {
  //             this.router.navigate(['/client/dashboard']);
  //           } else if (this.authenticationRequest.role === 'ADMIN') {
  //             this.router.navigate(['/admin/dashboard'])
  //           } else if (this.authenticationRequest.role === 'MANAGER') {
  //             this.router.navigate(['/manager/dashboard'])
  //           } else {
  //             this.router.navigate(['/client/dashboard']);
  //           }
  //         }
  //       }
  //     })
  // }


  onSubmit() {
    this.loginPayload.username = this.loginForm.get('username')?.value;
    this.loginPayload.username = this.loginForm.get('password')?.value;

    this.authService.login(this.loginPayload).subscribe(data => {
      if (data) {
        console.log('successfull login');
        this.router.navigateByUrl('admin/dashboard');
      } else {
        console.log('error wit login');
      }
    });
  }
}
