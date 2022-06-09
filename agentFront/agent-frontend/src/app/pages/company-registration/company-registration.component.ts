import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AttackService } from 'src/app/services/attack.service';
import { AuthService } from 'src/app/services/auth.service';
import { CompanyService } from 'src/app/services/company.service';

@Component({
  selector: 'app-company-registration',
  templateUrl: './company-registration.component.html',
  styleUrls: ['./company-registration.component.css']
})
export class CompanyRegistrationComponent implements OnInit {

  validateForm!: FormGroup;
  companyName: string = "";
  phone: string = "";
  username: string = "";
  description: string = "";
  decoded_token : any;

  companyNameBoolean: boolean = false;
  phoneBoolean: boolean = false;
  descriptionBoolean: boolean = false;

  constructor(private fb: FormBuilder,private authService: AuthService, private companyService: CompanyService, private router: Router, private attackService : AttackService) { }

  ngOnInit(): void {
    this.decoded_token = this.authService.getDataFromToken();
    this.validateForm = this.fb.group({
      companyName: [null, [Validators.required]],
      phone: [null, [Validators.required]],
      description: [null, [Validators.required]]
    });
  }

  submitForm(): void {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }

    this.username = this.decoded_token.username;
    this.companyName = this.validateForm.value.companyName;
    this.phone = this.validateForm.value.phone;
    this.description = this.validateForm.value.description;

    this.attackService.companyName(this.companyName).subscribe(data => {
      this.companyNameBoolean = data.bool;
      if(!this.companyNameBoolean)
      alert("Format for company name is not right");

      this.attackService.phoneNumber(this.phone).subscribe(data => {
        this.phoneBoolean = data.bool;
        if(!this.phoneBoolean)
        alert("Format for phone is not right");

        this.attackService.description(this.description).subscribe(data => {
          this.descriptionBoolean = data.bool;
          if(!this.descriptionBoolean)
          alert("Format for description is not right");

          if(this.companyNameBoolean && this.phoneBoolean && this.descriptionBoolean)
          {
            const body = {
              username: this.username,
              companyName: this.companyName,
              phone : this.phone,
              description: this.description,
            }

            if(this.validateForm.valid){
              this.companyService.registration(body).subscribe(data => { 
                  alert("Registration successfull");
              }, error => {
                console.log(error.status);
              });
            }
          }
        });
      });
    });
    
  }

}
