import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import jwt_decode from 'jwt-decode';
import { PostService } from 'src/app/services/post.service';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  public decodedToken: any;
  public token: any;
  public searchedProfiles: any[]= [];
  public empty = false;
  search : string ="";
  public allPosts: any[] = [];
  public image: any;

  constructor(private route: ActivatedRoute, private router: Router, private profileService : ProfileService, private postService : PostService, private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.getToken();
    this.showPublicPosts();
  }

  private getToken(): void {
    if(this.route.snapshot.params.token === undefined){
      this.token = JSON.parse(localStorage.getItem('token') || '{}');
    }else{
      this.token = this.route.snapshot.params.token;
    }
    this.decodedToken = this.getDecodedAccessToken(this.token);
    if (this.decodedToken === null || this.decodedToken === undefined) {
      alert("Nije dozvoljen pristup ovde");
      this.router.navigate(['landingPage']);
    }else {
      localStorage.setItem('token', JSON.stringify(this.token));
      if(this.decodedToken.user_role === 'ADMIN'){
        alert("Nije dozvoljen pristup");
        this.router.navigate(['adminHomePage']);
      }else {
        this.router.navigate(['homePage']);
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
      for(let i = 0; i<this.allPosts.length; i++){
        let objectURL = 'data:image/png;base64,' + this.allPosts[i].content;
        this.allPosts[i].image = this.sanitizer.bypassSecurityTrustUrl(objectURL);

        if(this.allPosts[i].likeIds != null){
          for(let j=0; j<this.allPosts[i].likeIds.length; j++){
            if(this.allPosts[i].likeIds[j] == this.decodedToken.id){
              this.allPosts[i].isLiked = true;
            }else{
              this.allPosts[i].isLiked = false;
            }
          }
        }

        if(this.allPosts[i].dislikeIds != null){
          for(let j=0; j<this.allPosts[i].dislikeIds.length; j++){
            if(this.allPosts[i].dislikeIds[j] == this.decodedToken.id){
              this.allPosts[i].isDisliked = true;
            }else{
              this.allPosts[i].isDisliked = false;
            }
          }
        }
        

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

  public Like(id:number): void {
    this.postService.like(this.decodedToken.id,id).subscribe(data => {
    }, error => {

    })
    this.ngOnInit();
  }

  public Dislike(id:number): void {
    this.postService.dislike(this.decodedToken.id,id).subscribe(data => {
    }, error => {

    })
    this.ngOnInit();
  }

}
