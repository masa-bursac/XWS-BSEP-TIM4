import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { CompanyService } from 'src/app/services/company.service';

@Component({
  selector: 'app-job-offers',
  templateUrl: './job-offers.component.html',
  styleUrls: ['./job-offers.component.css']
})
export class JobOffersComponent implements OnInit {

  decoded_token : any;
  public jobOffers: any[] = [];
  public empty = false;

  constructor(private companyService: CompanyService, private authService: AuthService) { }

  ngOnInit(): void {
    this.getAllJobOffers();
  }

  private getAllJobOffers(): void {
    this.decoded_token = this.authService.getDataFromToken();
    this.companyService.getAllSharedJobOffers().subscribe(data => {
      this.jobOffers = data;
      if (this.jobOffers.length === 0) {
        this.empty = true;
      }
    }, error => {

    })
  }

}
