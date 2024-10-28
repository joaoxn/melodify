import { Component, OnInit } from '@angular/core';
import { Song } from '../../interfaces/song';
import { DigitsPipe } from '../../pipes/digits.pipe';
import { LimitPipe } from '../../pipes/limit.pipe';
import { PlaylistService } from '../../services/playlist.service';
import { Playlist } from '../../interfaces/playlist';
import { SongService } from '../../services/song.service';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [NgClass, DigitsPipe, LimitPipe],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent implements OnInit {
  playlist?: Playlist;
  playingId?: string;

  constructor(private playlistService: PlaylistService, private songService: SongService) { }

  ngOnInit(): void {
    this.playlistService.getCurrentPlaylist().subscribe(playlist => this.playlist = playlist || undefined);
    this.songService.getCurrentSong().subscribe(song => this.playingId = song?.id);
  }

  select(song: Song, index: number): void {
    this.songService.setCurrentSong(song);
    this.playlistService.currentSongIndex = index;
  }
}
