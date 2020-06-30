import {AfterViewInit, Component, Injectable, OnInit, ViewChild} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {CarService} from "../shared/car.service";
import {CarModel} from "../shared/car.model";

// export interface PeriodicElement {
//   model:string;
//   licensePlate:string;
//   brand:string;
//   color:string;
//   owner:string;
//   telephoneNumber:string;
//   InOut:string;
// }
//
// const ELEMENT_DATA: PeriodicElement[] = [
// { licensePlate: 'AB BC 213', brand:'Lincoln', model: 'Lincoln ',color:'blue',owner:'Rusu Sandra', telephoneNumber: '+37368809306', InOut: 'In'},
// { licensePlate: 'CD FD 345', brand: 'Lancia',  model: 'Lancia ', color:'red', owner:'Rusu Sandra', telephoneNumber:'+37368809306',  InOut: 'In'},
// { licensePlate: 'UN MD 123', brand: 'McLaren',  model: 'McLaren ', color:'black', owner:'Rusu Sandra', telephoneNumber:'+37368809306',  InOut: 'In'},
// { licensePlate: 'QW WR 23', brand: 'BMW',  model: 'BMW ', color:'pink', owner:'Rusu Sandra', telephoneNumber:'+37368809306',  InOut: 'In'},
// { licensePlate: 'DE RT 345', brand: 'Dacia' , model: 'Lincoln ', color:'orange', owner:'Rusu Sandra', telephoneNumber:'+37368809306',  InOut: 'In'},
// { licensePlate: 'SD PL 343', brand: 'Pontiac',  model: 'Pontiac ', color:'green', owner:'Rusu Sandra', telephoneNumber:'+37368809306',  InOut: 'In'},
// { licensePlate: 'AE RT 098', brand: 'Rolls-Royce',  model: 'Lincoln ', color:'white', owner:'Rusu Sandra', telephoneNumber:'+37368809306',  InOut: 'In'},
// { licensePlate: 'YT IO 456', brand: 'SEAT',  model: 'SEAT ', color:'red', owner:'Rusu Sandra', telephoneNumber:'+37368809306', InOut: 'In'},
// { licensePlate: 'QWE UT 123', brand: 'Scion',  model: 'Scion ', color:'blue', owner:'Rusu Sandra',telephoneNumber:'+37368809306',  InOut: 'In'},
// { licensePlate: 'PU IO 345', brand:  'Saab',  model: 'Lincoln ', color:'black', owner:'Rusu Sandra', telephoneNumber:'+37368809306',  InOut: 'In'},
// ];

@Component({
  selector: 'app-car-list',
  templateUrl: './car-list.component.html',
  styleUrls: ['./car-list.component.css'],
  //providers: [CarService]
})


export class CarListComponent implements OnInit, AfterViewInit {
  displayedColumns = ["licensePlate", "brand", "model", "color", "owner", "telephoneNumber", "InOut"];
  cars: CarModel[];
  dataSource = new MatTableDataSource(this.cars);
  // dataSource = new MatTableDataSource(ELEMENT_DATA);
  value = '';


  constructor(private carService: CarService) {
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
}
