import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {HttpErrorResponse} from '@angular/common/http';
import {MatSnackBar} from '@angular/material/snack-bar';
import {CarModel} from '../shared/car.model';
import {CarService} from '../shared/car.service';
import {Router} from '@angular/router';
import {AuthenticationService} from '../auth/authentication.service';
import {User} from '../shared/user.model';
import {FileHandler} from '../utils/file.handler';

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
  labelDefault: string = 'Upload Photo';
  label: string = this.labelDefault;

  @ViewChild('carFileInput')
  carFileInput: ElementRef;

  constructor(private fb: FormBuilder,
              private carService: CarService,
              private snackBar: MatSnackBar,
              private router: Router,
              private authenticationService: AuthenticationService,
              private fileHandler: FileHandler) {
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
    this.car.photo = this.imgURL;

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

  handleFileInput(files: FileList) {
    console.log("received file")
    let fileToUpload = files.item(0);

    this.fileHandler.loadCarPhoto(files)
      .then(result => {
        this.imgURL = result;
        this.label = fileToUpload.name;
      })
      .catch(error => {
        this.snackBar.open(error, 'OK', {duration: 4000});
      });
  }

  removeUploadedCar() {
    this.imgURL = null;
    this.carFileInput.nativeElement.value = '';
    this.label = this.labelDefault;
  }
}
