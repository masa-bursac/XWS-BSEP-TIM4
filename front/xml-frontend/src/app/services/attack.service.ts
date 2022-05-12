import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const attack_url = 'http://localhost:8140/attack';

@Injectable({
  providedIn: 'root'
})
export class AttackService {

  constructor(private http: HttpClient) { }

  public email(body: any) : Observable<any>{ 
    return this.http.post(attack_url + `/email`, body);
  }

  public name(body: any) : Observable<any>{ 
    return this.http.post(attack_url + `/name`, body);
  }

  public password(body: any) : Observable<any>{ 
    return this.http.post(attack_url + `/password`, body);
  }

  public phoneNumber(body: any) : Observable<any>{ 
    return this.http.post(attack_url + `/phoneNumber`, body);
  }
}
