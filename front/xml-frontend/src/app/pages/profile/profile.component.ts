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
    phone : new FormControl(),
    dateOfBirth: new FormControl(),
    gender: new FormControl(),
    biography: new FormControl()
   
  }); 

  selectedValueGender = "Male";

  validateFormExperienceAdd = new FormGroup({
    nameExp:new FormControl(),
    position: new FormControl(),
    start: new FormControl(),
    end: new FormControl() 
  }); 

  nameExpAdd : string = "";
  positionAdd : string = "";
  startAdd : Date = new Date();
  endAdd : Date = new Date();

  oldUsername!: String;
  usernameChanged = false;
  decoded_token : any;
  
  today = new Date();

  constructor(private fb: FormBuilder, private authService : AuthService, private profileService : ProfileService) { }

  ngOnInit(): void {
    this.decoded_token = this.authService.getDataFromToken();
    this.profileService.getProfile(this.decoded_token.username).subscribe(data=> {
      this.validateForm = this.fb.group({
        username: [data.username,[Validators.required]],
        name: [data.name,[Validators.required]],
        surname: [data.surname,[Validators.required]],
        email: [data.email,[Validators.required]],
        phone : [data.phone,[Validators.required]],
        dateOfBirth: [new Date(data.dateOfBirth)	,[Validators.required]],
        gender: [data.gender,[Validators.required]],
        biography: [data.biography,[Validators.required]]

      });
      this.selectedValueGender = data.gender;
      this.oldUsername = data.username;
    });

    /*
    this.profileService.getExperience(this.decoded_token.username).subscribe(data=> {
      console.log(data)
      this.validateForm = this.fb.group({
        id: [data.id,[Validators.required]],
        nameEx: [data.name,[Validators.required]],
        positionEx: [data.position,[Validators.required]],
        startEx: [data.start,[Validators.required]],
        endEx : [data.end,[Validators.required]],
        userInfoId: [data.userInfoId,[Validators.required]]

      });

    });*/

  }

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

  submitFormExperienceAdd(): void  {
    for (const i in this.validateFormExperienceAdd.controls) {
      this.validateFormExperienceAdd.controls[i].markAsDirty();
      this.validateFormExperienceAdd.controls[i].updateValueAndValidity();
    }

    if(this.validateFormExperienceAdd.valid){

      const body = {
        name: this.validateFormExperienceAdd.value.nameExp,
        position: this.validateFormExperienceAdd.value.position,
        start: this.validateFormExperienceAdd.value.start,
        end: this.validateFormExperienceAdd.value.end,
        userInfoId: this.decoded_token.id
      }
      
      this.profileService.addExperience(body).subscribe(data => {
        if(data)
          alert("Experience successfully added!");
      });
    }
  }

}
