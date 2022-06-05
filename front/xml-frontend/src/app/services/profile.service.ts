import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const profile_url = 'http://localhost:8140/profile';
const request_url = 'http://localhost:8160/request';

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

  public getEducation(body:any): Observable<any>{
    return this.http.get(profile_url+`/getEducation/${body}`);
  }

  public getSkill(body:any): Observable<any>{
    return this.http.get(profile_url+`/getSkill/${body}`);
  }

  public getInterest(body:any): Observable<any>{
    return this.http.get(profile_url+`/getInterest/${body}`);
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

  public addSkill(body: any): Observable<any>{
    return this.http.post(profile_url+`/addSkill`, body);
  }

  public addInterest(body: any): Observable<any>{
    return this.http.post(profile_url+`/addInterest`, body);
  }

  public searchPublicProfiles(body:any): Observable<any>{
    return this.http.get(profile_url+`/getByUsername/${body}`);
  }

  public updateExperience(body: any): Observable<any>{
    return this.http.put(profile_url+`/updateExperience`, body);
  }

  public updateEducation(body: any): Observable<any>{
    return this.http.put(profile_url+`/updateEducation`, body);
  }

  public updateSkill(body: any): Observable<any>{
    return this.http.put(profile_url+`/updateSkill`, body);
  }

  public updateInterest(body: any): Observable<any>{
    return this.http.put(profile_url+`/updateInterest`, body);
  }

  public follow(loggedInId: number, currentId: number): Observable<any>{
    return this.http.put(profile_url + `/follow/${loggedInId}/${currentId}`, null);
  }

  public searchAllProfiles(body:any): Observable<any>{
    return this.http.get(profile_url+`/getAllByUsername/${body}`);
  }

  public getProfileView(body:any): Observable<any>{
    return this.http.get(profile_url+`/getProfileView/${body}`);
  }

  public newRequest(body: any): Observable<any>{
    return this.http.post(request_url+`/newRequest`, body);
  }

  public getAllForProfile(loggedInId: number): Observable<any>{
    return this.http.get(request_url + `/getAllForProfile/${loggedInId}`);
  }

  public acceptFollowRequest(to: number, from: number): Observable<any>{
    return this.http.put(profile_url + `/acceptFollowRequest/${to}/${from}`, null);
  }

  public denyFollowRequest(to: number, from: number): Observable<any>{
    return this.http.put(profile_url + `/acceptFollowRequest/${to}/${from}`, null);
  }
}
