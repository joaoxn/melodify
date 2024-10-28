import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, forkJoin, map, Observable, of, switchMap } from 'rxjs';
import { RawSong, Song } from '../interfaces/song';
import { Genre } from '../interfaces/genre';
import { PerformerService } from './performer.service';
import { Performer } from '../interfaces/performer';
import { Request } from '../interfaces/generics';

@Injectable({
  providedIn: 'root'
})
export class SongService {
  private readonly apiUrl = 'http://localhost:3000/song';
  private readonly genreUrl = 'http://localhost:3000/genre';
  
  private currentSongSubject = new BehaviorSubject<Song | RawSong | null>(null);
  
  constructor(private http: HttpClient, private performerService: PerformerService) { }

  getCurrentSong(): Observable<Song | RawSong | null> {
    return this.currentSongSubject.asObservable();
  }

  setCurrentSong(song: Song | RawSong | null): void {
    this.currentSongSubject.next(song);
  }

  getAll(): Observable<RawSong[]> {
    return this.http.get<RawSong[]>(this.apiUrl);
  }
  
  get(id: string): Observable<RawSong> {
    return this.http.get<RawSong>(`${this.apiUrl}/${id}`)
  }

  getFromRaw(rawUser: RawSong): Observable<RawSong> {
    const getPerformerObservable: Observable<Performer | undefined> = rawUser.performerId ? this.performerService.get(rawUser.performerId) : of(undefined);
    return getPerformerObservable.pipe(
      map((role) => {
        return {
          ...rawUser,
          role: role
        };
      }),
      switchMap((obj) => {
        return forkJoin(obj.genreIds?.map(genreId => this.getGenre(genreId)))
        .pipe(map((genres) => ({ ...obj, genres })))
    })
    )
  }

  add(song: Request<RawSong>): Observable<RawSong> {
    return this.http.post<RawSong>(this.apiUrl, song);
  }

  set(id: string, song: Request<RawSong>): Observable<RawSong> {
    return this.http.put<RawSong>(`${this.apiUrl}/${id}`, song);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getCount(): Observable<number> {
    return this.getAll().pipe(map((songs) => songs.length));
  }

  getByGenre(genreId: string): Observable<RawSong[]> {
    return this.http.get<RawSong[]>(`${this.apiUrl}?genreId=${genreId}`);
  }

  getCountByGenreId(genreId: string): Observable<number> {
    return this.http
      .get<RawSong[]>(`${this.apiUrl}?roleId=${genreId}`)
      .pipe(map((songs) => songs.length));
  }

  searchByName(name: string): Observable<RawSong[]> {
    return this.http.get<RawSong[]>(`${this.apiUrl}?name_like=${name}`);
  }

  getAllGenres(): Observable<Genre[]> {
    return this.http.get<Genre[]>(this.genreUrl);
  }

  getGenre(id: string): Observable<Genre> {
    return this.http.get<Genre>(`${this.genreUrl}/${id}`);
  }

  addGenre(name: string): Observable<Genre> {
    return this.http.post<Genre>(this.genreUrl, {name: name});
  }

  setGenre(id: string, name: string): Observable<Genre> {
    return this.http.put<Genre>(`${this.genreUrl}/${id}`, {name: name});
  }

  deleteGenre(id: string): Observable<void> {
    return this.http.delete<void>(`${this.genreUrl}/${id}`);
  }
}
