import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Performer } from '../interfaces/performer';
import { map, Observable } from 'rxjs';
import { Request } from '../interfaces/generics';

@Injectable({
  providedIn: 'root'
})
export class PerformerService {
  private readonly apiUrl = 'http://localhost:3000/Performer';

  constructor(private http: HttpClient) { }

  getAll(): Observable<Performer[]> {
    return this.http.get<Performer[]>(this.apiUrl);
  }

  get(id: string): Observable<Performer> {
    return this.http.get<Performer>(`${this.apiUrl}/${id}`);
  }

  getAllArtists() {
    return this.http.get<Performer[]>(`${this.apiUrl}?roleId=${2}`);
  }

  add(Performer: Request<Performer>): Observable<Performer> {
    return this.http.post<Performer>(this.apiUrl, Performer);
  }

  set(id: string, Performer: Request<Performer>): Observable<Performer> {
    return this.http.put<Performer>(`${this.apiUrl}/${id}`, Performer);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getCount(): Observable<number> {
    return this.getAll().pipe(map((performers) => performers.length));
  }

  searchByName(name: string): Observable<Performer[]> {
    return this.http.get<Performer[]>(`${this.apiUrl}?name_like=${name}`);
  }
}
