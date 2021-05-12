import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { URL_LOCAL } from '../utils'

const URL : string = URL_LOCAL;

@Injectable({
  providedIn: 'root'
})
export class PatternService {

  constructor(private http: HttpClient) { }

  draw(fileName: string): Observable<any> {
    const url = `${URL}patterns/${fileName}`;
    return this.http.get<any>(url);
  }

  postFile(fileToUpload: File, typeGarment: string): Observable<any> {
    const url = `${URL}files/upload/${typeGarment}`;
    const formData: FormData = new FormData();
    formData.append('file', fileToUpload);
    return this.http.post(url, formData);
  }
  
}
