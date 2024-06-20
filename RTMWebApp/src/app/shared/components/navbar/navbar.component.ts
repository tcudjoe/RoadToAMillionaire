import {Component} from '@angular/core';
import {Subscription} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthenticationService} from "../../../auth/services/authentication/authentication.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {
  logoutSubscription!: Subscription;

  constructor(
    private authService: AuthenticationService,
    private router: Router,
    private route: ActivatedRoute,
    private toastr: ToastrService
  ) {
  }

  logout() {
    this.authService.logout();
    if (!this.authService.isAuthenticated()) {
      this.toastr.success(' You\'re being redirected to the login page...', 'You have been successfully logged out!', {
        timeOut: 2750
      });
      setTimeout(() => {
        this.router.navigate(['/home'])
      }, 3000);
    } else {
      this.toastr.error("Something went wrong with logging out! Please try again or contact the admin...")
    }
  }

}
