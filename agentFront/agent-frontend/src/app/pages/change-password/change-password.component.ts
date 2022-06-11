import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AttackService } from 'src/app/services/attack.service';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  validateForm!: FormGroup;
  public username: any;
  hide: boolean = true;
  hideRp: boolean = true;
  passwordBoolean: boolean = false;

  constructor(private route: ActivatedRoute, private fb: FormBuilder, private authService: AuthService, private router: Router, private attackService : AttackService) { }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      password: [null, [Validators.required]],
      rePassword: [null, [Validators.required, this.confirmationValidator]]
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

  submitForm(): void {
    this.username = this.route.snapshot.params['token'];
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }
    this.attackService.password(this.validateForm.value.password).subscribe(data => {
      this.passwordBoolean = data.bool;
      if(!this.passwordBoolean)
          alert("Format for password is not right, it needs to have at least 8 characters, one small letter, one big letter, one number and one special charachter");
      else{
        const body = {
          username: this.username,
          password: this.validateForm.value.password,
          rePassword: this.validateForm.value.rePassword
        }
        this.authService.changePassword(body).subscribe(data => {
          alert("Password changed sucessfully!")
          this.router.navigate(['/login']);
        })
      }
    });
  }

}
