import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { CompanyService } from 'src/app/services/company.service';

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

  constructor(private companyService: CompanyService, private authService: AuthService) { }

  ngOnInit(): void {
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

  public addComment(companyName:string, id:number, index:number): void {
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

  public addSalary(companyName:string, id:number, index:number): void {
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

  public addSelection(companyName:string, id:number, index:number): void {
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

}
