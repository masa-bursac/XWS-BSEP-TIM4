import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import jwt_decode from 'jwt-decode';

const auth_url = 'http://localhost:8180/agent';

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
      return jwt_decode(token);
    }
    catch (Error) {
      return null;
    }
  }
}

