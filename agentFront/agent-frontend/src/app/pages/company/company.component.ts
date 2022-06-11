import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AttackService } from 'src/app/services/attack.service';
import { AuthService } from 'src/app/services/auth.service';
import { CompanyService } from 'src/app/services/company.service';

@Component({
  selector: 'app-company',
  templateUrl: './company.component.html',
  styleUrls: ['./company.component.css']
})
export class CompanyComponent implements OnInit {

  validateForm = new FormGroup({
    companyName:new FormControl(),
    description:new FormControl(), 
    phone:new FormControl()
  });

  validateFormJobOffer = new FormGroup({
    jobPosition:new FormControl()
  });

  public share: boolean = false;

  decoded_token : any;
  companyNameBoolean: boolean = false;
  phoneBoolean: boolean = false;
  descriptionBoolean: boolean = false;
  jobPositionBoolean: boolean = false;

  constructor(private fb: FormBuilder, private authService : AuthService, private companyService : CompanyService, 
      private attackService : AttackService, private router: Router) { }

  ngOnInit(): void {
    this.checkToken();
    this.companyService.getCompany(this.decoded_token.username).subscribe(data=> {
      this.validateForm = this.fb.group({
        companyName: [data.companyName,[Validators.required]],
        phone: [data.phone,[Validators.required]],
        description: [data.description,[Validators.required]]
      });
    });
  }
  checkToken():void{
    this.decoded_token = this.authService.getDataFromToken();
    console.log(this.decoded_token);
    if (this.decoded_token === null || this.decoded_token === undefined) {
      alert("Nije dozvoljen pristup");
      this.router.navigate(['landingPage']);
    }else {
      if(this.decoded_token.user_role === 'USER'){
        alert("Nije dozvoljen pristup");
        this.router.navigate(['homePage']);
      }
      if(this.decoded_token.user_role ==='ADMIN'){
        alert("Nije dozvoljen pristup");
        this.router.navigate(['adminHomePage']);
      }
    }
  }

  submitForm(): void {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }

    this.attackService.companyName(this.validateForm.value.companyName).subscribe(data => {
      this.companyNameBoolean = data.bool;
      if(!this.companyNameBoolean)
      alert("Format for company name is not right");

      this.attackService.phoneNumber(this.validateForm.value.phone).subscribe(data => {
        this.phoneBoolean = data.bool;
        if(!this.phoneBoolean)
        alert("Format for phone is not right");

        this.attackService.description(this.validateForm.value.description).subscribe(data => {
          this.descriptionBoolean = data.bool;
          if(!this.descriptionBoolean)
          alert("Format for description is not right");

          if(this.companyNameBoolean && this.phoneBoolean && this.descriptionBoolean)
          {
            const body = {
              username: this.decoded_token.username,
              companyName: this.validateForm.value.companyName,
              phone: this.validateForm.value.phone,
              description: this.validateForm.value.description
            }

            if(this.validateForm.valid){
              this.companyService.updateCompany(body).subscribe(data => {
                if(data)
                  alert("Company successfully edited");
                else
                alert("Username already exist");
              });
            }
          }
        });
      });
    });
  
  }

  submitFormJobOffer(): void {
    for (const i in this.validateFormJobOffer.controls) {
      this.validateFormJobOffer.controls[i].markAsDirty();
      this.validateFormJobOffer.controls[i].updateValueAndValidity();
    }

    this.attackService.description(this.validateFormJobOffer.value.jobPosition).subscribe(data => {
      this.jobPositionBoolean = data.bool
      if(!this.jobPositionBoolean)
          alert("Format for job position is not right");

      if (this.jobPositionBoolean) {
        const body = {
        companyName: this.validateForm.value.companyName,
        jobPosition: this.validateFormJobOffer.value.jobPosition,
        share: this.share
      }
    
      this.companyService.addJobOffer(body).subscribe(data => {
        if(data)
          alert("Job offer successfully added");
        else
        alert("Username already exist");
      });
      }
    });
  }

}
