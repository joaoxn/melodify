import { Routes } from '@angular/router';
import { RegisterComponent } from './pages/register/register.component';
import { HomeComponent } from './pages/home/home.component';
import { PageComponent } from './shared/components/page/page.component';
import { authGuard } from './shared/guards/auth.guard';
import { LoginComponent } from './pages/login/login.component';

export const routes: Routes = [
    { path: 'register', component: RegisterComponent, canActivate: [authGuard] },
    { path: 'login', component: LoginComponent, canActivate: [authGuard] },
    { path: '',   redirectTo: 'home', pathMatch: 'full' },
    {
        path: '', component: PageComponent, canActivate: [authGuard], children: [
            { path: 'home', component: HomeComponent }
        ]
    },
];
