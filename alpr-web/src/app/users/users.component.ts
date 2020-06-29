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

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  users: User[];
  usersDataSource: MatTableDataSource<User> =
    new MatTableDataSource<User>(this.users);
  columnsToDisplay = ['email', 'firstName', 'lastName', 'age',
    'telephoneNumber', 'company', 'password', 'actions'];
  addUserForm: FormGroup = this.formGenerator.generateUserRegisterGroup();
  companies = [];

  constructor(private userService: UserService,
              private companyService: CompanyService,
              private router: Router,
              private formGenerator: FormGenerator,
              private formExtractor: FormExtractor,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.userService.getAll().subscribe(users => this.users = users);
    this.companyService.getAll().subscribe(companies => this.companies = companies);
    this.updateTable();
  }

  updateTable() {
    this.usersDataSource = new MatTableDataSource<User>(this.users);
  }

  onEdit(email: any) {

  }

  onDelete(email: any) {

  }

  onAdd(addUserTemplate) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    this.dialog.open(addUserTemplate, dialogConfig);
  }

  close() {

  }

  addUser() {

  }
}
