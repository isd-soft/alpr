import { Component, OnInit } from '@angular/core';
import {User} from "../shared/user.model";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  user: User = new User();
  passwordConfirm: string = "";
  registrationForm: FormGroup = new FormGroup({
    email: new FormControl(''),
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    age: new FormControl(''),
    telephone: new FormControl(''),
    password: new FormControl(''),
    confirmPassword: new FormControl(''),
    company: new FormControl('')
  });
  constructor() { }

  ngOnInit(): void {

  }

  onRegister() {
    if(this.user.password.localeCompare(this.passwordConfirm) !== 0) {
      alert("Passwords don't match");
    }
    else {
      console.log(this.user);
    }
  }

}
