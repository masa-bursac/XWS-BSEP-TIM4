import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
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

  constructor(private fb: FormBuilder,private authService: AuthService, private companyService: CompanyService, private router: Router) { }

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

}
