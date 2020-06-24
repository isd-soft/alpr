import { Component, OnInit } from '@angular/core';
import {User} from "../shared/user.model";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";

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
              private httpClient: HttpClient) { }

  ngOnInit(): void {

  }

  onRegister() {
    if(this.user.password.localeCompare(this.passwordConfirm) !== 0) {
      alert("Passwords don't match");
    }
    else {
      this.user.company = null;
      this.httpClient.post("http://localhost:8080/register", this.user)
        .subscribe(response => console.log(response));
    }
  }

}
