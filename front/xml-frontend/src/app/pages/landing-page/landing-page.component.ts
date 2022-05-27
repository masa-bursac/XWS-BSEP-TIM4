import { Component, OnInit } from '@angular/core';
import { PostService } from 'src/app/services/post.service';
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
  public allPosts: any[] = [];

  constructor(private profileService : ProfileService, private postService : PostService) { 
    this.searchedProfiles = [];
  }

  ngOnInit(): void {
    localStorage.clear();
    this.showPublicPosts();
  }

  public Search(): void {
    this.profileService.searchPublicProfiles(this.search).subscribe(data => {
      this.searchedProfiles = data;
      if (this.searchedProfiles.length === 0) {
        this.empty = true;
      }
    }, error => {

    })
  }

  public showPublicPosts(): void {
    this.postService.getAllPublicPosts().subscribe(data => {
      this.allPosts = data;
      console.log(this.allPosts)
      if (this.allPosts.length === 0) {
        this.empty = true;
      }
    }, error => {

    })
  }

}
