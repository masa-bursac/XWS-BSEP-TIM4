import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminHomePageComponent } from './pages/admin-home-page/admin-home-page.component';
import { ChangePasswordComponent } from './pages/change-password/change-password.component';
import { CompanyRegistrationComponent } from './pages/company-registration/company-registration.component';
import { CompanyComponent } from './pages/company/company.component';
import { ForgotPasswordComponent } from './pages/forgot-password/forgot-password.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { LandingPageComponent } from './pages/landing-page/landing-page.component';
import { LoginComponent } from './pages/login/login.component';
import { OwnerHomePageComponent } from './pages/owner-home-page/owner-home-page.component';
import { PasswordlessLoginComponent } from './pages/passwordless-login/passwordless-login.component';
import { RegistrationRequestComponent } from './pages/registration-request/registration-request.component';
import { RegistrationComponent } from './pages/registration/registration.component';

const routes: Routes = [
  { path: '', pathMatch:'full', redirectTo:'landingPage'},
  { path: 'landingPage', component: LandingPageComponent},
  { path: 'login', component: LoginComponent},
  { path: 'login/:token', component: LoginComponent},
  { path: 'homePage', component: HomePageComponent},
  { path: 'adminHomePage', component: AdminHomePageComponent},
  { path: 'ownerHomePage', component: OwnerHomePageComponent},
  { path: 'registration', component: RegistrationComponent},
  { path: 'registrationRequest', component: RegistrationRequestComponent},
  { path: 'companyRegistration', component: CompanyRegistrationComponent},
  { path: 'company', component: CompanyComponent},
  { path: 'forgotPassword', component: ForgotPasswordComponent},
  { path: 'changePassword/:token', component: ChangePasswordComponent},
  { path: 'passwordlessLogin', component: PasswordlessLoginComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
