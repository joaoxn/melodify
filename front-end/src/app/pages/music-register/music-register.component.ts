import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { FormControl, FormGroup, Validators, FormsModule, ReactiveFormsModule, FormBuilder, ValidatorFn, AbstractControl } from '@angular/forms';
import { UserService } from '../../shared/services/user.service';
import { FormValidationService } from '../../shared/services/form-validation.service';
import { LoopState } from '../../shared/enums/loop-state';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatNativeDateModule } from '@angular/material/core';


@Component({
  selector: 'app-music-register',
  standalone: true,
  imports: [
    RouterLink, MatFormFieldModule, MatInputModule, MatButtonModule, MatIconModule,
    FormsModule, ReactiveFormsModule, MatDatepickerModule, MatFormFieldModule,
    MatInputModule, MatSelectModule, MatButtonModule, MatNativeDateModule
  ],
  templateUrl: './music-register.component.html',
  styleUrl: './music-register.component.scss'
})
export class MusicRegisterComponent {
  toppings = new FormControl('');
  toppingList: string[] = ['Pop', 'Trap', 'Geekie', 'K-pop', 'HipHop', 'JAVA ETC'];

  uploadForm: FormGroup;
selectedCover: File | null = null;

constructor(private formBuilder: FormBuilder) {
  this.uploadForm = this.formBuilder.group({
    cover: [null, Validators.required], 
  });
}


onFileSelected(event: Event, type: 'cover') {
  const input = event.target as HTMLInputElement;
  if (input.files && input.files.length > 0) {
    if (type === 'cover') {
      this.selectedCover = input.files[0];
      console.log('Selected cover image:', this.selectedCover.name);
    }
  }
}
}
  