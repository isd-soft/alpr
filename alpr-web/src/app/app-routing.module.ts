import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {NgModule} from '@angular/core';
import {RegistrationComponent} from './registration/registration.component';
import {UsersComponent} from './users/users.component';
import {AuthGuard} from './auth/auth.guard';
import {CarListComponent} from './car-list/car-list.component';
import {ProfileComponent} from './profile/profile.component';
import {HomeComponent} from './home/home.component';
import {LoginGuard} from './auth/login.guard';
import {CompanyCarsComponent} from "./company-cars/company-cars.component";

import {CompaniesComponent} from './companies/companies.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {AnnouncementComponent} from "./announcement/announcement.component";
import {ParkingPlanComponent} from "./parking-plan/parking-plan.component";



const routes: Routes = [
  {path: 'register', component: RegistrationComponent, canActivate: [LoginGuard]},
  {path: 'login', component: LoginComponent, canActivate: [LoginGuard]},
  {path: 'users', component: UsersComponent, canActivate: [AuthGuard]},
  {path: 'cars', component: CarListComponent, canActivate: [AuthGuard]},
  {path: 'announcements', component: AnnouncementComponent, canActivate: [AuthGuard]},
  {path: 'companies', component: CompaniesComponent, canActivate: [AuthGuard]},
  {path: '', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'profile', component: ProfileComponent, canActivate: [AuthGuard]},
  {path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard]},
  {path: 'companycars', component: CompanyCarsComponent, canActivate: [AuthGuard]},
  {path: 'parkingplan', component: ParkingPlanComponent, canActivate: [AuthGuard]}


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
