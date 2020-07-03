import {AfterViewInit, Component, Injectable, OnInit, ViewChild} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {CarService} from "../shared/car.service";
import {CarModel} from "../shared/car.model";
import {User} from "../shared/user.model";
import {FormGroup} from "@angular/forms";
import { FormGenerator} from "../utils/form.generator";
import {FormExtractor} from "../utils/form.extractor";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";


@Component({
  selector: 'app-car-list',
  templateUrl: './car-list.component.html',
  styleUrls: ['./car-list.component.css'],
})


export class CarListComponent implements OnInit, AfterViewInit {
  displayedColumns = ["licensePlate", "brand", "model", "color", "owner", "telephoneNumber", "InOut", "actions"];
  cars: CarModel[];
  dataSource = new MatTableDataSource(this.cars);
  value = '';

  editedCar: CarModel;
  editCarForm: FormGroup;



  constructor(private carService: CarService,
              private formGenerator: FormGenerator,
              private formExtractor: FormExtractor,
              private dialog: MatDialog,
              private snackBar: MatSnackBar) {
  }

  onRowClicked(row) {
    console.log('Row clicked: ', row);
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
    });
  };

  clearInput() {
    this.dataSource.filter = '';
  }



  ngOnInit(): void {
  }


  onEdit(editCarTemplate, car: CarModel) {
    this.editedCar = car;
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
        console.log(cars[0]);
        this.updateTable(this.cars);
      });
  }

  updateTable(cars: CarModel[]) {
    this.dataSource = new MatTableDataSource<CarModel>(cars);
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }
}
