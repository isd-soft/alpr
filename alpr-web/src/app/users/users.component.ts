import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
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
import {Role} from '../auth/role';
import {DomSanitizer} from '@angular/platform-browser';
import {FileHandler} from '../utils/file.handler';

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
  editedUser: User;
  editPasswordChecked = false;
  roles = [Role.User, Role.Admin];
  adminRole = Role.Admin;
  userRole = Role.User;
  value = '';

  userPhotoToView: any;
  userPhotoToEdit: any;
  userPhotoToAdd: any;
  userPhotoToEditURL: any;

  defaultUploadInputLabel = 'Upload Photo';
  uploadInputLabel: string = this.defaultUploadInputLabel;

  @ViewChild('userAddFileInput')
  userAddFileInput: ElementRef;

  @ViewChild('userEditFileInput')
  userEditFileInput: ElementRef;

  constructor(private userService: UserService,
              private companyService: CompanyService,
              private router: Router,
              private formGenerator: FormGenerator,
              private formExtractor: FormExtractor,
              private dialog: MatDialog,
              private snackBar: MatSnackBar,
              private sanitizer: DomSanitizer,
              private fileHandler: FileHandler) {
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
    this.editPasswordChecked = false;
    this.editedUser = user;
     if (user.photo) {
        this.userPhotoToEdit = this.base64PhotoToUrl(user.photo);
        this.userPhotoToEditURL = user.photo;
     } else {
        this.userPhotoToEdit = null;
     }
    this.uploadInputLabel = this.defaultUploadInputLabel;
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
      const user: User = this.formExtractor.extractUser(this.addUserForm);
      user.photo = this.userPhotoToAdd;
      if (user.password.localeCompare(
        this.addUserForm.get('confirmPassword').value) === 0) {
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
    if (this.editUserForm.valid) {
      const user: User = this.formExtractor.extractUser(this.editUserForm);
      if (this.userPhotoToEdit) {
        if (this.userPhotoToEditURL.includes('data:image')) {
          user.photo = this.userPhotoToEditURL;
        } else {
          user.photo = 'data:Image/*;base64,' + this.userPhotoToEditURL;
        }
      } else {
        user.photo = null;
      }

      if (user.password.localeCompare(
        this.editUserForm.get('confirmPassword').value) === 0) {
        user.email = this.editedUser.email;
        this.userService.update(user, this.editPasswordChecked).toPromise()
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

  onEditPasswordCheckBoxClick() {
    this.editPasswordChecked = !this.editPasswordChecked;
  }

  private base64PhotoToUrl(base64Photo: string) {
    return this.sanitizer.bypassSecurityTrustUrl('data:Image/*;base64,' +
     base64Photo);
  }

  handleEditUserFileInput(files: FileList) {
    this.fileHandler.loadCarPhoto(files)
      .then(result => {
          this.userPhotoToEdit = result;
          this.userPhotoToEditURL = result;
          this.uploadInputLabel = files.item(0).name.substring(0, 13) + '...';
          this.userEditFileInput.nativeElement.value = '';
        })
        .catch(error => {
          this.snackBar.open(error, 'OK', {duration: 4000});
        });
  }

  handleAddUserFileInput(files: FileList) {
      this.fileHandler.loadCarPhoto(files)
        .then(result => {
          this.userPhotoToAdd = result;
          this.uploadInputLabel = files.item(0).name.substring(0, 13) + '...';
          this.userAddFileInput.nativeElement.value ='';
        })
        .catch(error => {
          this.snackBar.open(error, 'OK', {duration: 4000});
        });
  }
}
