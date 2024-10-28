import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DigitsPipe } from '../../pipes/digits.pipe';
import { HeaderComponent } from '../header/header.component';


@Component({
  selector: 'app-page',
  standalone: true,
  imports: [RouterOutlet, DigitsPipe, HeaderComponent],
  templateUrl: './page.component.html',
  styleUrl: './page.component.scss'
})
export class PageComponent {
  playlist: {
    id: string,
    name: string,
    artist: string,
    thumbnail: string
  }[] = [{
    id: "0", 
    name: "1989", 
    artist: "Taylor Swift", 
    thumbnail: "assets/album-taylor.jpeg"
  }]
}
