import { Injectable } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidatorFn } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class FormValidationService {

  constructor() { }

  public getInputErrorMessage(formGroup: FormGroup, inputName: string) {
    const errors = formGroup.get(inputName)?.errors;

    if (!errors) return;
    
    if (errors['required']) return 'Required field!';
    if (errors['minlength']) {
      let err = errors['minlength']
      return 'This field requires at least ' + err['requiredLength'] + ' characters!';
    }
    if (errors['maxlength']) {
      let err = errors['maxlength']
      return 'This field cannot surpass ' + err['requiredLength'] + ' characters!';
    }
    if (errors['length']) {
      let err = errors['length']
      return 'This field must have '+ err +' characters!';
    }
    if (errors['numberLength']) {
      let err = errors['numberLength']
      return 'This field must have '+ err +' digits!';
    }
    if (errors['max']) {
      let err = errors['max']
      return 'This field cannot contain a value greater than ' + err['max'] + '!';
    }
    if (errors['min']) {
      let err = errors['min']
      return 'This field cannot contain a value smaller than ' + err['min'] + '!';
    }
    if (errors['mail']) {
      return 'Use a valid email!';
    }
    if (errors['lowercase']) {
      return 'Use at least a lowercase letter!';
    }
    if (errors['uppercase']) {
      return 'Use at least an uppercase letter!';
    }
    if (errors['number']) {
      return 'Use at least a number!';
    }
    if (errors['passwordMismatch'])
      return "The passwords don't match!";
    if (errors['pattern']) {
      return "Use a valid value prompted by the field!";
    }

    return 'Invalid Field';
  };


  public inputHasError(formGroup: FormGroup, inputName: string): boolean {
    const inputControl = formGroup.controls[inputName];

    return (inputControl.dirty || inputControl.touched) && inputControl.invalid;
  }

  public requireLength(length: number): ValidatorFn {
    return (control: AbstractControl) => {
      if (!control.value) return null;
      if (control.value.length != length)
        return { length: length };
      return null;
    }
  }

  public requireNumberLength(length: number): ValidatorFn {
    return (control: AbstractControl) => {
      const value: string = control.value;
      if (!value) return null;
      if (value.replaceAll(/\D/g, '').length != length)
        return { numberLength: length };
      return null;
    }
  }

  passwordValidator(): ValidatorFn {
    return (control: AbstractControl) => {
      const password: string = control.value;

      if (password.length < 8)
        return { length: 8 };

      if (!/^(?=.*[a-z]).+$/.test(password))
        return { lowercase: true };

      if (!/^(?=.*[A-Z]).+$/.test(password))
        return { uppercase: true };

      if (!/^(?=.*[0-9]).+$/.test(password))
        return { number: true };

      return null;
    }
  }

}
