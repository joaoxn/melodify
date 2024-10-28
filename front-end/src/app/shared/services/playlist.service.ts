import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RawPlaylist, Playlist } from '../interfaces/playlist';
import { BehaviorSubject, forkJoin, map, Observable, switchMap } from 'rxjs';
import { Request } from '../interfaces/generics';
import { Role } from '../interfaces/role';
import { UserService } from './user.service';
import { SongService } from './song.service';

@Injectable({
  providedIn: 'root'
})
export class PlaylistService {
  private readonly apiUrl = 'http://localhost:3000/playlist';

  private currentPlaylistSubject = new BehaviorSubject<Playlist | null>(null);

  constructor(
    private http: HttpClient,
    private userService: UserService,
    private songService: SongService
  ) {
    this.get("0").subscribe(playlist => {
      this.getFromRaw(playlist).subscribe(playlist => this.setCurrentPlaylist(playlist)); this.getCurrentPlaylist().subscribe(playlist => console.log("playlist service with current playlist:", playlist));
    }
    );
  }

  getCurrentPlaylist(): Observable<Playlist | null> {
    return this.currentPlaylistSubject.asObservable();
  }

  setCurrentPlaylist(song: Playlist | null): void {
    this.currentPlaylistSubject.next(song);
  }

  getAll(): Observable<RawPlaylist[]> {
    return this.http.get<RawPlaylist[]>(this.apiUrl);
  }

  get(id: string): Observable<RawPlaylist> {
    return this.http.get<RawPlaylist>(`${this.apiUrl}/${id}`);
  }

  getFromRaw(rawPlaylist: RawPlaylist): Observable<Playlist> {
    const allowedUsers$ = forkJoin(
        rawPlaylist.allowedUserIds.map(id => this.userService.get(id).pipe(
            switchMap(user => this.userService.getFromRaw(user))
        ))
    );

    const songs$ = forkJoin(
        rawPlaylist.songIds.map(id => this.songService.get(id).pipe(
            switchMap(song => this.songService.getFromRaw(song))
        ))
    );

    return forkJoin([allowedUsers$, songs$]).pipe(
        map(([allowedUsers, songs]) => ({
            ...rawPlaylist, 
            allowedUsers, 
            songs
        }))
    );
}


  add(playlist: Request<RawPlaylist>): Observable<RawPlaylist> {
    return this.http.post<RawPlaylist>(this.apiUrl, playlist);
  }

  set(id: string, playlist: Request<RawPlaylist>): Observable<RawPlaylist> {
    return this.http.put<RawPlaylist>(`${this.apiUrl}/${id}`, playlist);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getCount(): Observable<number> {
    return this.getAll().pipe(map((playlists) => playlists.length));
  }

  searchByName(name: string): Observable<RawPlaylist[]> {
    return this.http.get<RawPlaylist[]>(`${this.apiUrl}?name_like=${name}`);
  }
}
