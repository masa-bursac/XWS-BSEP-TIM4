import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const auth_url = 'http://localhost:8140/auth';

@Injectable({
  providedIn: 'root'
})
export class RegistrationRequestService {

  constructor(private http: HttpClient) { }

  public getAllPendingUsers(): Observable<any> {
    return this.http.get(auth_url + '/registration-requests');
  }

  public approveRegistrationRequest(id:number): Observable<any> {
    return this.http.put(auth_url + '/approve', id);
  }

  public denyRegistrationRequest(id:number): Observable<any> {
    return this.http.put(auth_url + '/deny', id);
  }

  public confirmRegistrationRequest(body:any): Observable<any> {
    return this.http.put(auth_url + '/confirm', body);
  }
}
