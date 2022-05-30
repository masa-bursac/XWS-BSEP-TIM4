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
}
