import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {UserService} from '../shared/user.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {AuthenticationService} from '../auth/authentication.service';
import {Router} from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  @ViewChild('email') emailRef: ElementRef;
  @ViewChild('password') passwordRef: ElementRef;

  constructor(private userService: UserService,
              private snackBar: MatSnackBar,
              private authenticationService: AuthenticationService,
              private router: Router) {
  }

  ngOnInit(): void {
  }

  login() {
    this.authenticationService.login(
      this.emailRef.nativeElement.value,
      this.passwordRef.nativeElement.value)
      .toPromise()
      .then(response => {
        console.log(response.token);
        this.snackBar.open('Successfully', 'OK', {duration: 4000});
        this.router.navigate(['profile'])
      })
      .catch(error => {
        console.log(error);
        this.snackBar.open(error.error.value, 'OK', {duration: 4000});
      });
  }

}
