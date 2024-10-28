import { isPlatformBrowser, NgClass } from '@angular/common';
import { Component, Inject, OnDestroy, OnInit, PLATFORM_ID } from '@angular/core';
import { MatSliderModule } from '@angular/material/slider';
import { SongService } from '../../services/song.service';
import { Song } from '../../interfaces/song';
import { PlaylistService } from '../../services/playlist.service';
import { Playlist } from '../../interfaces/playlist';
import { UserService } from '../../services/user.service';
import { User } from '../../interfaces/user';

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

export class FooterComponent implements OnInit, OnDestroy {
  currentUser?: User;

  audio?: HTMLAudioElement;
  currentSong: Song | null = null;
  currentPlaylist: Playlist | null = null;

  playing: boolean = false;
  currentTime: number = 0;
  songDuration: number = 0;

  loop: LoopState = LoopState.ALL;

  constructor(@Inject(PLATFORM_ID) private platformId: Object,
    private songService: SongService, 
    private playlistService: PlaylistService,
    private userService: UserService) {
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
    this.currentUser = this.userService.currentUser;
    if (!this.audio) return;

    this.audio.addEventListener('play', () => this.playing = true);
    this.audio.addEventListener('pause', () => this.playing = false);
    this.audio.addEventListener('ended', this.handleEndOfSong);

    this.playlistService.getCurrentPlaylist().subscribe(playlist => this.currentPlaylist = playlist);

    this.songService.getCurrentSong().subscribe((song) => {
      if (!song || !this.audio) return;
      this.currentSong = song;

      this.audio.src = song.src;
      this.audio.load();
      this.audio.play();
      this.updateMediaSession();
    })

    navigator.mediaSession.setActionHandler('play', () => this.audio?.play());
    navigator.mediaSession.setActionHandler('pause', () => this.audio?.pause());
    navigator.mediaSession.setActionHandler('previoustrack', () => this.previous());
    navigator.mediaSession.setActionHandler('nexttrack', () => this.next());
  }

  ngOnDestroy(): void {
    if (this.currentUser?.config)
      this.userService.changeCurrent(this.currentUser);
  }

  globalKeyupListener(event: KeyboardEvent) {
    if (event.code === 'Space') {
      this.playing ? this.audio?.pause() : this.audio?.play();
    }
  }

  handleEndOfSong() {
    switch (this.loop) {
      case LoopState.OFF:
        if (this.currentSong?.id != this.currentPlaylist?.songs.at(-1)?.id)
          this.next();
        break;
      case LoopState.ONE:
        this.goTo(0);
        this.audio?.play();
        break;
      case LoopState.ALL:
        this.audio?.play();
        break;
      default:
        this.next();
    }
  }

  updateMediaSession() {
    if (!('mediaSession' in navigator) || !this.currentSong) return;

    navigator.mediaSession.metadata = new MediaMetadata({
      title: this.currentSong.name,
      artist: this.currentSong.performer?.name || 'Unknown Artist',
      album: this.currentPlaylist?.name || 'Unknown Album',
      artwork: [
        { src: this.currentSong.thumbnailSrc || '/assets/no-album-cover.jpg', sizes: '512x512', type: 'image/png' }
      ]
    });
  }

  formatTime(value: any): string {
    value = Number.parseInt(value);
    if (value % 60 < 10)
      return `${Math.floor(value / 60)}:0${value % 60}`;
    return `${Math.floor(value / 60)}:${value % 60}`;
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
    index %= songs.length;

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

    const volume = Math.pow(Number.parseInt(value) / 100, 2);
    if (volume == this.audio.volume) return;
    this.audio.volume = volume;

    if (!this.currentUser?.config) return;

    this.currentUser.config.volume = value;
  }
}
