import { NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { UserService } from '../../shared/services/user.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-user-perfil',
  standalone: true,
  imports: [NgIf, RouterLink],
  templateUrl: './user-perfil.component.html',
  styleUrls: ['./user-perfil.component.scss']
})
export class UserPerfilComponent {
  userData: { name: string, email: string } | null = null;
  loading: boolean = true;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    const authObservable = this.userService.validateAuth();

    if (authObservable) {
      authObservable.subscribe({
        next: (user) => {
          this.userData = { name: user.name, email: user.email };
          this.loading = false;
        },
        error: (err) => {
          console.error('Usuário não autenticado ou sessão expirada.', err);
          this.loading = false;
        }
      });
    } else {
      console.log('Usuário não autenticado.');
      this.loading = false;
    }
  }
}
