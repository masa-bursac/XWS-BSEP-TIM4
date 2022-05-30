import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { RegistrationRequestService } from 'src/app/services/registration-request.service';
import jwt_decode from 'jwt-decode';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  token: any;
  errorLogin: boolean = false;
  validateForm!: FormGroup;
  username: any;
  password: any;
  usernameBool: boolean = true;

  hide: boolean = true;

  constructor(private route: ActivatedRoute, private fb: FormBuilder, private router: Router, private authService : AuthService, private rrService: RegistrationRequestService) { }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required]],
      remember: [true]
    });

    
    const token = this.route.snapshot.params['token'];
    if (token != undefined) {
      this.rrService.confirmRegistrationRequest(token).subscribe(() => {
        this.router.navigateByUrl(`/login`);
      },
        error => {
        });
    }
  }

  submitForm(): void {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }

    this.username = this.validateForm.value.username;
    this.password = this.validateForm.value.password;

    const body = {
      username: this.username,
      password: this.password
    }

    this.authService.login(body).subscribe(data => {
      const user = data;
      localStorage.setItem('user', JSON.stringify(user));
      localStorage.setItem('token', JSON.stringify(user.token));

      sessionStorage.setItem('username', user.username);
      let authString = 'Basic ' + btoa(user.username + ':' + user.password);
      sessionStorage.setItem('basicauth', authString);

      if(this.getDecodedAccessToken(data.token).user_role === 'USER'){
        this.router.navigate(['homePage']);
      }
      else if(this.getDecodedAccessToken(data.token).user_role === 'ADMIN'){
        this.router.navigate(['adminHomePage']);
      }
      else if(this.getDecodedAccessToken(data.token).user_role === 'OWNER'){
        this.router.navigate(['ownerHomePage']);
      }
    }, error => {
      this.errorLogin = true;
      alert(error.error);
    })

    
  }

  getDecodedAccessToken(token: string): any {
    try {
      return jwt_decode(token);
    }
    catch (Error) {
      return null;
    }
  }

}
