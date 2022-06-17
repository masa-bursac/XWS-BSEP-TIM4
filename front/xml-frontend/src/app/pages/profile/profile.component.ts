import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { AttackService } from 'src/app/services/attack.service';
import { AuthService } from 'src/app/services/auth.service';
import { PostService } from 'src/app/services/post.service';
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
    nameExp: new FormControl(),
    position: new FormControl(),
    start: new FormControl(),
    end: new FormControl() 
  }); 

  validateFormEducationAdd = new FormGroup({
    nameEducation: new FormControl(),
    positionEducation: new FormControl(),
    startEducation: new FormControl(),
    endEducation: new FormControl() 
  });

  validateFormSkillAdd = new FormGroup({
    nameSkill: new FormControl(),
    otherInfoSkill: new FormControl()
  });

  validateFormInterestAdd = new FormGroup({
    nameInterest: new FormControl(),
    otherInfoInterest: new FormControl()
  });


  oldUsername!: String;
  usernameChanged = false;
  decoded_token : any;
  
  today = new Date();

  public AllExperience: any[] = [];
  public AllEducation: any[] = [];
  public AllSkill: any[] = [];
  public AllInterest: any[] = [];

  public allPosts: any[] = [];
  public image: any;
  public empty = false;

  emailBoolean: boolean = false;
  usernameBoolean: boolean = false;
  nameBoolean: boolean = false;
  surnameNameBoolean: boolean = false;
  phoneBoolean: boolean = false;
  dateBoolean: boolean = false;
  biographyBoolean: boolean = false;

  constructor(private fb: FormBuilder, private authService : AuthService, private profileService : ProfileService, private postService : PostService, private sanitizer: DomSanitizer, private attackService : AttackService) { }

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

    
    this.profileService.getExperience(this.decoded_token.username).subscribe(data=> {
      this.AllExperience = data;
      for(let i = 0; i<this.AllExperience.length; i++){
        this.AllExperience[i].start = new Date(this.AllExperience[i].start);
        this.AllExperience[i].end = new Date(this.AllExperience[i].end);
      }
    });

    this.profileService.getEducation(this.decoded_token.username).subscribe(data=> {
      this.AllEducation = data;
      this.AllEducation = data;
      for(let i = 0; i<this.AllEducation.length; i++){
        this.AllEducation[i].start = new Date(this.AllEducation[i].start);
        this.AllEducation[i].end = new Date(this.AllEducation[i].end);
      }
    });

    this.profileService.getSkill(this.decoded_token.username).subscribe(data=> {
      this.AllSkill = data;
    });

    this.profileService.getInterest(this.decoded_token.username).subscribe(data=> {
      this.AllInterest = data;
    });

    this.showPosts();

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

        this.attackService.username(this.validateForm.value.username).subscribe(data => {
          this.usernameBoolean = data.bool;
          if(!this.usernameBoolean)
          alert("Format for username is not right, it needs to have at least 5 characters, the dot (.), underscore (_), or hyphen (-) are allowed and must not be the first or last character or appear consecutively");
    
          this.attackService.name(this.validateForm.value.name).subscribe(data => {
            this.nameBoolean = data.bool;
            if(!this.nameBoolean)
            alert("Format for name is not right");
    
            this.attackService.name(this.validateForm.value.surname).subscribe(data => {
              this.surnameNameBoolean = data.bool;
              if(!this.surnameNameBoolean)
              alert("Format for surname is not right");
    
              this.attackService.email(this.validateForm.value.email).subscribe(data => {
                this.emailBoolean = data.bool;
                if(!this.emailBoolean)
                  alert("Format for email is not right");
                       
                    this.attackService.phoneNumber(this.validateForm.value.phone).subscribe(data => {
                      this.phoneBoolean = data.bool;
                      if(!this.phoneBoolean)
                      alert("Format for phone number is not right");

                      this.attackService.date(this.validateForm.value.dateOfBirth).subscribe(data => {
                        this.dateBoolean = data.bool;
                        if(!this.dateBoolean)
                        alert("Format for date of birth is not right");
                        
                        this.attackService.comment(this.validateForm.value.biography).subscribe(data => {
                          this.biographyBoolean = data.bool;
                          if(!this.biographyBoolean)
                          alert("Format for biography is not right");
                         
                      
                      if(this.usernameBoolean && this.nameBoolean && this.surnameNameBoolean && this.emailBoolean && this.phoneBoolean && this.dateBoolean && this.biographyBoolean)
                      {
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
                    });
                    });
                  });
    
                });
            });
        
          });
        
        });
    }
  }

  submitFormExperienceAdd(): void  {
    for (const i in this.validateFormExperienceAdd.controls) {
      this.validateFormExperienceAdd.controls[i].markAsDirty();
      this.validateFormExperienceAdd.controls[i].updateValueAndValidity();
    }

    this.attackService.comment(this.validateFormExperienceAdd.value.nameExp).subscribe(data => {
      this.usernameBoolean = data.bool;
      if(!this.usernameBoolean)
      alert("Format for name is not right");

      this.attackService.comment(this.validateFormExperienceAdd.value.position).subscribe(data => {
        this.nameBoolean = data.bool;
        if(!this.nameBoolean)
        alert("Format for position is not right");

        this.attackService.date(this.validateFormExperienceAdd.value.start).subscribe(data => {
          this.surnameNameBoolean = data.bool;
          if(!this.surnameNameBoolean)
          alert("Format for date is not right");

          this.attackService.date(this.validateFormExperienceAdd.value.end).subscribe(data => {
            this.emailBoolean = data.bool;
            if(!this.emailBoolean)
            alert("Format for date is not right");

              if(this.usernameBoolean && this.nameBoolean && this.surnameNameBoolean && this.emailBoolean){

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
                    this.ngOnInit();
                });
              }
            });
          });
      });
    });
  }

  submitFormEducationAdd(): void  {
    for (const i in this.validateFormEducationAdd.controls) {
      this.validateFormEducationAdd.controls[i].markAsDirty();
      this.validateFormEducationAdd.controls[i].updateValueAndValidity();
    }

    this.attackService.comment(this.validateFormEducationAdd.value.nameEducation).subscribe(data => {
      this.usernameBoolean = data.bool;
      if(!this.usernameBoolean)
      alert("Format for name is not right");

      this.attackService.comment(this.validateFormEducationAdd.value.positionEducation).subscribe(data => {
        this.nameBoolean = data.bool;
        if(!this.nameBoolean)
        alert("Format for position is not right");

        this.attackService.date(this.validateFormEducationAdd.value.startEducation).subscribe(data => {
          this.surnameNameBoolean = data.bool;
          if(!this.surnameNameBoolean)
          alert("Format for date is not right");

          this.attackService.date(this.validateFormEducationAdd.value.endEducation).subscribe(data => {
            this.emailBoolean = data.bool;
            if(!this.emailBoolean)
            alert("Format for date is not right");

              if(this.usernameBoolean && this.nameBoolean && this.surnameNameBoolean && this.emailBoolean){

                const body = {
                  name: this.validateFormEducationAdd.value.nameEducation,
                  position: this.validateFormEducationAdd.value.positionEducation,
                  start: this.validateFormEducationAdd.value.startEducation,
                  end: this.validateFormEducationAdd.value.endEducation,
                  userInfoId: this.decoded_token.id
                }
                
                this.profileService.addEducation(body).subscribe(data => {
                  if(data)
                    alert("Education successfully added!");
                    this.ngOnInit();
                });
              }
            });
          });
      });
    });
  }

  submitFormSkillAdd(): void  {
    for (const i in this.validateFormSkillAdd.controls) {
      this.validateFormSkillAdd.controls[i].markAsDirty();
      this.validateFormSkillAdd.controls[i].updateValueAndValidity();
    }

    this.attackService.comment(this.validateFormSkillAdd.value.nameSkill).subscribe(data => {
      this.surnameNameBoolean = data.bool;
      if(!this.surnameNameBoolean)
      alert("Format for skill is not right");

      this.attackService.comment(this.validateFormSkillAdd.value.otherInfoSkill).subscribe(data => {
        this.emailBoolean = data.bool;
        if(!this.emailBoolean)
        alert("Format for other info is not right");

          if(this.surnameNameBoolean && this.emailBoolean){

            const body = {
              name: this.validateFormSkillAdd.value.nameSkill,
              otherInfo: this.validateFormSkillAdd.value.otherInfoSkill,
              userInfoId: this.decoded_token.id
            }
            
            this.profileService.addSkill(body).subscribe(data => {
              if(data)
                alert("Skill successfully added!");
                this.ngOnInit();
            });
          }
        });
      });
  }

  submitFormInterestAdd(): void  {
    for (const i in this.validateFormInterestAdd.controls) {
      this.validateFormInterestAdd.controls[i].markAsDirty();
      this.validateFormInterestAdd.controls[i].updateValueAndValidity();
    }

    this.attackService.comment(this.validateFormInterestAdd.value.nameInterest).subscribe(data => {
      this.surnameNameBoolean = data.bool;
      if(!this.surnameNameBoolean)
      alert("Format for name is not right");

      this.attackService.comment(this.validateFormInterestAdd.value.otherInfoInterest).subscribe(data => {
        this.emailBoolean = data.bool;
        if(!this.emailBoolean)
        alert("Format for other info is not right");

          if(this.surnameNameBoolean && this.emailBoolean){

            const body = {
              name: this.validateFormInterestAdd.value.nameInterest,
              otherInfo: this.validateFormInterestAdd.value.otherInfoInterest,
              userInfoId: this.decoded_token.id
            }
            
            this.profileService.addInterest(body).subscribe(data => {
              if(data)
                alert("Interest successfully added!");
                this.ngOnInit();
            });
          }
        });
      }); 
  }

  public updateExperience(id:number, name:string, position:string, start:string, end:string, userInfoId:number): void {
    this.attackService.comment(name).subscribe(data => {
      this.usernameBoolean = data.bool;
      if(!this.usernameBoolean)
      alert("Format for name is not right");

      this.attackService.comment(position).subscribe(data => {
        this.nameBoolean = data.bool;
        if(!this.nameBoolean)
        alert("Format for position is not right");

        this.attackService.date(start).subscribe(data => {
          this.surnameNameBoolean = data.bool;
          if(!this.surnameNameBoolean)
          alert("Format for date is not right");

          this.attackService.date(end).subscribe(data => {
            this.emailBoolean = data.bool;
            if(!this.emailBoolean)
            alert("Format for date is not right");

              if(this.usernameBoolean && this.nameBoolean && this.surnameNameBoolean && this.emailBoolean){

                const body = {
                  id: id,
                  name: name,
                  position: position,
                  start : start,
                  end : end,
                  userInfoId : userInfoId
                }
                
                this.profileService.updateExperience(body).subscribe(data => {
                  alert("Experience updated!");
                })
              }
            });
          });
      });
    });   
  }

  public updateEducation(id:number, name:string, position:string, start:string, end:string, userInfoId:number): void {
    this.attackService.comment(name).subscribe(data => {
      this.usernameBoolean = data.bool;
      if(!this.usernameBoolean)
      alert("Format for name is not right");

      this.attackService.comment(position).subscribe(data => {
        this.nameBoolean = data.bool;
        if(!this.nameBoolean)
        alert("Format for position is not right");

        this.attackService.date(start).subscribe(data => {
          this.surnameNameBoolean = data.bool;
          if(!this.surnameNameBoolean)
          alert("Format for date is not right");

          this.attackService.date(end).subscribe(data => {
            this.emailBoolean = data.bool;
            if(!this.emailBoolean)
            alert("Format for date is not right");

              if(this.usernameBoolean && this.nameBoolean && this.surnameNameBoolean && this.emailBoolean){

                const body = {
                  id: id,
                  name: name,
                  position: position,
                  start : start,
                  end : end,
                  userInfoId : userInfoId
                }
            
                this.profileService.updateEducation(body).subscribe(data => {
                  alert("Education updated!");
                })
              }
            });
          });
      });
    }); 
    
  }

  public updateSkill(id:number, name:string, otherInfo:string, userInfoId:number): void {
    this.attackService.comment(name).subscribe(data => {
      this.surnameNameBoolean = data.bool;
      if(!this.surnameNameBoolean)
      alert("Format for name is not right");

      this.attackService.comment(otherInfo).subscribe(data => {
        this.emailBoolean = data.bool;
        if(!this.emailBoolean)
        alert("Format for other info is not right");

          if(this.surnameNameBoolean && this.emailBoolean){

            const body = {
              id: id,
              name: name,
              otherInfo: otherInfo,
              userInfoId : userInfoId
            }
        
            this.profileService.updateSkill(body).subscribe(data => {
              alert("Skill updated!");
            })
          }
        });
      });   
  }

  public updateInterest(id:number, name:string, otherInfo:string, userInfoId:number): void {
    this.attackService.comment(name).subscribe(data => {
      this.surnameNameBoolean = data.bool;
      if(!this.surnameNameBoolean)
      alert("Format for name is not right");

      this.attackService.comment(otherInfo).subscribe(data => {
        this.emailBoolean = data.bool;
        if(!this.emailBoolean)
        alert("Format for other info is not right");

          if(this.surnameNameBoolean && this.emailBoolean){

            const body = {
              id: id,
              name: name,
              otherInfo: otherInfo,
              userInfoId : userInfoId
            }
        
            this.profileService.updateInterest(body).subscribe(data => {
              alert("Interest updated!");
            })
          }
        });
      });    
  }

  public showPosts(): void {
    this.postService.getAllPosts(this.decoded_token.id).subscribe(data => {
      this.allPosts = data;
      for(let i = 0; i<this.allPosts.length; i++){
        let objectURL = 'data:image/png;base64,' + this.allPosts[i].content;
        this.allPosts[i].image = this.sanitizer.bypassSecurityTrustUrl(objectURL);

        if(this.allPosts[i].postInfo.caption.substring(0,4) === "http"){
          this.allPosts[i].link = true;
        }

        if(this.allPosts[i].likeIds != null){
          for(let j=0; j<this.allPosts[i].likeIds.length; j++){
            if(this.allPosts[i].likeIds[j] == this.decoded_token.id){
              this.allPosts[i].isLiked = true;
            }else{
              this.allPosts[i].isLiked = false;
            }
          }
        }

        if(this.allPosts[i].dislikeIds != null){
          for(let j=0; j<this.allPosts[i].dislikeIds.length; j++){
            if(this.allPosts[i].dislikeIds[j] == this.decoded_token.id){
              this.allPosts[i].isDisliked = true;
            }else{
              this.allPosts[i].isDisliked = false;
            }
          }
        }
      }

      if (this.allPosts.length === 0) {
        this.empty = true;
      }
    }, error => {

    })
  }

}
