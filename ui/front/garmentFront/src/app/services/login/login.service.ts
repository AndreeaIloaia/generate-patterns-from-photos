import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserLogin } from 'src/app/model/userLogin';
import { URL_LOCAL } from '../utils'

const URL : string = URL_LOCAL + 'auth/';
@Injectable({
  providedIn: 'root'
})
export class LoginService {
  constructor(private http: HttpClient) { }

  login(user: UserLogin): Observable<any> {
    console.log('blabla login');
    const url = `${URL}login`;
    return this.http.post<any>(url, user);
  }
}
