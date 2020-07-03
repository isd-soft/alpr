import {Component, OnInit} from '@angular/core';
import {User} from '../shared/user.model';
import {HttpErrorResponse} from '@angular/common/http';
import {MatSnackBar} from '@angular/material/snack-bar';
import {UserService} from '../shared/user.service';
import {CompanyService} from '../shared/company.service';
import {Router} from '@angular/router';
import {FormExtractor} from '../utils/form.extractor';
import {FormGenerator} from '../utils/form.generator';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  user: User = new User();
  passwordConfirm = '';

  companies = [];

  registrationForm = this.formGenerator.generateUserRegisterForm();

  constructor(private userService: UserService,
              private snackBar: MatSnackBar,
              private companyService: CompanyService,
              private router: Router,
              private formExtractor: FormExtractor,
              private formGenerator: FormGenerator) {
  }

  ngOnInit(): void {
    this.companyService.getAll().subscribe(companies => this.companies = companies);
  }

  onRegister() {
    this.user = this.formExtractor.extractUser(this.registrationForm);
    this.passwordConfirm = this.registrationForm.get('confirmPassword').value;

    if (this.user.password.localeCompare(this.passwordConfirm) !== 0) {
      this.snackBar.open('Passwords don\'t match', 'OK', {duration: 4000});
    } else {
      this.userService.registerUser(this.user)
        .toPromise()
        .then(_ => {
          this.snackBar.open('Successfully', 'OK', {duration: 3000});
          this.router.navigate(['login']);
        })
        .catch(error => {
          this.handleError(error);
        });
    }
  }

  handleError(httpError: string): void {
    this.snackBar.open(httpError, 'OK', {duration: 4000});
  }

}
