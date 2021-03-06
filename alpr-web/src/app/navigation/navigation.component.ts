import {Component, OnInit} from '@angular/core';
import {User} from '../shared/user.model';
import {AuthenticationService} from '../auth/authentication.service';
import {Role} from '../auth/role';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  user: User;
  links: any[];

  constructor(private authenticationService: AuthenticationService) {
  }

  ngOnInit(): void {
    this.authenticationService.currentUser.subscribe(user => {
      this.user = user;
      if (this.user && this.user.role === Role.Admin) {
        this.links = [
          {
            link: 'dashboard',
            name: 'Dashboard'
          },
          {
            link: 'profile',
            name: 'Profile'
          },
          {
            link: 'announcements',
            name: 'Announcements'
          },
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
          },
          {
            link: 'parkingplan',
            name: 'Parking Plan'
          }
        ];
      } else {
        this.links = [
          {
            link: 'profile',
            name: 'Profile'
          },
          {
            link: 'announcements',
            name: 'Announcements'
          },
          {
            link: 'companycars',
            name: 'Company\'s Cars'
          }];
      }
    });
  }
}
