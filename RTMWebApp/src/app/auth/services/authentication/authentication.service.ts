import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, map, Observable} from "rxjs";
import {User} from "../../../client/models/user/user";
import {environment} from "../../../../environments/environment";
import {LoginPayload} from "../../models/login-payload";
import {JwtAuthResponse} from "../../models/JwtAuthResponse";
import {LocalStorageService} from "ngx-webstorage";
import {RegisterPayload} from "../../models/RegisterPayload";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private url = environment.apiUrl + '/api/v1/auth/';

  constructor(private httpClient: HttpClient,
              private localStorageService: LocalStorageService) {
  }

  login(loginPayload: LoginPayload): Observable<boolean> {
    return this.httpClient.post<JwtAuthResponse>(this.url + 'signup', loginPayload).pipe(map(data => {
      this.localStorageService.store('authenticationToken', data.authenticationToken);
      this.localStorageService.store('username', data.username);
      return true;
    }));
  }

  register(registerPayload: RegisterPayload): Observable<any> {
    return this.httpClient.post(this.url + 'signup', registerPayload);
  }

  isAuthenticated(): boolean {
    return this.localStorageService.retrieve('username') != null;
  }

  logout() {
    this.localStorageService.clear('authenticationToken');
    this.localStorageService.clear('username');
  }
}
