import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatCardModule} from '@angular/material/card';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { RegistrationComponent } from './pages/registration/registration.component';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { LandingPageComponent } from './pages/landing-page/landing-page.component';
import { NavbarComponent } from './navbar/navbar.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { ProfileComponent } from './pages/profile/profile.component';
import { JwtInterceptorComponent } from './_helpers/jwt-interceptor/jwt-interceptor.component';
import { ForgotPasswordComponent } from './pages/forgot-password/forgot-password.component';
import { ChangePasswordComponent } from './pages/change-password/change-password.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { AdminHomePageComponent } from './pages/admin-home-page/admin-home-page.component';
import { RegistrationRequestComponent } from './pages/registration-request/registration-request.component';
import { PasswordlessLoginComponent } from './pages/passwordless-login/passwordless-login.component';
import { ViewProfileComponent } from './pages/view-profile/view-profile.component';
import { PostComponent } from './pages/post/post.component';




@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    LandingPageComponent,
    NavbarComponent,
    ProfileComponent,
    ForgotPasswordComponent,
    ChangePasswordComponent,
    HomePageComponent,
    AdminHomePageComponent,
    RegistrationRequestComponent,
    PasswordlessLoginComponent,
    ViewProfileComponent,
    PostComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatCardModule,
    MatInputModule,
    MatButtonModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatSelectModule,
    MatNativeDateModule,
    MatDatepickerModule,
    MatToolbarModule,
    MatIconModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptorComponent, multi: true },

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
