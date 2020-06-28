import {Component, OnInit} from '@angular/core';
import {User} from '../../shared/user.model';
import {MatTableDataSource} from '@angular/material/table';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {AddUserComponent} from '../add-user/add-user.component';

@Component({
  selector: 'app-view-users',
  templateUrl: './view-users.component.html',
  styleUrls: ['./view-users.component.css']
})
export class ViewUsersComponent implements OnInit {

  users: User[] = [];
  usersDataSource = new MatTableDataSource<User>(this.users);
  columnsToDisplay = ['email', 'firstName', 'lastName', 'age',
    'telephoneNumber', 'company', 'password', 'actions'];

  constructor(private dialog: MatDialog) {
  }

  ngOnInit(): void {
    const user: User = new User(6, 'mail6@mail.com',
      'name', 'name', 19,
      '+37311111111', 'admin', 'ISD');
    const user1: User = new User(6, 'mail6@mail.com',
      'name', 'name', 19,
      '+37311111111', 'admin', 'ISD');
    const user2: User = new User(6, 'mail6@mail.com',
      'name', 'name', 19,
      '+37311111111', 'admin', 'ISD');
    const user3: User = new User(6, 'mail6@mail.com',
      'name', 'name', 19,
      '+37311111111', 'admin', 'ISD');

    this.users.push(user);
    this.users.push(user1);
    this.users.push(user2);
    this.users.push(user3);

    this.updateTable();

    // setTimeout(() => {
    //   console.log("I am here");
    //   const user: User =  new User(7, 'mail8@mail.com', 'name', 'name', 19, '+37311111111', 'admin', 'ISD');
    //   this.users.push(user);
    // }, 3000);
  }

  onEdit(email: string) {

  }

  onDelete(email: string) {

  }

  updateTable() {
    this.usersDataSource = new MatTableDataSource<User>(this.users);
  }

  onAdd() {
    this.openDialog(AddUserComponent);
  }

  openDialog(component) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    // dialogConfig.direction = ;
    this.dialog.open(component, dialogConfig);
  }
}
