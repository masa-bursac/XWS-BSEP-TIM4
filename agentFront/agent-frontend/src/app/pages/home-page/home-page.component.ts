import { Component, OnInit } from '@angular/core';
import { AttackService } from 'src/app/services/attack.service';
import { AuthService } from 'src/app/services/auth.service';
import { CompanyService } from 'src/app/services/company.service';
import jwt_decode from 'jwt-decode';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  public jobOffers: any[] = [];
  public empty = false;
  public content: string[] = [];
  public salary: string[] = [];
  public comment: string[] = [];
  public duration: string[] = [];
  public mark: string[] = [];
  decoded_token : any;

  commentBoolean: boolean = false;
  salaryBoolean: boolean = false;
  commentsBoolean: boolean = false;
  durationBoolean: boolean = false;
  markBoolean: boolean = false;
  
  public decodedToken: any;
  public token: any;

  constructor(private companyService: CompanyService, private authService: AuthService, private attackService : AttackService, private router : Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.getToken();
    this.getAllJobOffers();
  }

  private getAllJobOffers(): void {
    this.decoded_token = this.authService.getDataFromToken();
    this.companyService.getAllJobOffers().subscribe(data => {
      this.jobOffers = data;
      if (this.jobOffers.length === 0) {
        this.empty = true;
      }
    }, error => {

    })
  }
  private getToken(): void {
    if(this.route.snapshot.params['token'] === undefined){
      this.token = JSON.parse(localStorage.getItem('token') || '{}');
    }else{
      this.token = this.route.snapshot.params['token'];
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

  public addComment(companyName:string, id:number, index:number): void {
    this.attackService.description(this.content[index]).subscribe(data => {
      this.commentBoolean = data.bool;
      if(!this.commentBoolean)
      alert("Format for comment is not right");

      if(this.commentBoolean){
        const body = {
          content: this.content[index],
          userId: this.decoded_token.id,
          companyName: companyName,
          idJobOffer : id
        }

        this.companyService.addComment(body).subscribe(data => {
          alert("Added comment!");
        })
      }
    });
  }

  public addSalary(companyName:string, id:number, index:number): void {
    this.attackService.description(this.salary[index]).subscribe(data => {
      this.salaryBoolean = data.bool;
      if(!this.salaryBoolean)
      alert("Format for salary is not right");

      if(this.salaryBoolean){
        const body = {
          salary: this.salary[index],
          userId: this.decoded_token.id,
          companyName: companyName,
          idJobOffer : id
        }

        this.companyService.addSalary(body).subscribe(data => {
          alert("Added salary!");
        })
      }
    });
  }

  public addSelection(companyName:string, id:number, index:number): void {
    this.attackService.description(this.comment[index]).subscribe(data => {
      this.commentsBoolean = data.bool;
      if(!this.commentsBoolean)
      alert("Format for comment is not right");

      this.attackService.description(this.duration[index]).subscribe(data => {
        this.durationBoolean = data.bool;
        if(!this.durationBoolean)
        alert("Format for duration is not right");

        this.attackService.mark(this.mark[index]).subscribe(data => {
          this.markBoolean = data.bool;
          if(!this.markBoolean)
          alert("Format for mark is not right, it has to be number from 1 to 5");
  

          if(this.commentsBoolean && this.durationBoolean && this.markBoolean){
            const body = {
              comment: this.comment[index],
              userId: this.decoded_token.id,
              companyName: companyName,
              idJobOffer : id,
              duration: this.duration[index],
              mark: this.mark[index]
            }

            this.companyService.addSelection(body).subscribe(data => {
              alert("Added selection!");
            })
          }
        });
      });
    });
  }

}
