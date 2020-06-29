import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {NgModule} from '@angular/core';
import {RegistrationComponent} from './registration/registration.component';
import {UsersComponent} from './users/users.component';
import {AuthGuard} from './auth/auth.guard';


const routes: Routes = [
  {path: '', component: RegistrationComponent},
  {path: 'login', component: LoginComponent},
  {path: 'users', component: UsersComponent, canActivate: [AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
