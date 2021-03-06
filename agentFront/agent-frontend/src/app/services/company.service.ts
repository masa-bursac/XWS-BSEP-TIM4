import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

const company_url = 'http://localhost:8180/company';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  constructor(private http: HttpClient) { }

  public registration(body: any) : Observable<any>{ 
    return this.http.post(company_url + `/registration`, body);
  }

  public getAllPendingCompanies(): Observable<any> {
    return this.http.get(company_url + '/registration-requests');
  }

  public approveRegistrationRequest(companyName:any): Observable<any> {
    return this.http.put(company_url + '/approve', companyName);
  }

  public denyRegistrationRequest(companyName:any): Observable<any> {
    return this.http.put(company_url + '/deny', companyName);
  }

  public getCompany(body:any): Observable<any> {
    return this.http.get(company_url + `/getCompany/${body}`);
  }

  public updateCompany(body: any): Observable<any>{
    return this.http.put(company_url + `/updateCompany`, body);
  }

  public addJobOffer(body: any) : Observable<any>{ 
    return this.http.post(company_url + `/addJobOffer`, body);
  }

  public getAllJobOffers(): Observable<any> {
    return this.http.get(company_url + `/getAllJobOffers`);
  }

  public addComment(body: any) : Observable<any>{ 
    return this.http.post(company_url + `/addComment`, body);
  }

  public addSalary(body: any) : Observable<any>{ 
    return this.http.post(company_url + `/addSalary`, body);
  }

  public addSelection(body: any) : Observable<any>{ 
    return this.http.post(company_url + `/addSelection`, body);
  }
}
