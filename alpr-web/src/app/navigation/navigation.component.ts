import {Component, OnInit} from '@angular/core';
import {User} from '../shared/user.model';
import {AuthenticationService} from '../auth/authentication.service';
import {Router} from '@angular/router';
import {Role} from '../auth/role';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  user: User;
  links: any[];

  constructor(private authenticationService: AuthenticationService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.authenticationService.currentUser.subscribe(user => {
      this.user = user;
      if (this.user.role == Role.Admin) {
        this.links = [
          {
            link: 'cars',
            name: 'Cars'
          },
          {
            link: 'users',
            name: 'Users'
          },
          {
            link: 'companies',
            name: 'Companies'
          }];
      } else {
        this.links = [
          {
            link: 'profile',
            name: 'Profile'
          },
          {
            link: 'addcar',
            name: 'Add Car'
          },
          {
            link: '',
            name: 'Change password'
          }];
      }
    });
  }
}
