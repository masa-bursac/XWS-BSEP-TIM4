import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const profile_url = 'https://localhost:8140/profile';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http: HttpClient) { }

  public getProfile(body:any): Observable<any>{
    return this.http.get(profile_url+`/getProfile/${body}`);
  }

  public getExperience(body:any): Observable<any>{
    return this.http.get(profile_url+`/getExperience/${body}`);
  }

  public editProfile(body: any): Observable<any>{
    return this.http.put(profile_url+`/update`, body);
  }

  public addExperience(body: any): Observable<any>{
    return this.http.post(profile_url+`/addExperience`, body);
  }

  public addEducation(body: any): Observable<any>{
    return this.http.post(profile_url+`/addEducation`, body);
  }
}
