import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { AdminHomePageComponent } from './pages/admin-home-page/admin-home-page.component';
import { ChangePasswordComponent } from './pages/change-password/change-password.component';
import { FollowingRequestComponent } from './pages/following-request/following-request.component';
import { ForgotPasswordComponent } from './pages/forgot-password/forgot-password.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { JobOffersComponent } from './pages/job-offers/job-offers.component';
import { LandingPageComponent } from './pages/landing-page/landing-page.component';
import { PasswordlessLoginComponent } from './pages/passwordless-login/passwordless-login.component';
import { PostComponent } from './pages/post/post.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { RegistrationRequestComponent } from './pages/registration-request/registration-request.component';
import { RegistrationComponent } from './pages/registration/registration.component';
import { ViewProfileComponent } from './pages/view-profile/view-profile.component';


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
  { path: 'homePage/:token', component: HomePageComponent},
  { path: 'adminHomePage', component: AdminHomePageComponent},
  { path: 'registrationRequest', component: RegistrationRequestComponent},
  { path: 'passwordlessLogin', component: PasswordlessLoginComponent},
  { path: 'viewProfile/:username', component: ViewProfileComponent},
  { path: 'post', component: PostComponent},
  { path: 'jobOffers', component: JobOffersComponent},
  { path: 'followingRequests', component: FollowingRequestComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
