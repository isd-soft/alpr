import { Component, OnInit } from '@angular/core';
import {User} from "../shared/user.model";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  user: User = new User();
  passwordConfirm: string = "";

  registrationForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    age: ['', [Validators.required, Validators.min(18)]],
    telephone: ['', [Validators.required, Validators.pattern("^\\+(373[0-9]{8})$")]],
    password: ['', [Validators.required, Validators.minLength(2)]],
    confirmPassword: ['', [Validators.required, Validators.minLength(2)]],
    company: ['', Validators.required]
  });
  constructor(private fb: FormBuilder,
              private httpClient: HttpClient,
              private snackBar: MatSnackBar) { }

  ngOnInit(): void {

  }

  onRegister() {
    this.user = new User(1,
      this.registrationForm.get('email').value,
      this.registrationForm.get('firstName').value,
      this.registrationForm.get('lastName').value,
      this.registrationForm.get('age').value,
      this.registrationForm.get('telephone').value,
      this.registrationForm.get('password').value,
      this.registrationForm.get('company').value);
    this.passwordConfirm = this.registrationForm.get('confirmPassword').value;
    if(this.user.password.localeCompare(this.passwordConfirm) !== 0) {
      this.snackBar.open("Passwords don't match", "OK", {duration: 4000})
    }
    else {
      this.user.company = null;
      this.httpClient.post("http://localhost:8080/register", this.user)
        .toPromise()
        .then(_ => alert("GOOD"))
        .catch(error => this.handleError(error));
    }
  }

  handleError(httpError: HttpErrorResponse) {
    if(httpError.status === 200) {
      alert("GOOD in error");
    }
    else {
      this.snackBar.open(httpError.error, "OK", {duration: 4000})
    }
  }
}
