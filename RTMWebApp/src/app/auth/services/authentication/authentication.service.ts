import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RegisterRequest} from "../../models/registerRequest/register-request";
import {AuthenticationResponse} from "../../models/authentication-response/authentication-response";
import {VerificationRequest} from "../../models/verificationRequest/verification-request";
import {AuthenticationRequest} from "../../models/authenticationRequest/authentication-request";
import {BehaviorSubject, Observable} from "rxjs";
import {User} from "../../../client/models/user/user";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  public userSubject = new BehaviorSubject<User | null>(null);
  public isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  private baseUrl = 'http://localhost:8080/api/v1/auth'

  constructor(
    private http: HttpClient,
  ) {
  }

  public get user$(): Observable<User | null> {
    return this.userSubject.asObservable();
  }

  public get isAuthenticated$(): Observable<boolean> {
    return this.isAuthenticatedSubject.asObservable();
  }

  register(registerRequest: RegisterRequest) {
    return this.http.post<AuthenticationResponse>(`${this.baseUrl}/register`, registerRequest)
  }

  login(authRequest: AuthenticationRequest) {
    return this.http.post<AuthenticationResponse>(`${this.baseUrl}/authenticate`, authRequest)
  }

  logout() {
    return this.http.post<void>(`${this.baseUrl}/logout`, {})
  }

  verifyCode(verificationRequest: VerificationRequest) {
    return this.http.post<AuthenticationResponse>(`${this.baseUrl}/verify`, verificationRequest)
  }

  public getCurrentUser(): User | null {
    const userJson = localStorage.getItem('user');
    if (userJson) {
      return JSON.parse(userJson);
    }
    return null;
  }
}
