import { Component, OnInit } from '@angular/core';
import { Song } from '../../interfaces/song';
import { DigitsPipe } from '../../pipes/digits.pipe';
import { LimitPipe } from '../../pipes/limit.pipe';
import { PlaylistService } from '../../services/playlist.service';
import { Playlist } from '../../interfaces/playlist';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [DigitsPipe, LimitPipe],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent implements OnInit {
  playlist?: Playlist;

  constructor(private playlistService: PlaylistService) { }

  ngOnInit(): void {
    this.playlistService.getCurrentPlaylist().subscribe(playlist => this.playlist = playlist || undefined);
  }

  
}
