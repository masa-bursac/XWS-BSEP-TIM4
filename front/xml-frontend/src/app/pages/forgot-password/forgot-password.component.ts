import { AuthService } from './../../services/auth.service';
import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { AttackService } from 'src/app/services/attack.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

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
        this.authService.forgotPassword(this.username).subscribe(data => {
          this.success = true;
          this.error = false;
          alert("Check your email");
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
