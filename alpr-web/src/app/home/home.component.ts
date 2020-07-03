import {AfterViewInit, Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../auth/authentication.service';
import {Router} from '@angular/router';
import {User} from '../shared/user.model';
import {Role} from '../auth/role';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, AfterViewInit {

  constructor(private router: Router,
              private authenticationService: AuthenticationService) {
  }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    let user: User = this.authenticationService.currentUserValue;
    console.log(user.role + "user role in home")
    if (user.role == Role.Admin) {
      this.router.navigate(['/cars']);
    } else {
      this.router.navigate(['/register']);
    }
  }


}
