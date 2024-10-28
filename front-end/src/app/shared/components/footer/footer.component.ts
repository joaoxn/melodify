import { isPlatformBrowser, NgClass } from '@angular/common';
import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { MatSliderModule } from '@angular/material/slider';
import { SongService } from '../../services/song.service';
import { Song } from '../../interfaces/song';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [MatSliderModule, NgClass],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.scss'
})

export class FooterComponent implements OnInit {
  audio?: HTMLAudioElement;
  currentSong: Song | null = null;

  playing: boolean = false;
  currentTime: number = 0;
  songDuration: number = 0;

  constructor(@Inject(PLATFORM_ID) private platformId: Object, private songService: SongService) {
    // Check if running in the browser
    if (isPlatformBrowser(this.platformId)) {
      this.audio = new Audio();

      
    window.addEventListener('keyup', this.globalKeyupListener.bind(this));

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
    if (!this.audio) return;

    this.audio.addEventListener('play', () => this.playing = true);
    this.audio.addEventListener('pause', () => this.playing = false);
    this.audio.addEventListener('ended', () => this.playing = false);

    this.songService.getCurrentSong().subscribe((song) => {
      if (!song || !this.audio) return;
      this.currentSong = song;
      
      this.audio.src = song.src;
      this.audio.load();
      this.audio.play();
    })
  }

  globalKeyupListener(event: KeyboardEvent) {
    if (event.code === 'Space') {
      this.playing ? this.audio?.pause() : this.audio?.play();
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
