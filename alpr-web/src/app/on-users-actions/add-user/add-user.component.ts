import {Component, OnInit} from '@angular/core';
import {CompanyService} from '../../shared/company.service';
import {FormBuilder, Validators} from '@angular/forms';
import {User} from '../../shared/user.model';
import {MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {

  user: User;
  companies = [];
  addUserForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    age: ['', [Validators.required, Validators.min(18)]],
    telephone: ['', [Validators.required, Validators.pattern('^\\+(373[0-9]{8})$')]],
    password: ['', [Validators.required, Validators.minLength(2)]],
    company: ['', Validators.required]
  });

  constructor(private companyService: CompanyService,
              private fb: FormBuilder,
              private dialogRef: MatDialogRef<AddUserComponent>) {
  }

  ngOnInit(): void {
    this.companyService.getAll().subscribe(companies => this.companies = companies);
  }

  close() {
    this.dialogRef.close(this.addUserForm.value);
  }

  addUser() {
    this.user = new User(null,
      this.addUserForm.get('email').value,
      this.addUserForm.get('firstName').value,
      this.addUserForm.get('lastName').value,
      this.addUserForm.get('age').value,
      this.addUserForm.get('telephone').value,
      this.addUserForm.get('password').value,
      this.addUserForm.get('company').value);
  }
}
