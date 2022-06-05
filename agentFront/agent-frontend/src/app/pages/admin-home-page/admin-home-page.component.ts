import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import jwt_decode from 'jwt-decode';

@Component({
  selector: 'app-admin-home-page',
  templateUrl: './admin-home-page.component.html',
  styleUrls: ['./admin-home-page.component.css']
})
export class AdminHomePageComponent implements OnInit {

  public decodedToken: any;
  public token: any;

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.getToken();
  }

  private getToken(): void {
    this.token = JSON.parse(localStorage.getItem('token') || '{}');
    this.decodedToken = this.getDecodedAccessToken(this.token);
    console.log(this.decodedToken);
    if (this.decodedToken === null || this.decodedToken === undefined) {
      alert("Nije dozvoljen pristup");
      this.router.navigate(['landingPage']);
    }else {
      if(this.decodedToken.user_role === 'USER'){
        alert("Nije dozvoljen pristup");
        this.router.navigate(['homePage']);
      }
      else if(this.decodedToken.user_role === 'OWNER'){
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

}
