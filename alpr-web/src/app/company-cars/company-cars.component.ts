import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {MatSort} from '@angular/material/sort';
import {MatPaginator} from '@angular/material/paginator';
import {CarService} from '../shared/car.service';
import {CarModel} from '../shared/car.model';
import {FormGenerator} from '../utils/form.generator';
import {FormExtractor} from '../utils/form.extractor';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';
import {AuthenticationService} from '../auth/authentication.service';
import {DomSanitizer} from '@angular/platform-browser';
import {User} from '../shared/user.model';
import {ParkingHistory} from '../shared/parking.history.model';


@Component({
  selector: 'app-company-cars',
  templateUrl: './company-cars.component.html',
  styleUrls: ['./company-cars.component.css']
})
export class CompanyCarsComponent implements OnInit, AfterViewInit {
  displayedColumns = ['licensePlate', 'brand', 'model', 'color', 'owner', 'telephoneNumber', 'InOut'];
  cars: CarModel[];
  dataSource = new MatTableDataSource(this.cars);
  value = '';
  carPhotoToView: any;
  user: User;
  history: ParkingHistory = new ParkingHistory('', 0, 0);

  constructor(private carService: CarService,
              private formGenerator: FormGenerator,
              private formExtractor: FormExtractor,
              private dialog: MatDialog,
              private snackBar: MatSnackBar,
              private authenticationService: AuthenticationService,
              private sanitizer: DomSanitizer) {
  }

  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;

  onRowClicked(row) {
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  ngAfterViewInit() {
    this.carService.getCarsByCompanyName(this.authenticationService.currentUserValue.company).toPromise().then(cars => {
      this.cars = cars;
      this.dataSource = new MatTableDataSource<CarModel>(this.cars);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    });
  }

  clearInput() {
    this.dataSource.filter = '';
  }

  ngOnInit(): void {
    this.user = this.authenticationService.currentUserValue;
    this.carService.getParkingHistory(this.authenticationService.currentUserValue.company).subscribe(
      history => this.history = history
    );
  }

  loadCars() {
    this.carService.getCarsByCompanyName(this.authenticationService.currentUserValue.company).subscribe(cars => {
      this.cars = cars;
      this.updateTable(this.cars);
    });
  }

  updateTable(cars: CarModel[]) {
    this.dataSource = new MatTableDataSource<CarModel>(cars);
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  private openTemplate(template) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    this.dialog.open(template, dialogConfig);
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
}
