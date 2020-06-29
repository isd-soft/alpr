import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {UserService} from '../shared/user.service';

@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {
  constructor(
    private router: Router,
    private userService: UserService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const currentToken = this.userService.currentToken;
    console.log('currentToken = ' + currentToken + ' : auth guard');
    if (currentToken) {
      console.log('user logged in : auth guard')
      return true;
    }

    // not logged in so redirect to login page with the return url
    this.router.navigate(['/login'],
      {queryParams: {returnUrl: state.url}});
    return false;
  }
}
