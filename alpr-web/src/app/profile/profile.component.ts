import {AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {CarModel} from "../shared/car.model";
import {CarService} from "../shared/car.service";
import {User} from "../shared/user.model";
import {UserService} from "../shared/user.service";
import {Router} from '@angular/router';
import {AuthenticationService} from "../auth/authentication.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit, AfterViewInit {
  displayedColumns = ["licensePlate", "brand", "model", "color"];
  user: User = new User();
  cars: CarModel[];
  dataSource = new MatTableDataSource(this.cars);
  // dataSource = new MatTableDataSource(ELEMENT_DATA);
  value = '';

  constructor(private carService: CarService,
              private userService: UserService,
              private router: Router,
              private authenticationService : AuthenticationService) { }

  onRowClicked(row) {
      console.log('Row clicked: ', row);
  }

  @ViewChild(MatSort, {static: false}) sort: MatSort;

  ngAfterViewInit() {
      this.carService.getCars().toPromise().then(cars => {
        this.cars = cars;
        this.dataSource = new MatTableDataSource<CarModel>(this.cars);
        this.dataSource.sort = this.sort;
      });
  };

  ngOnInit(): void {
   this.user = authenticationService.currentUserValue()
  }

}
