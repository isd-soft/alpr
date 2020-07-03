import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {UserService} from '../shared/user.service';
import {MatSnackBar} from '@angular/material/snack-bar';
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
              private router: Router) { }

  ngOnInit(): void {
  }

  async login() {
    await this.userService.login(this.emailRef.nativeElement.value, this.passwordRef.nativeElement.value)
      .toPromise()
      .then(response => {
        console.log(response.token);
        localStorage.setItem("token", "Bearer " + response.token);
        localStorage.setItem("email", this.emailRef.nativeElement.value);
        this.userService.currentToken = localStorage.getItem("token");
        this.userService.hello()
          .toPromise()
          .then(responseString => alert("You are authenticated!"))
          .catch(error => console.error(error));
        this.router.navigate(['profile'])
      })
      .catch(error => {
        console.log(error);
        this.snackBar.open(error.error.value, "OK", {duration: 4000})
      });

  }

}
