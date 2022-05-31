import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
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

  decoded_token : any;

  constructor(private fb: FormBuilder, private authService : AuthService, private companyService : CompanyService) { }

  ngOnInit(): void {
    this.decoded_token = this.authService.getDataFromToken();
    this.companyService.getCompany(this.decoded_token.username).subscribe(data=> {
      this.validateForm = this.fb.group({
        companyName: [data.companyName,[Validators.required]],
        phone: [data.phone,[Validators.required]],
        description: [data.description,[Validators.required]]
      });
    });
  }

  submitForm(): void {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }

    if(this.validateForm.valid){
      const body = {
        companyName: this.validateForm.value.companyName,
        phone: this.validateForm.value.phone,
        description: this.validateForm.value.description,
        username: this.decoded_token.username
      }
      
      this.companyService.updateCompany(body).subscribe(data => {
        if(data)
          alert("Company successfully edited");
        else
        alert("Username already exist");
      });
    }
  }

  submitFormJobOffer(): void {
    for (const i in this.validateFormJobOffer.controls) {
      this.validateFormJobOffer.controls[i].markAsDirty();
      this.validateFormJobOffer.controls[i].updateValueAndValidity();
    }

    if(this.validateFormJobOffer.valid){
      const body = {
        companyName: this.validateForm.value.companyName,
        jobPosition: this.validateFormJobOffer.value.jobPosition
      }
      
      this.companyService.addJobOffer(body).subscribe(data => {
        if(data)
          alert("Job offer successfully added");
        else
        alert("Username already exist");
      });
    }
  }

}
