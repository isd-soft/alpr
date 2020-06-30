import {Component, OnInit} from '@angular/core';
import {UserService} from '../shared/user.service';
import {Router} from '@angular/router';
import {FormGenerator} from '../utils/form.generator';
import {FormExtractor} from '../utils/form.extractor';
import {User} from '../shared/user.model';
import {MatTableDataSource} from '@angular/material/table';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {FormGroup} from '@angular/forms';
import {CompanyService} from '../shared/company.service';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
})
export class UsersComponent implements OnInit {

  users: User[] = [];
  usersDataSource: MatTableDataSource<User> =
    new MatTableDataSource<User>(this.users);
  columnsToDisplay = ['email', 'firstName', 'lastName', 'age',
    'telephoneNumber', 'company', 'password', 'actions'];
  addUserForm: FormGroup = this.formGenerator.generateUserRegisterForm();
  companies = [];

  constructor(private userService: UserService,
              private companyService: CompanyService,
              private router: Router,
              private formGenerator: FormGenerator,
              private formExtractor: FormExtractor,
              private dialog: MatDialog,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.userService.getAll()
      .subscribe(users => {
        this.users = users;
        console.log(users[0])
        this.updateTable();
      });

    this.companyService.getAll()
      .subscribe(companies => this.companies = companies);
  }

  updateTable() {
    this.usersDataSource = new MatTableDataSource<User>(this.users);
  }

  onEdit(email: any) {

  }

  onDelete(user: User) {
    console.log(user);
    this.userService.remove(user.id).toPromise()
      .then(_ => {
        this.snackBar.open('Successfully', 'OK', {duration: 3000});
      })
      .catch(_ => {
        this.snackBar.open('Oops! Something went wrong', 'OK', {duration: 3000});
      });
  }

  onAdd(addUserTemplate) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    this.dialog.open(addUserTemplate, dialogConfig);
  }

  addUser() {
    if (this.addUserForm.valid) {
      let user: User = this.formExtractor.extractUser(this.addUserForm);
      if (user.password.localeCompare(
        this.addUserForm.get('confirmPassword').value) == 0) {
        this.userService.add(user).toPromise()
          .then(_ => {
            this.snackBar.open('Successfully', 'OK', {duration: 3000});
          })
          .catch(_ => {
            this.snackBar.open('Oops! Something went wrong', 'OK', {duration: 3000});
          });
      } else {
        this.snackBar.open('Passwords don\'t match', 'OK', {duration: 4000});
      }
    } else {
      this.snackBar.open('Fill all the required fields, please',
        'OK', {duration: 4000});
    }
  }
}
