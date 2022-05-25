import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { ProfileService } from 'src/app/services/profile.service';

interface Gender {
  value: string;
}

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  validateForm = new FormGroup({
    username:new FormControl(),
    name: new FormControl(),
    surname: new FormControl(),
    email: new FormControl(),
    password: new FormControl(),
    phone : new FormControl(),
    dateOfBirth: new FormControl(),
    gender: new FormControl(),
    biography: new FormControl()
   
  }); 

  validateFormExperience = new FormGroup({
    nameExp:new FormControl(),
    position: new FormControl(),
    start: new FormControl(),
    end: new FormControl() 
  }); 

  oldUsername!: String;
  usernameChanged = false;
  decoded_token : any;
  
  today = new Date();

  constructor(private fb: FormBuilder, private authService : AuthService, private profileService : ProfileService) { }

  ngOnInit(): void {
    this.decoded_token = this.authService.getDataFromToken();
    this.profileService.getProfile2(this.decoded_token).subscribe(data=> { //promeniti na  this.decoded_token.id
      this.validateForm = this.fb.group({
        username: [data.username,[Validators.required]],
        name: [data.name,[Validators.required]],
        surname: [data.surname,[Validators.required]],
        email: [data.email,[Validators.required]],
        password: [data.password,[Validators.required]],
        phone : [data.phone,[Validators.required]],
        dateOfBirth: [new Date(data.dateOfBirth)	,[Validators.required]],
        gender: [data.gender,[Validators.required]],
        biography: [data.biography,[Validators.required]]

      });
      this.oldUsername = data.username;
    });
  }

  genders: Gender[] = [
    {value: 'Male'},
    {value: 'Female'},
    {value: 'Non-Binary'},
  ];

  submitForm() {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }

    if(this.validateForm.valid){
      if(this.oldUsername==this.validateForm.value.username)
        this.usernameChanged=false
      else
        this.usernameChanged=true

      const body = {
        username: this.validateForm.value.username,
        name: this.validateForm.value.name,
        surname: this.validateForm.value.surname,
        email: this.validateForm.value.email,
        password: this.validateForm.value.password,
        phone: this.validateForm.value.phone,
        dateOfBirth: this.validateForm.value.dateOfBirth,       
        gender: this.validateForm.value.gender,
        biography: this.validateForm.value.biography

      }
      
      this.profileService.editProfile(body).subscribe(data => {
        if(data)
          alert("Profile successfully edited");
        else
        alert("Username already exist");
      });
    }
  }

  submitFormExperience() {
    for (const i in this.validateFormExperience.controls) {
      this.validateFormExperience.controls[i].markAsDirty();
      this.validateFormExperience.controls[i].updateValueAndValidity();
    }

    if(this.validateFormExperience.valid){

      const body = {
        name: this.validateForm.value.nameExp,
        position: this.validateForm.value.position,
        start: this.validateForm.value.start,
        end: this.validateForm.value.end
      }
      console.log(body);
      
      this.profileService.addExperience(body).subscribe(data => {
        if(data)
          alert("Experience successfully edited");
      });
    }
  }

}
