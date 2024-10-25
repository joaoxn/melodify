import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-page',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './page.component.html',
  styleUrl: './page.component.scss'
})
export class PageComponent {

}
