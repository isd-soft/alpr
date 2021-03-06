import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {MatSort} from '@angular/material/sort';
import {MatPaginator} from '@angular/material/paginator';
import {CarService} from '../shared/car.service';
import {CarModel} from '../shared/car.model';
import {FormGroup} from '@angular/forms';
import {FormGenerator} from '../utils/form.generator';
import {FormExtractor} from '../utils/form.extractor';
import {UserService} from '../shared/user.service';
import {MatTableDataSource} from '@angular/material/table';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';
import {DomSanitizer} from '@angular/platform-browser';
import {FileHandler} from '../utils/file.handler';


@Component({
  selector: 'app-car-list',
  templateUrl: './car-list.component.html',
  styleUrls: ['./car-list.component.css'],
})


export class CarListComponent implements OnInit, AfterViewInit {
  displayedColumns = ['licensePlate', 'brand', 'model', 'color', 'owner', 'telephoneNumber', 'InOut', 'actions'];
  cars: CarModel[];
  users = [];
  dataSource = new MatTableDataSource(this.cars);
  value = '';
  addCarForm: FormGroup = this.formGenerator.generateAdminCarAddForm();
  editedCar: CarModel;
  editCarForm: FormGroup;
  carPhotoToView: any;
  carPhotoToEdit: any;
  carPhotoToEditURL: any;
  carPhotoToAdd: any;

  defaultUploadInputLabel = 'Upload Photo';
  uploadInputLabel: string = this.defaultUploadInputLabel;

  @ViewChild('carAddFileInput')
  carAddFileInput: ElementRef;

  @ViewChild('carEditFileInput')
  carEditFileInput: ElementRef;

  constructor(private carService: CarService,
              private userService: UserService,
              private formGenerator: FormGenerator,
              private formExtractor: FormExtractor,
              private dialog: MatDialog,
              private snackBar: MatSnackBar,
              private sanitizer: DomSanitizer,
              private fileHandler: FileHandler) {
  }

  onRowClicked(row) {
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }


  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;

  ngAfterViewInit() {
    this.carService.getCars().toPromise().then(cars => {
      this.cars = cars;
      this.dataSource = new MatTableDataSource<CarModel>(this.cars);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    }).catch(_ => {
      this.snackBar.open('Oops! Something went wrong; Cars not loaded',
        'OK', {duration: 3000});
    });
    this.userService.getAll().toPromise()
      .then(users => {
        this.users = users;
      })
      .catch(_ => {
        this.snackBar.open('Oops! Something went wrong; Users not loaded',
          'OK', {duration: 3000});
      });
  }

  clearInput() {
    this.dataSource.filter = '';
  }


  ngOnInit(): void {
    this.loadCars();
    this.carService.getCars()
      .subscribe(cars => this.cars = cars);
  }


  onEdit(editCarTemplate, car: CarModel) {
    this.editedCar = car;
    if (car.photo) {
      this.carPhotoToEdit = this.base64PhotoToUrl(car.photo);
      this.carPhotoToEditURL = car.photo;
    } else {
      this.carPhotoToEdit = null;
    }
    this.uploadInputLabel = this.defaultUploadInputLabel;
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

  onAdd(addCarTemplate) {
    this.openTemplate(addCarTemplate);
  }

  addCar() {
    const car: CarModel = this.formExtractor.extractAdminAddCar(this.addCarForm);
    car.photo = this.carPhotoToAdd;
    this.carService.registerCar(car)
      .toPromise()
      .then(_ => {
        this.snackBar.open('Successfully', 'OK', {duration: 3000});
        this.loadCars();
      })
      .catch(_ => {
        this.snackBar.open('Oops! Something went wrong', 'OK', {duration: 3000});
      });
  }

  updateCar() {
    const car: CarModel = this.formExtractor.extractCar(this.editCarForm);
    car.licensePlate = this.editedCar.licensePlate;
    if (this.carPhotoToEdit) {
      if (this.carPhotoToEditURL.includes('data:image')) {
        car.photo = this.carPhotoToEditURL;
      } else {
        car.photo = 'data:Image/*;base64,' + this.carPhotoToEditURL;
      }
    } else {
      car.photo = null;
    }

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
    this.carService.getCars()
      .subscribe(cars => {
        this.cars = cars;
        this.updateTable(this.cars);
      });
  }

  updateTable(cars: CarModel[]) {
    this.dataSource = new MatTableDataSource<CarModel>(cars);
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
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
        this.carPhotoToEditURL = result;
        this.uploadInputLabel = files.item(0).name.substring(0, 13) + '...';
        this.carEditFileInput.nativeElement.value = '';
      })
      .catch(error => {
        this.snackBar.open(error, 'OK', {duration: 4000});
      });
  }

  handleAddCarFileInput(files: FileList) {
    this.fileHandler.loadCarPhoto(files)
      .then(result => {
        this.carPhotoToAdd = result;
        this.uploadInputLabel = files.item(0).name.substring(0, 13) + '...';
        this.carAddFileInput.nativeElement.value = '';
      })
      .catch(error => {
        this.snackBar.open(error, 'OK', {duration: 4000});
      });
  }

  removeUploadedCar() {
    this.carAddFileInput.nativeElement.value = '';
    this.carEditFileInput.nativeElement.value = '';
  }

  setDefaultPhoto() {
    this.carPhotoToEdit = null;
    this.carPhotoToAdd = null;
    this.uploadInputLabel = this.defaultUploadInputLabel;
  }
}
