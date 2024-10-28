import { isPlatformBrowser, NgClass } from '@angular/common';
import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { MatSliderModule } from '@angular/material/slider';
import { SongService } from '../../services/song.service';
import { Song } from '../../interfaces/song';
import { PlaylistService } from '../../services/playlist.service';
import { Playlist } from '../../interfaces/playlist';

enum LoopState {
  OFF,
  ONE,
  ALL
}

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
  currentPlaylist: Playlist | null = null;

  playing: boolean = false;
  currentTime: number = 0;
  songDuration: number = 0;

  loop: LoopState = LoopState.ALL;

  constructor(@Inject(PLATFORM_ID) private platformId: Object, 
  private songService: SongService, private playlistService: PlaylistService) {
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
    this.audio.addEventListener('ended', () => {
      if (this.loop != LoopState.ONE) {
      this.next();
      return;
      }
      this.goTo(0);
      this.audio?.play();
    });

    this.playlistService.getCurrentPlaylist().subscribe(playlist => this.currentPlaylist = playlist);

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
    let index = this.playlistService.currentSongIndex;
    const songs = this.currentPlaylist?.songs;
    if (index === undefined || !songs) return;

    index--;

    if (true && index < 0) index = songs.length - 1;

    this.songService.setCurrentSong(songs[index]);
    this.playlistService.currentSongIndex = index;
  }
  
  next() {
    let index = this.playlistService.currentSongIndex;
    const songs = this.currentPlaylist?.songs;
    if (index === undefined || !songs) return;

    index++;

    if (this.loop != LoopState.OFF) index %= songs.length;

    this.songService.setCurrentSong(songs[index]);
    this.playlistService.currentSongIndex = index;
  }

  shuffle() {
    // TODO
  }

  nextLoopState() {
    this.loop = (this.loop + 1) % 3;
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
