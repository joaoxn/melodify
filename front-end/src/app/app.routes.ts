import { Routes } from '@angular/router';
import { RegisterComponent } from './pages/register/register.component';
import { PageComponent } from './shared/components/page/page.component';
import { HomeComponent } from './pages/home/home.component';

export const routes: Routes = [
    { path: 'register', component: RegisterComponent },
    { path: '',   redirectTo: 'home', pathMatch: 'full' },
    {
        path: '', component: PageComponent, children: [
            { path: 'home', component: HomeComponent },
        ]
    },
];
