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

  public forgotPassword(body: any): Observable<any> {
    return this.http.post(auth_url + `/forgot-password`, body);
  }

  public changePassword(body: any): Observable<any> {
    return this.http.put(auth_url + `/change-password`, body);
  }

  public passwordlessLogin(body: any): Observable<any> {
    return this.http.post(auth_url + `/passwordless-login`, body);
  }

  public getDataFromToken() : any
  {
    let token : any;
    let decoded_token : any;
    let result : any;
    token = localStorage.getItem("token");
    decoded_token = this.getDecodedAccessToken(token);
    return decoded_token;
  }

  getDecodedAccessToken(token: string): any {
    try {
      //return jwt_decode(token);
    }
    catch (Error) {
      return null;
    }
  }
}

