import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { URL_LOCAL } from '../utils'
import { Graph } from 'src/app/model/graph';

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

  getType(fileToUpload: File[]): Observable<any> {
    const url = `${URL}upload`;
    const formData: FormData = new FormData();
    fileToUpload.forEach(element => {
      formData.append('file', element);      
    });
    console.log("form DATA");
    console.log(formData);
    // formData.append('file', fileToUpload);
    return this.http.post(url, formData);
  }

  send3DGraph(graph: Graph, number: number): Observable<any> { 
    var url;
    if (number === 1) {
      url = `${URL}get-3d-graph`;
      return this.http.post(url, graph);
    } else if (number === 2) {
      url = 'http://127.0.0.1:5000/get-prediction';
      return this.http.post(url, graph);
    }
    // return this.http.post(url, graph);
  }
  
  load3DGraph(id_garment: string, number: string): Observable<any> {
    const url = `${URL}load-3d-graph/${id_garment}/${number}`;
    return this.http.get(url);
  }
}
