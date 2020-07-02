import {AfterViewInit, Component, OnInit } from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {CarModel} from "../shared/car.model";
import {CarService} from "../shared/car.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit, AfterViewInit {
  displayedColumns = ["licensePlate", "brand", "model", "color"];
  cars: CarModel[];
  dataSource = new MatTableDataSource(this.cars);
  // dataSource = new MatTableDataSource(ELEMENT_DATA);
  value = '';

  constructor(private carService: CarService) { }

  onRowClicked(row) {
      console.log('Row clicked: ', row);
  }

  ngAfterViewInit() {
      this.carService.getCars().toPromise().then(cars => {
        this.cars = cars;
        this.dataSource = new MatTableDataSource<CarModel>(this.cars);
      });
  };

  ngOnInit(): void {
  }

}
