import { Component, OnInit } from '@angular/core';
import { AttackService } from 'src/app/services/attack.service';
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
  search : string ="";
  public searchedJobOffers: any[]= [];
  searchBool: boolean = true;

  constructor(private companyService: CompanyService, private authService: AuthService, private attackService: AttackService) { }

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

  public Search(): void {
    this.attackService.escaping(this.search).subscribe(data => {
      this.searchBool = data.bool
      if (this.searchBool) {
          this.companyService.searchJobOffers(this.search).subscribe(data => {
            this.searchedJobOffers = data;
            if (this.searchedJobOffers.length === 0) {
              this.empty = true;
            }
          }, error => {

          })
      }
    });
  }

}
