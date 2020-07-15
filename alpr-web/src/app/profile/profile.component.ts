import {AfterViewInit, Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {MatSort} from '@angular/material/sort';
import {CarModel} from '../shared/car.model';
import {CarService} from '../shared/car.service';
import {User} from '../shared/user.model';
import {UserService} from '../shared/user.service';
import {Router} from '@angular/router';
import {AuthenticationService} from '../auth/authentication.service';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';
import {FormGenerator} from '../utils/form.generator';
import {FormExtractor} from '../utils/form.extractor';
import {FormGroup} from '@angular/forms';
import {DomSanitizer} from '@angular/platform-browser';
import {FileHandler} from '../utils/file.handler';
import {Role} from '../auth/role';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit, AfterViewInit {
  displayedColumns = ['licensePlate', 'brand', 'model', 'color', 'actions'];
  user: User = new User();
  cars: CarModel[];
  dataSource = new MatTableDataSource(this.cars);
  value = '';
  editedCar: CarModel;
  editCarForm: FormGroup;
  editProfileForm: FormGroup;
  profilePhotoToView: any;

  carPhotoToView: any;
  carPhotoToEdit: any;

  defaultUploadInputLabel = 'Upload Photo';
  uploadInputLabel: string = this.defaultUploadInputLabel;

  hasCars: boolean;
  changePasswordForm: FormGroup;

  constructor(private carService: CarService,
              private userService: UserService,
              private formGenerator: FormGenerator,
              private formExtractor: FormExtractor,
              private router: Router,
              private dialog: MatDialog,
              private snackBar: MatSnackBar,
              private authenticationService: AuthenticationService,
              private sanitizer: DomSanitizer,
              private fileHandler: FileHandler) {
  }

  onRowClicked(row) {
  }

  @ViewChild(MatSort, {static: false}) sort: MatSort;

  ngAfterViewInit() {
    this.carService.getCars().toPromise().then(cars => {
      this.cars = cars.filter(car => car.ownerEmail.localeCompare(this.authenticationService.currentUserValue.email) === 0);
      this.dataSource = new MatTableDataSource<CarModel>(this.cars);
      this.dataSource.sort = this.sort;
    });
  }

  ngOnInit(): void {
    this.authenticationService.currentUser.subscribe(user => {
    this.user = user;
          if (this.user.photo) {
                this.profilePhotoToView = this.base64PhotoToUrl(this.user.photo);
              } else {
                this.profilePhotoToView = null;
          }
    });

  }

  onCarEdit(editCarTemplate, car: CarModel) {
    this.editedCar = car;
    if (car.photo) {
      this.carPhotoToEdit = this.base64PhotoToUrl(car.photo);
    } else {
      this.carPhotoToEdit = null;
    }
    this.editCarForm = this.formGenerator
      .generateCarEditForm(this.editedCar);
    this.openTemplate(editCarTemplate);
  }


  onCarDelete(car: CarModel) {
    this.carService.removeById(car.id).toPromise()
      .then(_ => {
        this.snackBar.open('Successfully', 'OK', {duration: 3000});
        this.cars.splice(this.cars.indexOf(car), 1);
        this.loadCars();
      })
      .catch(_ => {
        this.snackBar.open('Oops! Something went wrong', 'OK', {duration: 3000});
      });
  }

  updateCar() {
    const car: CarModel = this.formExtractor.extractCar(this.editCarForm);
    car.licensePlate = this.editedCar.licensePlate;
    car.photo = this.carPhotoToEdit;

    this.carService.update(car)
      .toPromise()
      .then(_ => {
        this.snackBar.open('Successfully', 'OK', {duration: 3000});
        this.loadCars();
      })
      .catch(_ => {
        this.snackBar.open('Oops! Something went wrong', 'OK', {duration: 3000});
      });
  }


  private openTemplate(template: TemplateRef<any>) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    this.dialog.open(template, dialogConfig);
  }

  loadCars() {
    this.carService.getCars().toPromise().then(cars => {
      this.cars = cars.filter(car => car.ownerEmail.localeCompare(this.authenticationService.currentUserValue.email) === 0);
      this.updateTable(this.cars);
    });
  }

  updateTable(cars: CarModel[]) {
    this.dataSource = new MatTableDataSource<CarModel>(cars);
    this.dataSource.sort = this.sort;
  }

  onCarIconClick(viewCarTemplate: TemplateRef<any>, car: CarModel) {
    if (car.photo) {
      this.carPhotoToView = this.base64PhotoToUrl(car.photo);
    } else {
      this.carPhotoToView = null;
    }
    this.openTemplate(viewCarTemplate);
  }

  private base64PhotoToUrl(base64Photo: string) {
    return this.sanitizer.bypassSecurityTrustUrl('data:Image/*;base64,' +
      base64Photo);
  }

  handleEditCarFileInput(files: FileList) {
    this.fileHandler.loadCarPhoto(files)
      .then(result => {
        this.carPhotoToEdit = result;
        this.uploadInputLabel = files.item(0).name.substring(0, 13) + '...';
      })
      .catch(error => {
        this.snackBar.open(error, 'OK', {duration: 4000});
      });
  }

  onEditProfileClick(editProfileTemplate: TemplateRef<any>) {
    this.editProfileForm = this.formGenerator.generateProfileEditForm(this.user);
    this.openTemplate(editProfileTemplate);
  }

  onChangePasswordClick(changePasswordTemplate: TemplateRef<any>) {
    this.changePasswordForm = this.formGenerator.generateChangePasswordForm();
    if (this.user.role === Role.User) {
      this.userService.hasCars(this.user.email).toPromise()
        .then(response => {
          this.hasCars = response;
        })
        .catch(_ => this.snackBar.open(
          'Oops! Something went wrong', 'OK', {duration: 3000}));
    }
    this.openTemplate(changePasswordTemplate);
  }

  changeUserPassword() {
    const oldPassword: string = this.changePasswordForm.get('oldPassword').value;
    const newPassword: string = this.changePasswordForm.get('newPassword').value;
    const newPasswordConfirm: string = this.changePasswordForm.get('newPasswordConfirm').value;
    const licensePlate: string = this.changePasswordForm.get('licensePlate').value;

    if (newPassword.localeCompare(newPasswordConfirm) !== 0) {
      this.snackBar.open('Passwords don\'t match', 'OK', {duration: 4000});
    } else {
      this.userService.changePassword(oldPassword, newPassword, licensePlate)
        .toPromise()
        .then(_ => {
          this.snackBar.open('Successfully', 'OK', {duration: 3000});
        })
        .catch(error => this.handleError(error));
    }
  }

  updateUserProfile() {
    console.log('click');
    if (this.editProfileForm.valid) {
      const user: User = this.formExtractor
        .extractUserFromProfileForm(this.editProfileForm);
      user.email = this.user.email;
      user.company = this.user.company;
      user.password = this.user.password;
      user.role = this.user.role;

      this.userService.update(user, false).toPromise()
        .then(_ => {
          this.user.firstName = user.firstName;
          this.user.lastName = user.lastName;
          this.user.telephoneNumber = user.telephoneNumber;
          this.user.age = user.age;
          this.authenticationService.updateUser(this.user);
          this.snackBar.open('Successfully', 'OK', {duration: 3000});
        })
        .catch(error => {
          this.handleError(error);
        });

    } else {
      this.snackBar.open('Fill all the required fields, please',
        'OK', {duration: 4000});
    }
  }

  handleError(httpError: string): void {
    this.snackBar.open(httpError, 'OK', {duration: 4000});
  }
}
