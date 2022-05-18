import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { AttackService } from '../services/attack.service';
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

  constructor(private fb: FormBuilder, private router: Router, private authService : AuthService, private attackService: AttackService) { }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required]],
      remember: [true]
    });
  }

  submitForm(): void {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }

    this.username = this.validateForm.value.username;
    this.password = this.validateForm.value.password;

    this.attackService.username(this.username).subscribe(data => {
      this.usernameBool = data.bool
      console.log(this.username, this.password, this.usernameBool);
      if (this.usernameBool) {
        const body = {
          username: this.username,
          password: this.password
        }
        this.authService.login(body).subscribe(data => {
          const user = data;
          localStorage.setItem('user', JSON.stringify(user));
          localStorage.setItem('token', JSON.stringify(user.token));
          console.log(this.getDecodedAccessToken(data.token));
          console.log(user);
          if(this.getDecodedAccessToken(data.token).user_role === 'USER'){
            this.router.navigate(['homePage']);
          }
          else if(this.getDecodedAccessToken(data.token).user_role === 'ADMIN'){
            this.router.navigate(['adminHomePage']);
          }
        }, error => {
          this.errorLogin = true;
        })
      }
      else {
        alert("Greska");
      }
    });
  }

  getDecodedAccessToken(token: string): any {
    try {
      return jwt_decode(token);
    }
    catch (Error) {
      return null;
    }
  }


 /* login(){
    if(this.validateForm.valid){
      const body = {
        username : this.validateForm.get('username')?.value,
        password : this.validateForm.get('password')?.value
      }

      console.log(body);
      this.authService.login(body).subscribe( data => {
        console.log(data);
        if (data === null){
          this.ngOnInit();
        }else{
          const user = data;
          localStorage.setItem('user', JSON.stringify(user));
          localStorage.setItem('token', JSON.stringify(user.token));

          sessionStorage.setItem('username', user.username);
          let authString = 'Basic ' + btoa(user.username + ':' + user.password);
          sessionStorage.setItem('basicauth', authString);
        }
      
      });    
    }
  }*/

  
}
