import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const auth_url = 'http://localhost:8140/auth';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  public login(body: any) : Observable<any>{ 
    return this.http.post(auth_url + `/login`, body);
  }

  public registration(body: any) : Observable<any>{ 
    return this.http.post(auth_url + `/registration`, body);
  }
}
