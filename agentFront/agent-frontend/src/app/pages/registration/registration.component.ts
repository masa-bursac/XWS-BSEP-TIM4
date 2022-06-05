import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

interface Gender {
  value: string;
}

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  validateForm!: FormGroup;
  username: string = "";
  name: string = "";
  surname : string = "";
  email : string = "";
  password : string = "";
  checkPassword : string = "";
  phone : string = "";
  dateOfBirth : Date = new Date();
  selectedValueGender = "Male";
  hide: boolean = true;
  hideRp: boolean = true;

  constructor(private fb: FormBuilder,private authService : AuthService, private router: Router) { }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      username: [null,[Validators.required]],
      name: [null, [Validators.required]],
      surname: [null, [Validators.required]],
      email: [null, [Validators.email, Validators.required]],
      password: [null, [Validators.required]],
      checkPassword: [null, [Validators.required, this.confirmationValidator]],
      phone: [null, [Validators.required]],
      dateOfBirth: [null, [Validators.required]]
    });
  }

  confirmationValidator = (control: FormControl): { [s: string]: boolean } => {
    if (!control.value) {
      return { required: true };
    } else if (control.value !== this.validateForm.controls['password'].value) {
      return { confirm: true, error: true };
    }
    return {};
  };

  genders: Gender[] = [
    {value: 'Male'},
    {value: 'Female'},
    {value: 'Non-Binary'},
  ];

  submitForm(): void {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }

    this.username = this.validateForm.value.username;
    this.name = this.validateForm.value.name;
    this.surname = this.validateForm.value.surname;
    this.email = this.validateForm.value.email;
    this.password = this.validateForm.value.password;
    this.phone = this.validateForm.value.phone;
    this.dateOfBirth = this.validateForm.value.dateOfBirth;
    this.checkPassword = this.validateForm.value.checkPassword;

      const body = {
        username: this.username,
        name: this.name,
        surname: this.surname,
        email : this.email,
        password : this.password,
        repeatPassword: this.checkPassword,
        phone : this.phone,
        dateOfBirth: this.dateOfBirth,
        gender: this.selectedValueGender,   
      }

      if(this.validateForm.valid){
        this.authService.registration(body).subscribe(data => { 
            alert("Registration successfull");
            this.router.navigate(['login']);
        }, error => {
          console.log(error.status);
          if(error.status == 409){
            alert("Username already exists");
          }
        });
      }
    }
                    


}
