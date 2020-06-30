import {FormGroup} from '@angular/forms';
import {User} from '../shared/user.model';
import {Injectable} from '@angular/core';

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
        'USER_ROLE' : userDetailsForm.get('role').value);
  }
}
