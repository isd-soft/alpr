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
import {FormGenerator} from "../utils/form.generator";
import {FormExtractor} from "../utils/form.extractor";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {AuthenticationService} from "../auth/authentication.service";

@Component({
  selector: 'app-company-cars',
  templateUrl: './company-cars.component.html',
  styleUrls: ['./company-cars.component.css']
})
export class CompanyCarsComponent implements OnInit, AfterViewInit {
  displayedColumns = ["licensePlate", "brand", "model", "color", "owner", "telephoneNumber", "InOut"];
  cars: CarModel[];
  dataSource = new MatTableDataSource(this.cars);
  value = '';


  constructor(private carService: CarService,
              private formGenerator: FormGenerator,
              private formExtractor: FormExtractor,
              private dialog: MatDialog,
              private snackBar: MatSnackBar,
              private authenticationService : AuthenticationService) { }

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

      loadCars() {
        this.carService.getCars().toPromise().then(cars => {
        this.cars = cars.filter(car => car.ownerEmail.localeCompare(this.authenticationService.currentUserValue.company) === 0);
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
