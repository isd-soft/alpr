import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {MatSnackBar} from '@angular/material/snack-bar';
import {CarModel} from '../shared/car.model';
import {CarService} from "../shared/car.service";
import {Router} from '@angular/router';

@Component({
  selector: 'app-add-car',
  templateUrl: './add-car.component.html',
  styleUrls: ['./add-car.component.css']
})
export class AddCarComponent implements OnInit {
  car: CarModel = new CarModel();

   registrationForm = this.fb.group({
      licensePlate: ['', Validators.required],
      brand: ['', Validators.required],
      model: ['', Validators.required],
      color: ['', Validators.required],
    });

      constructor(private fb: FormBuilder,
                  private carService: CarService,
                  private snackBar: MatSnackBar,
                  private router: Router) { }

  ngOnInit(): void {
  }

  onRegister() {
  this.car.licensePlate = this.registrationForm.get('licensePlate').value;
  this.car.brand = this.registrationForm.get('brand').value;
  this.car.model = this.registrationForm.get('model').value;
  this.car.color = this.registrationForm.get('color').value;
  this.car.ownerEmail = localStorage.getItem("email");
  console.log(this.car);
  this.carService.registerCar(this.car)
        .toPromise()
        .then(_ => {
        this.snackBar.open('Successfully', 'OK', {duration: 3000});
         }).catch(error => this.handleError(error));

}

  handleError(httpError: HttpErrorResponse): void {
    this.snackBar.open(httpError.error.value, 'OK', {duration: 4000});
  }

}
