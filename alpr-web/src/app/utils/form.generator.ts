import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Injectable} from '@angular/core';
import {User} from '../shared/user.model';
import {CarModel} from '../shared/car.model';
import {CompanyModel} from '../shared/company.model';


@Injectable()
export class FormGenerator {
  constructor(private fb: FormBuilder) {
  }

  public generateUserRegisterForm(): FormGroup {
    return this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      firstName: ['', [Validators.required, Validators.pattern('^[a-zA-Z]+$')]],
      lastName: ['', [Validators.required, Validators.pattern('^[a-zA-Z]+$')]],
      age: ['', [Validators.required, Validators.min(18)]],
      telephone: ['', [Validators.required, Validators.pattern('^\\+(373[0-9]{8})$')]],
      password: ['', [Validators.required, Validators.minLength(2)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(2)]],
      company: ['', Validators.required]
    });
  }

  public generateUserAddForm(): FormGroup {
    const form = this.generateUserRegisterForm();
    form.addControl('role',
      new FormControl('', Validators.required));
    return form;
  }

  public generateUserEditForm(user: User): FormGroup {
    return this.fb.group({
      email: [user.email, [Validators.required, Validators.email]],
      firstName: [user.firstName, [Validators.required, Validators.pattern('^[a-zA-Z]+$')]],
      lastName: [user.lastName, [Validators.required, Validators.pattern('^[a-zA-Z]+$')]],
      age: [user.age, [Validators.required, Validators.min(18)]],
      telephone: [user.telephoneNumber, [Validators.required, Validators.pattern('^\\+(373[0-9]{8})$')]],
      password: [user.password, [Validators.required, Validators.minLength(2)]],
      confirmPassword: [user.password, [Validators.required, Validators.minLength(2)]],
      company: [user.company, Validators.required],
      role: [user.role, Validators.required]
    });
  }


  public generateCarEditForm(car: CarModel): FormGroup {
    return this.fb.group({
      id: [car.id],
      licensePlate: [car.licensePlate, [Validators.required,
        Validators.pattern('^([A-Z]{3}\\s\\d{1,3}|[A-Z]{1,2}\\s[A-Z]{2}\\s\\d{2,3})$')]],
      brand: [car.brand, Validators.required],
      model: [car.model, Validators.required],
      color: [car.color, Validators.required],
      ownerName: [car.ownerName, Validators.required],
      ownerTelephone: [car.ownerTelephone, Validators.required]
    });
  }

  public generateCompanyAddForm(): FormGroup {
    return this.fb.group({
      name: ['', Validators.required],
      nrParkingSpots: ['', Validators.required]
    });
  }

  public generateCompanyEditForm(companyModel: CompanyModel): FormGroup {
    console.log(companyModel.name);
    return this.fb.group({
      name: [companyModel.name, Validators.required],
      nrParkingSpots: [companyModel.nrParkingSpots, Validators.required]
    });
  }

  public generateChangePasswordForm(): FormGroup {
    return this.fb.group({
      licensePlate: ['', Validators.pattern
      ('^([A-Z]{3}\\s\\d{1,3}|[A-Z]{1,2}\\s[A-Z]{2}\\s\\d{2,3})$')],
      oldPassword: ['', Validators.required],
      newPassword: ['', Validators.required],
      newPasswordConfirm: ['', Validators.required]
    });
  }
}
