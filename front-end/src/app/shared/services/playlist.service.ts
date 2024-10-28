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
  ) { }

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
    return forkJoin(rawPlaylist.allowedUserIds.map(id => this.userService.get(id))).pipe(
      map(allowedUsers => {
        return {
          ...rawPlaylist,
          allowedUsers
        };
      }),
      switchMap((obj) => {
        return forkJoin(obj.songIds?.map((id) => this.songService.get(id)))
          .pipe(map((songs) => ({ ...obj, songs })))
      }),
      switchMap(obj =>
        forkJoin(obj.allowedUsers.map(user => this.userService.getFromRaw(user)))
          .pipe(map((allowedUsers) => ({ ...obj, allowedUsers })))
      ),
      switchMap(obj =>
        forkJoin(obj.songs.map(song => this.songService.getFromRaw(song)))
          .pipe(map((songs) => ({ ...obj, songs })))
      )
    )
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
