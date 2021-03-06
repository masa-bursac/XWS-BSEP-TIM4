import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AttackService } from 'src/app/services/attack.service';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-passwordless-login',
  templateUrl: './passwordless-login.component.html',
  styleUrls: ['./passwordless-login.component.css']
})
export class PasswordlessLoginComponent implements OnInit {

  validateForm!: FormGroup;
  error: boolean = false;
  success: boolean = false;
  username: string = "";
  usernameBool: boolean = true;

  constructor(private fb: FormBuilder, private authService: AuthService, private attackService: AttackService) { }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      username: [null, [Validators.required]]
    });
  }

  submitForm(): void {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }

    this.username = this.validateForm.value.username;

    this.attackService.username(this.username).subscribe(data => {
      this.usernameBool = data.bool
      
      if (this.usernameBool) {
      this.authService.passwordlessLogin(this.username).subscribe(data => {
        this.success = true;
        this.error = false;
        alert("Check your email to login");
      }, error => { 
        this.error = true;
        this.success = false;
        alert(error.error);
      })
    }
    else {
      alert("Invalid username!");
    }
    });
  }

}
