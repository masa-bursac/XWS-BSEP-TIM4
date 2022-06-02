import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProfileService } from 'src/app/services/profile.service';
import jwt_decode from 'jwt-decode';

@Component({
  selector: 'app-following-request',
  templateUrl: './following-request.component.html',
  styleUrls: ['./following-request.component.css']
})
export class FollowingRequestComponent implements OnInit {

  public followRequests: any[] = [];
  public approveAlert = false;
  public denyAlert = false;
  public empty = false;
  public decodedToken: any;
  public token: any;

  constructor(private router: Router, private profileService: ProfileService) { }

  ngOnInit(): void {
    this.getToken();
    this.getAllPendingUsers();
  }

  private getToken(): void {
    this.token = JSON.parse(localStorage.getItem('token') || '{}');
    this.decodedToken = this.getDecodedAccessToken(this.token);
    if (this.decodedToken === null || this.decodedToken === undefined) {
      alert("Nije dozvoljen pristup");
      this.router.navigate(['landingPage']);
    }else {
      if(this.decodedToken.user_role === 'ADMIN'){
        alert("Nije dozvoljen pristup");
        this.router.navigate(['adminHomePage']);
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
    this.profileService.getAllForProfile(this.decodedToken.id).subscribe(data => {
      this.followRequests = data;
      if (this.followRequests.length === 0) {
        this.empty = true;
      }
    }, error => {

    })
  }

  public approve(id:number): void {
    this.profileService.acceptFollowRequest(this.decodedToken.id,id).subscribe(data => {
      this.approveAlert = true;
    })
    alert("Approved!");
    this.ngOnInit();
  }

  public deny(id:number): void {
    this.profileService.denyFollowRequest(this.decodedToken.id,id).subscribe(data => {
      this.denyAlert = true;
    })
    alert("Denied!");
    this.ngOnInit();
  }

}
