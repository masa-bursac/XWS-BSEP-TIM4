import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RegistrationRequestService } from 'src/app/services/registration-request.service';
import jwt_decode from 'jwt-decode';
import { CompanyService } from 'src/app/services/company.service';

@Component({
  selector: 'app-registration-request',
  templateUrl: './registration-request.component.html',
  styleUrls: ['./registration-request.component.css']
})
export class RegistrationRequestComponent implements OnInit {
  public pendingUsers: any[];
  public pendingCompanies: any[];
  public approveAlert = false;
  public denyAlert = false;
  public empty = false;
  public decodedToken: any;
  public token: any;


  constructor(private router: Router, private rrService: RegistrationRequestService, private companyService: CompanyService) { 
    this.pendingUsers = [];
    this.pendingCompanies = [];
  }

  ngOnInit(): void {
    this.getToken();
    this.getAllPendingUsers();
    this.getAllPendingCompanies();
  }

  private getToken(): void {
    this.token = JSON.parse(localStorage.getItem('token') || '{}');
    this.decodedToken = this.getDecodedAccessToken(this.token);
    if (this.decodedToken === null || this.decodedToken === undefined) {
      alert("Nije dozvoljen pristup");
      this.router.navigate(['landingPage']);
    }else {
      if(this.decodedToken.user_role === 'USER'){
        alert("Nije dozvoljen pristup");
        this.router.navigate(['homePage']);
      }else if(this.decodedToken.user_role === 'OWNER'){
        alert("Nije dozvoljen pristup");
        this.router.navigate(['ownerHomePage']);
      }
    }


  }

  getDecodedAccessToken(token: string): any {
    try {
      return jwt_decode(token);
    }
    catch (Error) {
      return null;
    }
  }

  private getAllPendingUsers(): void {
    this.rrService.getAllPendingUsers().subscribe(data => {
      this.pendingUsers = data;
      if (this.pendingUsers.length === 0) {
        this.empty = true;
      }
    }, error => {

    })
  }

  private getAllPendingCompanies(): void {
    this.companyService.getAllPendingCompanies().subscribe(data => {
      this.pendingCompanies = data;
      if (this.pendingCompanies.length === 0) {
        this.empty = true;
      }
    }, error => {

    })
  }

  public approve(id:number): void {
    this.rrService.approveRegistrationRequest(id).subscribe(data => {
      this.approveAlert = true;
    })
    alert("Approved!");
    this.ngOnInit();
  }

  public deny(id:number): void {
    this.rrService.denyRegistrationRequest(id).subscribe(data => {
      this.denyAlert = true;
    })
    alert("Denied!");
    this.ngOnInit();
  }

  public approveCompany(companyName:any): void {
    this.companyService.approveRegistrationRequest(companyName).subscribe(data => {
      this.approveAlert = true;
    })
    alert("Approved!");
    this.ngOnInit();
  }

  public denyCompany(companyName:any): void {
    this.companyService.denyRegistrationRequest(companyName).subscribe(data => {
      this.denyAlert = true;
    })
    alert("Denied!");
    this.ngOnInit();
  }
}
