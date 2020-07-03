import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {NgModule} from '@angular/core';
import {RegistrationComponent} from './registration/registration.component';
import {UsersComponent} from './users/users.component';
import {AuthGuard} from './auth/auth.guard';
import {CarListComponent} from "./car-list/car-list.component";
import{AddCarComponent} from "./add-car/add-car.component";
import{ProfileComponent} from "./profile/profile.component";
import {HomeComponent} from './home/home.component';


const routes: Routes = [
  {path: 'register', component: RegistrationComponent},
  {path: 'login', component: LoginComponent},
  {path: 'users', component: UsersComponent, canActivate: [AuthGuard]},
  {path: 'cars', component: CarListComponent, canActivate: [AuthGuard]},
  {path: 'addcar', component: AddCarComponent, canActivate: [AuthGuard]},
  {path: '', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'profile', component: ProfileComponent, canActivate: [AuthGuard]}


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
