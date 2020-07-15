import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../auth/authentication.service';
import {Router} from '@angular/router';
import {User} from '../shared/user.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  user: User;

  constructor(private authenticationService: AuthenticationService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.authenticationService.currentUser.subscribe(user => this.user = user);
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }

  navigateHome(){
    if (this.authenticationService.currentUserValue.role === 'USER'){
      this.router.navigate(['/']);
    }
    else {
      this.router.navigate(['/']);
    }
  }
}



