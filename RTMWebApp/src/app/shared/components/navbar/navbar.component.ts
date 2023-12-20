import { Component } from '@angular/core';
import {Subscription} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthenticationService} from "../../../auth/services/authentication/authentication.service";

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


    private route: ActivatedRoute
  ) {
  }
  logout() {
    this.logoutSubscription = this.authService.logout().subscribe(() => {
    });
    localStorage.clear();
    this.router.navigateByUrl("/auth/login");
  }

}
