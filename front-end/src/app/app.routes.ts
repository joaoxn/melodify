import { Routes } from '@angular/router';
import { RegisterComponent } from './pages/register/register.component';

export const routes: Routes = [
    { path: 'pages/register', component: RegisterComponent },
    { path: '',   redirectTo: '/pages/register', pathMatch: 'full' }, 
];
