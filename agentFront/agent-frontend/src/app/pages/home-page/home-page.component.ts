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
  decoded_token : any;

  constructor(private companyService: CompanyService, private authService: AuthService) { }

  ngOnInit(): void {
    this.getAllJobOffers();
  }

  private getAllJobOffers(): void {
    this.decoded_token = this.authService.getDataFromToken();
    this.companyService.getAllJobOffers().subscribe(data => {
      this.jobOffers = data;
      console.log(this.jobOffers)
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

    console.log(body)

    this.companyService.addComment(body).subscribe(data => {
      alert("Added comment!");
    })
    
  }

}
