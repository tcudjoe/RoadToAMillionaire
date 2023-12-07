import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RegisterRequest} from "../../models/registerRequest/register-request";
import {AuthenticationResponse} from "../../models/authentication-response/authentication-response";
import {VerificationRequest} from "../../models/verificationRequest/verification-request";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private baseUrl = 'http://localhost:8080/api/v1/auth'

  constructor(
    private http: HttpClient,
  ) { }

  register(registerRequest: RegisterRequest){
    return this.http.post<AuthenticationResponse>(`${this.baseUrl}/register`, registerRequest)
  }

  verifyCode(verificationRequest: VerificationRequest){
    return this.http.post<AuthenticationResponse>(`${this.baseUrl}/verify`, verificationRequest)
  }
}
