import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Injectable} from '@angular/core';
import {User} from '../shared/user.model';

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
    let form = this.generateUserRegisterForm();
    form.addControl('role',
      new FormControl('', Validators.required));
    return form;
  }

  public generateUserEditForm(user: User): FormGroup {
    console.log(user.company);
    console.log(user.telephoneNumber);
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
}
