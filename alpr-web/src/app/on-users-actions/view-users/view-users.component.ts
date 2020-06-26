import {Component, OnInit} from '@angular/core';
import {User} from '../../shared/user.model';
import {MatTableDataSource} from '@angular/material/table';

@Component({
  selector: 'app-view-users',
  templateUrl: './view-users.component.html',
  styleUrls: ['./view-users.component.css']
})
export class ViewUsersComponent implements OnInit {

  users: User[] = [];
    // 1, 'mail1@mail.com, name, ' +
    // 'name, 19, +37311111111, admin, admin, ISD'),
    // new User(2, 'mail2@mail.com, name, ' +
    //   'name, 19, +37311111111, admin, admin, ISD'),
    // new User(3, 'mail3@mail.com, name, ' +
    //   'name, 19, +37311111111, admin, admin, ISD'),
    // new User(4, 'mail4@mail.com, name, ' +
    //   'name, 19, +37311111111, admin, admin, ISD'),
    // new User(5, 'mail5@mail.com, name, ' +
    //   'name, 19, +37311111111, admin, admin, ISD'),
    // new User(6, 'mail6@mail.com, name, ' +
    //   'name, 19, +37311111111, admin, admin, ISD')];

  usersDataSource = new MatTableDataSource<User>(this.users);

  columnsToDisplay = ['email', 'firstName', 'lastName', 'age',
    'telephoneNumber', 'company', 'password', 'actions'];


  constructor() {
  }

  ngOnInit(): void {
    const user: User =  new User(6, 'mail6@mail.com',
      'name', 'name', 19,
      '+37311111111', 'admin', 'ISD');
    const user1: User =  new User(6, 'mail6@mail.com',
      'name', 'name', 19,
      '+37311111111', 'admin', 'ISD');
    const user2: User =  new User(6, 'mail6@mail.com',
      'name', 'name', 19,
      '+37311111111', 'admin', 'ISD');
    const user3: User =  new User(6, 'mail6@mail.com',
      'name', 'name', 19,
      '+37311111111', 'admin', 'ISD');

    this.users.push(user);
    this.users.push(user1);
    this.users.push(user2);
    this.users.push(user3);


    // setTimeout(() => {
    //   console.log("I am here");
    //   const user: User =  new User(7, 'mail8@mail.com', 'name', 'name', 19, '+37311111111', 'admin', 'ISD');
    //   this.users.push(user);
    //   this.usersDataSource = new MatTableDataSource<User>(this.users);
    // }, 3000);
  }

}
