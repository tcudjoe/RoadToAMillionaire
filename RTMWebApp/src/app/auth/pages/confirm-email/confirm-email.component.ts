import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-confirm-email',
  templateUrl: './confirm-email.component.html',
  styleUrls: ['./confirm-email.component.scss']
})
export class ConfirmEmailComponent implements OnInit {
  verificationFailed: boolean | undefined;

  constructor(private route: ActivatedRoute,
              private http: HttpClient,
              private router: Router) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const token = params['token'];
      if (token) {
        this.verifyEmail(token);
      } else {
        // handle missing token scenario
        console.error('No token found in query parameters');
      }
    });
  }

  verifyEmail(token: string): void {
    this.http.get(`${environment.apiUrl}/api/v1/auth/verify?token=${token}`)
      .subscribe(
        response => {
          // handle successful verification
          this.verificationFailed = false;
          console.log('Email verified successfully', response);
          this.router.navigate(['/auth/login']); // Redirect to login or another page
        },
        error => {
          this.verificationFailed = true;
          // handle verification error
          console.error('Email verification failed', error);
        }
      );
  }
}
