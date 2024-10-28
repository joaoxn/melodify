import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { FormControl, FormGroup, Validators, FormsModule, ReactiveFormsModule, FormBuilder, ValidatorFn, AbstractControl } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { UserService } from '../../shared/services/user.service';
import { FormValidationService } from '../../shared/services/form-validation.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    RouterLink, MatFormFieldModule, MatInputModule, MatButtonModule, MatIconModule,
    FormsModule, ReactiveFormsModule
  ],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class RegisterComponent implements OnInit {
  form!: FormGroup;
  hidePassword = true;

  serviceLoading = false;
  globalErrorMessage?: string;

  constructor(
    private userService: UserService, private formValidationService: FormValidationService,
    private fb: FormBuilder, private router: Router
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, this.formValidationService.passwordValidator()]],
      confirmPassword: ['', Validators.required]
    })
  }
  
  confirmPasswordChange() {
    const password = this.form.get('password');
    const confirmPassword = this.form.get('confirmPassword');
    
    if (confirmPassword && password && confirmPassword.value !== password.value) {
      confirmPassword.setErrors({ passwordMismatch: true });
    } else {
      confirmPassword?.setErrors(null);  // Clear error if they match
    }
  }
  
  togglePasswordVisibility() {
    this.hidePassword = !this.hidePassword;
  }
  
  hasError(inputName: string): boolean {
    return this.formValidationService.inputHasError(this.form, inputName);
  }

  getError(inputName: string): string | undefined {
    return this.formValidationService.getInputErrorMessage(this.form, inputName);
  }

  register() {
    if (this.form.invalid) {
      console.error('Form is invalid');
      this.form.markAllAsTouched();
      return;
    }

    const defaultUserErrorMessage = "E-mail já cadastrado. Entre ou cadastre um novo e-mail!";
    const defaultServerErrorMessage = "Erro ao cadastrar-se! Tente novamente mais tarde...";

    const newUser = {
      name: this.form.get('name')!.value,
      email: this.form.get('email')!.value,
      password: this.form.get('password')!.value,
      roleId: "0"
    }

    console.log("Adding new user:", newUser.name, "with email:", newUser.email);

    this.userService.register(newUser).subscribe({
      next: (user) => {
        console.log('User added successfully:', user);
        this.router.navigate(['/home']);
      },
      error: (error: Error) => {
        this.serviceLoading = false;
        console.error(error);
        if (error.message.slice(0, 21) === "DuplicateEntityError:")
          this.globalErrorMessage = defaultUserErrorMessage;
        else
          this.globalErrorMessage = "Erro ao cadastrar-se! Tente novamente mais tarde...";
      }
    });
  }
}