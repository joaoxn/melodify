import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Song } from '../interfaces/song';
import { SongRequest } from '../interfaces/song-request';

@Injectable({
  providedIn: 'root'
})
export class SongService {
  private readonly apiUrl = 'http://localhost:3000/song';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Song[]> {
    return this.http.get<Song[]>(this.apiUrl);
  }

  get(id: string): Observable<Song> {
    return this.http.get<Song>(`${this.apiUrl}?id=${id}`);
  }

  getAllArtists() {
    return this.http.get<Song[]>(`${this.apiUrl}?roleId=${2}`);
  }

  add(song: SongRequest): Observable<Song> {
    return this.http.post<Song>(this.apiUrl, song);
  }

  set(id: string, song: SongRequest): Observable<Song> {
    return this.http.put<Song>(`${this.apiUrl}/${id}`, song);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getCount(): Observable<number> {
    return this.getAll().pipe(map((songs) => songs.length));
  }

  getByRole(roleId: string): Observable<Song[]> {
    return this.http.get<Song[]>(`${this.apiUrl}?roleId=${roleId}`);
  }

  getCountByRole(roleId: string): Observable<number> {
    return this.http
      .get<Song[]>(`${this.apiUrl}?roleId=${roleId}`)
      .pipe(map((songs) => songs.length));
  }

  searchByName(name: string): Observable<Song[]> {
    return this.http.get<Song[]>(`${this.apiUrl}?name_like=${name}`);
  }
}
