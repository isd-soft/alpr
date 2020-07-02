import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
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
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
})
export class UsersComponent implements OnInit, AfterViewInit {

  users: User[] = [];
  usersDataSource: MatTableDataSource<User> =
    new MatTableDataSource<User>(this.users);
  columnsToDisplay = ['email', 'firstName', 'lastName', 'age',
    'telephoneNumber', 'company', 'password', 'role', 'actions'];
  addUserForm: FormGroup = this.formGenerator.generateUserAddForm();
  editUserForm: FormGroup;
  companies = [];
  roles = ['USER', 'SYSTEM_ADMINISTRATOR'];
  editedUser: User;
  editPasswordChecked = false;

  constructor(private userService: UserService,
              private companyService: CompanyService,
              private router: Router,
              private formGenerator: FormGenerator,
              private formExtractor: FormExtractor,
              private dialog: MatDialog,
              private snackBar: MatSnackBar) {
  }

  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;

  ngOnInit() {
  }

  loadUsers() {
    this.userService.getAll()
      .subscribe(users => {
        this.users = users;
        this.updateTable(this.users);
      });
  }

  updateTable(users: User[]) {
    this.usersDataSource = new MatTableDataSource<User>(users);
    this.usersDataSource.paginator = this.paginator;
    this.usersDataSource.sort = this.sort;
  }

  onEdit(editUserTemplate, user: User) {
    this.editedUser = user;
    this.editUserForm = this.formGenerator
      .generateUserEditForm(this.editedUser);
    this.openTemplate(editUserTemplate);
  }

  onDelete(user: User) {
    this.userService.removeByEmail(user.email).toPromise()
      .then(_ => {
        this.snackBar.open('Successfully', 'OK', {duration: 3000});
        this.users.splice(this.users.indexOf(user), 1);
        this.loadUsers();
      })
      .catch(_ => {
        this.snackBar.open('Oops! Something went wrong', 'OK', {duration: 3000});
      });
  }

  onAdd(addUserTemplate) {
    this.openTemplate(addUserTemplate);
  }

  addUser() {
    if (this.addUserForm.valid) {
      let user: User = this.formExtractor.extractUser(this.addUserForm);
      if (user.password.localeCompare(
        this.addUserForm.get('confirmPassword').value) == 0) {
        this.userService.add(user).toPromise()
          .then(_ => {
            this.snackBar.open('Successfully', 'OK', {duration: 3000});
            this.loadUsers();
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

  updateUser() {
    let user: User = this.formExtractor.extractUser(this.editUserForm);
    user.email = this.editedUser.email;

    this.userService.update(user, this.editPasswordChecked)
      .toPromise()
      .then(_ => {
        this.snackBar.open('Successfully', 'OK', {duration: 3000});
        this.loadUsers();
      })
      .catch(_ => {
        this.snackBar.open('Oops! Something went wrong', 'OK', {duration: 3000});
      });
  }

  private openTemplate(template) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    this.dialog.open(template, dialogConfig);
  }

  ngAfterViewInit(): void {
    this.userService.getAll().toPromise()
      .then(users => {
        this.users = users;
        this.usersDataSource = new MatTableDataSource<User>(this.users);
        this.usersDataSource.paginator = this.paginator;
        this.usersDataSource.sort = this.sort;
      })
      .catch(_ => {
        this.snackBar.open('Oops! Something went wrong; Users not loaded',
          'OK', {duration: 3000});
      });
    this.companyService.getAll().toPromise()
      .then(companies => {
        this.companies = companies;
      })
      .catch(_ => {
        this.snackBar.open('Oops! Something went wrong; Companies not loaded',
          'OK', {duration: 3000});
      });
  }

  applyFilter(filterValue: string) {
    this.usersDataSource.filter = filterValue.trim().toLowerCase();
  }

  clearInput() {
    this.usersDataSource.filter = '';
  }
}
