import { NgClass } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatSliderModule } from '@angular/material/slider';
import { Song } from '../../interfaces/song';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [MatSliderModule, NgClass],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.scss'
})
export class FooterComponent implements OnInit {
  song: Song = {
    name: "Young",
    artistName: "Vacations",
    views: 0
  };
  audio = new Audio();

  playing: boolean = false;
  songDuration: number = 100;
  volumeVisible: boolean = false;

  ngOnInit(): void {
      this.audio.src = 'assets/young.mp3';
      this.audio.load();
  }

  formatTime(value: any): string {
    value = Number.parseInt(value);
    if (value%60 < 10)
      return `${Math.floor(value/60)}:0${value%60}`;
    return `${Math.floor(value/60)}:${value%60}`;
  }

  previous() {

  }

  playSwitch() {
    this.playing = !this.playing;
    if (this.playing) {
      this.audio.play();
    } else {
      this.audio.pause();
    }
  }

  volumeSwitch() {
    this.volumeVisible = !this.volumeVisible;
  }
  
  next() {

  }

  volume(value: any) {
    value = Number.parseInt(value);

  }
}
