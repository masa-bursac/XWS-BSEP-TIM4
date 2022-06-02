import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-view-profile',
  templateUrl: './view-profile.component.html',
  styleUrls: ['./view-profile.component.css']
})
export class ViewProfileComponent implements OnInit {

  public usernameRoute: any;
  public username: string = "";
  public name: string = "";
  public surname: string = "";
  public email: string = "";
  public phone : string = "";
  public dateOfBirth = new Date();
  public gender: string = "";
  public biography: string = "";
  public id!: number;

  public decodedToken: any;
  public token: any;

  constructor(private route: ActivatedRoute, private authService : AuthService, private profileService : ProfileService,private router: Router) { }

  ngOnInit(): void {
    this.getUsername();
    this.getToken();

    this.profileService.getProfile(this.usernameRoute).subscribe(data=> {
      this.username = data.username,
      this.name= data.name,
      this.surname= data.surname,
      this.email= data.email,
      this.phone = data.phone,
      this.dateOfBirth= new Date(data.dateOfBirth),
      this.gender= data.gender,
      this.biography= data.biography,
      this.id = data.id
  });

  }

  private getToken(): void {
    this.decodedToken = this.authService.getDataFromToken();
    if (this.decodedToken === null || this.decodedToken === undefined) {
      alert("Nije dozvoljen pristup ovde");
      this.router.navigate(['landingPage']);
    }else {
      if(this.decodedToken.user_role === 'ADMIN'){
        alert("Nije dozvoljen pristup");
        this.router.navigate(['adminHomePage']);
      }
    }
  }

  private getUsername(): void {
    this.usernameRoute = this.route.snapshot.params.username;
  }

  public Connect(): void {   
    this.profileService.follow(this.decodedToken.id, this.id).subscribe(data => {
    })
    alert("Connected!");
  }

  

}
