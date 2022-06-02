import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

const company_url = 'http://localhost:8180/company';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  constructor(private http: HttpClient) { }

  public getAllSharedJobOffers(): Observable<any> {
    return this.http.get(company_url + `/getAllSharedJobOffers`);
  }
}
