import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AttackService } from 'src/app/services/attack.service';
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
  phone : string = "";
  dateOfBirth : Date = new Date();
  selectedValueGender = "Male";

  today = new Date();
  errorRegister: boolean = false;
  emailBoolean: boolean = false;
  nameBoolean: boolean = false;
  surnameNameBoolean: boolean = false;
  passwordBoolean: boolean = false;
  phoneBoolean: boolean = false;
  dateBoolean: boolean = false;

  constructor(private fb: FormBuilder,private authService : AuthService, private attackService : AttackService, private router: Router) { }

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
    } else if (control.value !== this.validateForm.controls.password.value) {
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

    const body = {
      username: this.username,
      name: this.name,
      surname: this.surname,
      email : this.email,
      password : this.password,
      phone : this.phone,
      dateOfBirth: this.dateOfBirth,
      gender: this.selectedValueGender,   
    }
    console.log(body);

    this.attackService.name(this.name).subscribe(data => {
      this.nameBoolean = data.bool;
      if(!this.nameBoolean)
      alert("Format for name is not right");

      this.attackService.name(this.surname).subscribe(data => {
        this.surnameNameBoolean = data.bool;
        if(!this.surnameNameBoolean)
        alert("Format for surname is not right");

        this.attackService.email(this.email).subscribe(data => {
          this.emailBoolean = data.bool;
          if(!this.emailBoolean)
            alert("Format for email is not right");

              this.attackService.password(this.password).subscribe(data => {
                this.passwordBoolean = data.bool;
                if(!this.passwordBoolean)
                alert("Format for password is not right, it needs to have at least 8 characters, one small letter, one big letter, one number and one special charachter");

                  this.attackService.phoneNumber(this.phone).subscribe(data => {
                    this.phoneBoolean = data.bool;
                    if(!this.phoneBoolean)
                    alert("Format for phone number is not right");
                    
                    if(this.nameBoolean && this.surnameNameBoolean && this.emailBoolean && this.passwordBoolean && this.phoneBoolean)
                    {
                      const body = {
                        username: this.username,
                        name: this.name,
                        surname: this.surname,
                        email : this.email,
                        password : this.password,
                        phone : this.phone,
                        dateOfBirth: this.dateOfBirth,
                        gender: this.selectedValueGender,   
                      }

                      if(this.validateForm.valid){
                        this.authService.registration(body).subscribe(data => { console.log(data) 
                          if(data == true){
                            console.log(body);
                            //this.authService.getId(this.username).subscribe(data => {
                            //})
                            alert("Registration successfull");
                            this.router.navigate(['login']);
                          }
                          else{
                            alert("Username is not valid");
                          }
                        })
                      }
                    }
                  });
              });

          });
      });
  
    });

  }

}
