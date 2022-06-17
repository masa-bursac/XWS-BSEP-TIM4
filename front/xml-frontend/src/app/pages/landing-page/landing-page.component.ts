import { Component, OnInit } from '@angular/core';
import { PostService } from 'src/app/services/post.service';
import { ProfileService } from 'src/app/services/profile.service';
import { DomSanitizer } from '@angular/platform-browser';
import { AttackService } from 'src/app/services/attack.service';

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
  public image: any;
  usernameBool: boolean = true;

  constructor(private profileService : ProfileService, private postService : PostService, private sanitizer: DomSanitizer, private attackService: AttackService) { 
    this.searchedProfiles = [];
  }

  ngOnInit(): void {
    localStorage.clear();
    this.showPublicPosts();
  }

  public Search(): void {
    this.attackService.escaping(this.search).subscribe(data => {
      this.usernameBool = data.bool
      if (this.usernameBool) {
        this.profileService.searchPublicProfiles(this.search).subscribe(data => {
          this.searchedProfiles = data;
          if (this.searchedProfiles.length === 0) {
            this.empty = true;
          }
        }, error => {
        
        })
      }
    });
  }

  public showPublicPosts(): void {
    this.postService.getAllPublicPosts().subscribe(data => {
      this.allPosts = data;
      console.log(this.allPosts)
      for(let i = 0; i<this.allPosts.length; i++){
        let objectURL = 'data:image/png;base64,' + this.allPosts[i].content;
        this.allPosts[i].image = this.sanitizer.bypassSecurityTrustUrl(objectURL);

        if(this.allPosts[i].postInfo.caption.substring(0,4) === "http"){
          this.allPosts[i].link = true;
        }
      }

      if (this.allPosts.length === 0) {
        this.empty = true;
      }
    }, error => {

    })
  }

}
