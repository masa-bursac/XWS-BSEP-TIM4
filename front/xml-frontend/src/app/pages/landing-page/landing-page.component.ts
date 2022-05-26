import { Component, OnInit } from '@angular/core';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css']
})
export class LandingPageComponent implements OnInit {

  public searchedProfiles: any[];
  public empty = false;
  search : string ="";

  constructor(private profileService : ProfileService) { 
    this.searchedProfiles = [];
  }

  ngOnInit(): void {
    localStorage.clear();
  }

  public Search(): void {
    this.profileService.searchPublicProfiles(this.search).subscribe(data => {
      console.log(data)
      this.searchedProfiles = data;
      if (this.searchedProfiles.length === 0) {
        this.empty = true;
      }
    }, error => {

    })
  }

}
