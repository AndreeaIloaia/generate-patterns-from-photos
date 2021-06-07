import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { URL_LOCAL } from '../utils'

const URL : string = URL_LOCAL;

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http: HttpClient) { }

  getGarments(): Observable<any> {
    const url = `${URL}get-images`;
    return this.http.get<any>(url);
  }

}
