import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
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
  // dataSource = new MatTableDataSource(ELEMENT_DATA);
  value = '';
  editedCar: CarModel;
  editCarForm: FormGroup;

  carPhotoToView: any;
  carPhotoToEdit: any;

  defaultUploadInputLabel: string = 'Upload Photo';
  uploadInputLabel: string = this.defaultUploadInputLabel;

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
    console.log('Row clicked: ', row);
  }

  @ViewChild(MatSort, {static: false}) sort: MatSort;

  ngAfterViewInit() {
    this.carService.getCars().toPromise().then(cars => {
      this.cars = cars.filter(car => car.ownerEmail.localeCompare(this.authenticationService.currentUserValue.email) === 0);
      this.dataSource = new MatTableDataSource<CarModel>(this.cars);
      this.dataSource.sort = this.sort;
    });
  };

  ngOnInit(): void {
    this.user = this.authenticationService.currentUserValue;
  }

  onEdit(editCarTemplate, car: CarModel) {
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


  onDelete(car: CarModel) {
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
    let car: CarModel = this.formExtractor.extractCar(this.editCarForm);
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


  private openTemplate(template) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    this.dialog.open(template, dialogConfig);
  }

  loadCars() {
    this.carService.getCars().toPromise().then(cars => {
      this.cars = cars.filter(car => car.ownerEmail.localeCompare(this.authenticationService.currentUserValue.email) === 0);
      console.log(cars[0]);
      this.updateTable(this.cars);
    });
  }

  updateTable(cars: CarModel[]) {
    this.dataSource = new MatTableDataSource<CarModel>(cars);
    this.dataSource.sort = this.sort;
  }

  onCarIconClick(viewCarTemplate, car: CarModel) {
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
}
