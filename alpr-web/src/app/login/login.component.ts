import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {UserService} from '../shared/user.service';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  @ViewChild('email') emailRef: ElementRef;
  @ViewChild('password') passwordRef: ElementRef;

  constructor(private userService: UserService,
              private snackBar: MatSnackBar) { }

  ngOnInit(): void {
  }

  async login() {
    await this.userService.login(this.emailRef.nativeElement.value, this.passwordRef.nativeElement.value)
      .toPromise()
      .then(response => {
        console.log(response.token);
        localStorage.setItem("token", "Bearer " + response.token);
        localStorage.setItem("email", this.emailRef.nativeElement.value);
        this.userService.hello()
          .toPromise()
          .then(responseString => alert("You are authenticated!"))
          .catch(error => console.error(error));
      })
      .catch(_ => {
        this.snackBar.open("Invalid email or password", "OK", {duration: 4000})
      });

  }

}
