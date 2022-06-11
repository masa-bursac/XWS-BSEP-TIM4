import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './pages/login/login.component';
import { LandingPageComponent } from './pages/landing-page/landing-page.component';
import { NavbarComponent } from './navbar/navbar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { AdminHomePageComponent } from './pages/admin-home-page/admin-home-page.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { OwnerHomePageComponent } from './pages/owner-home-page/owner-home-page.component';
import { RegistrationComponent } from './pages/registration/registration.component';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { RegistrationRequestComponent } from './pages/registration-request/registration-request.component';
import { CompanyRegistrationComponent } from './pages/company-registration/company-registration.component';
import { CompanyComponent } from './pages/company/company.component';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { JwtInterceptorComponent } from './jwt-interceptor/jwt-interceptor.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LandingPageComponent,
    NavbarComponent,
    HomePageComponent,
    AdminHomePageComponent,
    OwnerHomePageComponent,
    RegistrationComponent,
    RegistrationRequestComponent,
    CompanyRegistrationComponent,
    CompanyComponent
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
    MatIconModule,
    MatCheckboxModule
  ],
  providers: [   
     { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptorComponent, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
