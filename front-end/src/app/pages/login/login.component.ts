import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { FormControl, FormGroup, Validators, FormsModule, ReactiveFormsModule, FormBuilder } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { CommonModule } from '@angular/common'; 
import { UserService } from '../../shared/services/user.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink, MatFormFieldModule, MatInputModule, MatButtonModule, MatIconModule,
    FormsModule, ReactiveFormsModule
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class LoginComponent implements OnInit {
  form!: FormGroup;
  hidePassword = true;
  serviceLoading = false;
  globalErrorMessage?: string;

  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  togglePasswordVisibility() {
    this.hidePassword = !this.hidePassword;
  }

  login() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
  
    this.serviceLoading = true;
    this.globalErrorMessage = undefined; 
  
    const { email, password } = this.form.value;
  
    this.userService.login(email, password).subscribe({
      next: () => {
        this.serviceLoading = false;
        this.router.navigate(['/home']);
      },
      error: (error) => {
        this.serviceLoading = false;
        this.globalErrorMessage = error.message.includes("UnauthorizedError")
          ? "Incorrect email or password. Try again."
          : "Error when logging in. Please try again later.";
      }
    });
  }  
}
