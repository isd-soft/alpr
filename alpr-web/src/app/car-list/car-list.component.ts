import {AfterViewInit, Component, Injectable, OnInit, ViewChild} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {CarService} from "../shared/car.service";
import {CarModel} from "../shared/car.model";


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
