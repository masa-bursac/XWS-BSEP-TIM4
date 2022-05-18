import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { AdminHomePageComponent } from './pages/admin-home-page/admin-home-page.component';
import { ChangePasswordComponent } from './pages/change-password/change-password.component';
import { ForgotPasswordComponent } from './pages/forgot-password/forgot-password.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { LandingPageComponent } from './pages/landing-page/landing-page.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { RegistrationRequestComponent } from './pages/registration-request/registration-request.component';
import { RegistrationComponent } from './pages/registration/registration.component';

const routes: Routes = [
  { path: '', pathMatch:'full', redirectTo:'landingPage'},
  { path: 'landingPage', component: LandingPageComponent},
  { path: 'login', component: LoginComponent},
  { path: 'login/:token', component: LoginComponent},
  { path: 'registration', component: RegistrationComponent},
  { path: 'profile', component: ProfileComponent},
  { path: 'forgotPassword', component: ForgotPasswordComponent},
  { path: 'changePassword/:token', component: ChangePasswordComponent},
  { path: 'homePage', component: HomePageComponent},
  { path: 'adminHomePage', component: AdminHomePageComponent},
  { path: 'registrationRequest', component: RegistrationRequestComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
