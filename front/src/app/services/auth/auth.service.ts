import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
// import decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  public getToken(): string {
    return sessionStorage.getItem('token');
  }
  
  public isAuthenticated(): boolean {
    // get the token
    const token = this.getToken();

    const helper = new JwtHelperService;
    console.log("AuthService returns" + !helper.isTokenExpired(token));
    return !helper.isTokenExpired(token);
  }
}
