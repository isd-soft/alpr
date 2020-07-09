import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {HttpErrorResponse} from '@angular/common/http';
import {MatSnackBar} from '@angular/material/snack-bar';
import {CarModel} from '../shared/car.model';
import {CarService} from '../shared/car.service';
import {Router} from '@angular/router';
import {AuthenticationService} from '../auth/authentication.service';
import {User} from '../shared/user.model';

@Component({
  selector: 'app-add-car',
  templateUrl: './add-car.component.html',
  styleUrls: ['./add-car.component.css']
})
export class AddCarComponent implements OnInit {
  car: CarModel = new CarModel();
  user: User = new User();
  fileToUpload: File = null;
  public imagePath;
  public imgURL;

  registrationForm = this.fb.group({
    licensePlate: ['', [Validators.required, Validators.pattern('^([A-Z]{3}\\s\\d{1,3}|[A-Z]{1,2}\\s[A-Z]{2}\\s\\d{2,3})$')]],
    brand: ['', Validators.required],
    model: ['', Validators.required],
    color: ['', Validators.required],
  });

  constructor(private fb: FormBuilder,
              private carService: CarService,
              private snackBar: MatSnackBar,
              private router: Router,
              private authenticationService: AuthenticationService) {
  }

  ngOnInit(): void {
    this.user = this.authenticationService.currentUserValue;
  }

  onRegister() {
    this.car.licensePlate = this.registrationForm.get('licensePlate').value;
    this.car.brand = this.registrationForm.get('brand').value;
    this.car.model = this.registrationForm.get('model').value;
    this.car.color = this.registrationForm.get('color').value;
    this.car.ownerEmail = this.user.email;
    console.log(this.car);
    this.carService.registerCar(this.car)
      .toPromise()
      .then(_ => {
        this.snackBar.open('Successfully', 'OK', {duration: 3000});
      }).catch(error => this.handleError(error));

  }

  handleError(httpError: string): void {
    this.snackBar.open(httpError, 'OK', {duration: 4000});
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);

    const mimeType = files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      this.snackBar.open('only images can be uploaded', 'OK',
        {duration: 4000});
      return;
    }

    const reader = new FileReader();
    this.imagePath = files;
    reader.readAsDataURL(files[0]);
    reader.onload = (_event) => {
      this.imgURL = reader.result;
    };
  }

  removeUploadedCar() {
    this.imgURL = null;
  }
}
