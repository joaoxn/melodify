import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DigitsPipe } from '../../pipes/digits.pipe';
import { FooterComponent } from '../footer/footer.component';
import { Song } from '../../interfaces/song';

@Component({
  selector: 'app-page',
  standalone: true,
  imports: [RouterOutlet, DigitsPipe, FooterComponent],
  templateUrl: './page.component.html',
  styleUrl: './page.component.scss'
})
export class PageComponent {
  playlist?: Song[];

  
}
