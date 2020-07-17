import {Component, ElementRef, OnInit , ViewChild} from '@angular/core';
import {User} from '../shared/user.model';
import {MatSnackBar} from '@angular/material/snack-bar';
import {UserService} from '../shared/user.service';
import {CompanyService} from '../shared/company.service';
import {Router} from '@angular/router';
import {FormExtractor} from '../utils/form.extractor';
import {FormGenerator} from '../utils/form.generator';
import {FileHandler} from '../utils/file.handler';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  user: User = new User();
  public imgURL;
  passwordConfirm = '';

  companies = [];

  registrationForm = this.formGenerator.generateUserRegisterForm();

  labelDefault = 'Upload Photo';
  label: string = this.labelDefault;

  @ViewChild('userFileInput')
  userFileInput: ElementRef;

  constructor(private userService: UserService,
              private snackBar: MatSnackBar,
              private companyService: CompanyService,
              private router: Router,
              private formExtractor: FormExtractor,
              private formGenerator: FormGenerator,
              private fileHandler: FileHandler) {
  }

  ngOnInit(): void {
    this.companyService.getAll().subscribe(companies => this.companies = companies);
  }

  onRegister() {
    this.user = this.formExtractor.extractUser(this.registrationForm);
    this.passwordConfirm = this.registrationForm.get('confirmPassword').value;

    if (this.user.password.localeCompare(this.passwordConfirm) !== 0) {
      this.snackBar.open('Passwords don\'t match', 'OK', {duration: 4000});
    } else {
      this.user.photo = this.imgURL;
      this.userService.registerUser(this.user)
        .toPromise()
        .then(_ => {
          this.snackBar.open('Successfully', 'OK', {duration: 3000});
          this.router.navigate(['login']);
        })
        .catch(error => {
          this.handleError(error);
        });
    }
  }

  handleError(httpError: string): void {
    this.snackBar.open(httpError, 'OK', {duration: 4000});
  }

  handleFileInput(files: FileList) {
      const fileToUpload = files.item(0);

      this.fileHandler.loadCarPhoto(files)
        .then(result => {
          this.imgURL = result;
          this.label = fileToUpload.name;
        })
        .catch(error => {
          this.snackBar.open(error, 'OK', {duration: 4000});
        });
  }

  removeUploadedPhoto() {
      this.imgURL = null;
      this.userFileInput.nativeElement.value = '';
      this.label = this.labelDefault;
  }

  moveToLogin() {
    this.router.navigate(['/login'])
  }
}
