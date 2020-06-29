import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Injectable} from '@angular/core';

@Injectable()
export class FormGenerator {
  constructor(private fb: FormBuilder) {
  }

  public generateUserRegisterForm(): FormGroup {
    return this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      age: ['', [Validators.required, Validators.min(18)]],
      telephone: ['', [Validators.required, Validators.pattern('^\\+(373[0-9]{8})$')]],
      password: ['', [Validators.required, Validators.minLength(2)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(2)]],
      company: ['', Validators.required]
    });
  }
}
