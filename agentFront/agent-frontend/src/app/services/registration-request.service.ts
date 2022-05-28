import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const auth_url = 'http://localhost:8180/agent';

@Injectable({
  providedIn: 'root'
})
export class RegistrationRequestService {

  constructor(private http: HttpClient) { }

  public confirmRegistrationRequest(body:any): Observable<any> {
    return this.http.put(auth_url + '/confirm', body);
  }
}
