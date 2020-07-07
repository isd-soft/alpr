import {FormGroup} from '@angular/forms';
import {User} from '../shared/user.model';
import {CarModel} from '../shared/car.model';
import {Injectable} from '@angular/core';
import {CompanyModel} from '../shared/company.model';


@Injectable()
export class FormExtractor {
  public extractUser(userDetailsForm: FormGroup) {
    return new User(
      userDetailsForm.get('email').value,
      userDetailsForm.get('firstName').value,
      userDetailsForm.get('lastName').value,
      userDetailsForm.get('age').value,
      userDetailsForm.get('telephone').value,
      userDetailsForm.get('password').value,
      userDetailsForm.get('company').value,
      userDetailsForm.get('role') === null ?
        'USER' : userDetailsForm.get('role').value);
  }

  public extractCar(carDetailsForm: FormGroup) {
    return new CarModel(
      carDetailsForm.get('id').value,
      carDetailsForm.get('licensePlate').value,
      carDetailsForm.get('brand').value,
      carDetailsForm.get('model').value,
      carDetailsForm.get('color').value,
      carDetailsForm.get('ownerName').value,
      carDetailsForm.get('ownerTelephone').value)
  }

  public extractCompany(companyDetailsForm: FormGroup) {
    return new CompanyModel(
      1,
      companyDetailsForm.get('name').value,
      companyDetailsForm.get('nrParkingSpots').value);
  }
}


