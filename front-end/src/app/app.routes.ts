import { Routes } from '@angular/router';
import { RegisterComponent } from './pages/register/register.component';
import { PageComponent } from './shared/components/page/page.component';
import { authGuard } from './shared/guards/auth.guard';
import { HomeComponent } from './pages/home/home.component';

export const routes: Routes = [
    { path: 'register', component: RegisterComponent, canActivate: [authGuard] },
    { path: '',   redirectTo: 'home', pathMatch: 'full' },
    {
        path: '', component: PageComponent, canActivate: [authGuard], children: [
            { path: 'home', component: HomeComponent }
        ]
    },
];
