import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import jwt_decode from 'jwt-decode';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  public decodedToken: any;
  public token: any;
  public role: any;

  constructor(private router: Router) { }

  ngOnInit(): void {
    if(localStorage.getItem('token')){
      this.getToken();
    }
  }

  private getToken(): void {
    this.token = JSON.parse(localStorage.getItem('token') || '{}');
    this.decodedToken = this.getDecodedAccessToken(this.token);
    this.role = this.decodedToken.user_role;
    console.log(this.role);
  }

  getDecodedAccessToken(token: string): any {
    try {
      return jwt_decode(token);
    }
    catch (Error) {
      return null;
    }
  }

  public Logout(): void {
    localStorage.clear();
    this.router.navigateByUrl('landingPage');
  }

}
