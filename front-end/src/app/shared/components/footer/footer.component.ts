import { isPlatformBrowser, NgClass } from '@angular/common';
import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { MatSliderModule } from '@angular/material/slider';
import { Song } from '../../interfaces/song';
import { log } from 'node:console';

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
  audio?: HTMLAudioElement;

  playing: boolean = false;
  currentTime: number = 0;
  songDuration: number = 0;

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {
    // Check if running in the browser
    if (isPlatformBrowser(this.platformId)) {
      this.audio = new Audio("assets/Young.mp3");

      
    window.addEventListener('keydown', this.globalKeydownListener.bind(this));

      this.audio.onloadedmetadata = () => {
        this.songDuration = this.audio!.duration;
      };

      // Update current time every second
      this.audio.ontimeupdate = () => {
        this.currentTime = this.audio!.currentTime;
      };
    }
  }

  ngOnInit(): void {
    this.audio?.addEventListener('play', () => this.playSwitch(true));
    this.audio?.addEventListener('pause', () => this.playSwitch(false));
    this.audio?.addEventListener('ended', () => this.playSwitch(false));
  }

  globalKeydownListener(event: KeyboardEvent) {
    if (event.code === 'Space') {
      this.playSwitch();
    }
  }

  formatTime(value: any): string {
    value = Number.parseInt(value);
    if (value%60 < 10)
      return `${Math.floor(value/60)}:0${value%60}`;
    return `${Math.floor(value/60)}:${value%60}`;
  }

  previous() {
    if (!this.audio) return;
    
  }
  
  next() {
    if (!this.audio) return;
    

  }

  playSwitch(playing?: boolean) {
    console.log("playSwitch called");
    console.log("Playing variable:", this.playing);
    if (playing)
      this.playing = playing;
    else
      this.playing = !this.playing;

    if (!this.audio) return;

    if (this.playing) {
      this.audio.play();
    } else {
      this.audio.pause();
    }
    
    console.log("Playing variable after call:", this.playing);
  }
  
  goTo(timestamp: any) {
    if (!this.audio) return;
    
    timestamp = Number.parseInt(timestamp);

    this.audio.currentTime = timestamp;
  }

  volume(value: any) {
    if (!this.audio) return;

    value = Math.pow(Number.parseInt(value)/100, 2);
    this.audio.volume = value;
  }
}
