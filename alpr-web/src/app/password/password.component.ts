import {Component, OnInit} from '@angular/core';
import {User} from '../shared/user.model';
import {Role} from '../auth/role';
import {AuthenticationService} from '../auth/authentication.service';
import {Router} from '@angular/router';
import {UserService} from '../shared/user.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {FormGroup} from '@angular/forms';
import {FormGenerator} from '../utils/form.generator';

@Component({
  selector: 'app-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.css']
})
export class PasswordComponent implements OnInit {

  private user: User;
  hasCars: boolean;
  changePasswordForm: FormGroup;

  constructor(private authenticationService: AuthenticationService,
              private router: Router,
              private userService: UserService,
              private snackBar: MatSnackBar,
              private formGenerator: FormGenerator) {
  }

  ngOnInit(): void {
    this.user = this.authenticationService.currentUserValue;
    if (!this.user) {
      this.router.navigate(['/']);
    }

    if (this.user.role === Role.User) {
      this.userService.hasCars(this.user.email).toPromise()
        .then(response => {
          this.hasCars = response;
        })
        .catch(_ => this.snackBar.open(
          'Oops! Something went wrong', 'OK', {duration: 3000}));
    }

    this.changePasswordForm = this.formGenerator.generateChangePasswordForm();
  }

  onPasswordChange() {
    let oldPassword: string = this.changePasswordForm.get('oldPassword').value;
    let newPassword: string = this.changePasswordForm.get('newPassword').value;
    let newPasswordConfirm: string = this.changePasswordForm.get('newPasswordConfirm').value;
    let licensePlate: string = this.changePasswordForm.get('licensePlate').value;

    console.log(oldPassword + ' old');
    console.log(newPassword + ' new');
    console.log(newPasswordConfirm + ' confirm');

    if (newPassword.localeCompare(newPasswordConfirm) !== 0) {
      this.snackBar.open('Passwords don\'t match', 'OK', {duration: 4000});
    } else {
      this.userService.changePassword(this.user.email, oldPassword,
        newPassword, licensePlate).toPromise()
        .then(_ => {
          this.snackBar.open('Successfully', 'OK', {duration: 3000});
          this.router.navigate(['login']);
        })
        .catch(error => this.handleError(error));
    }
  }

  handleError(httpError: string): void {
    this.snackBar.open(httpError, 'OK', {duration: 4000});
  }
}
