import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/model/user';
import { URL_LOCAL } from '../utils';

const URL : string = URL_LOCAL + 'auth/';


@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http: HttpClient) { }

  register(user: User): Observable<any> {
    const url = `${URL}signup`;
    return this.http.post<any>(url, user);
  }
}
