import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../interfaces/user';
import { map, Observable } from 'rxjs';
import { UserRequest } from '../interfaces/user-request';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly apiUrl = 'http://localhost:3000/user';

  constructor(private http: HttpClient) {}

  getAll(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }

  get(id: string): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}?id=${id}`);
  }

  getAllArtists() {
    return this.http.get<User[]>(`${this.apiUrl}?roleId=${2}`);
  }

  add(user: UserRequest): Observable<User> {
    return this.http.post<User>(this.apiUrl, user);
  }

  set(id: string, user: UserRequest): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${id}`, user);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getCount(): Observable<number> {
    return this.getAll().pipe(map((users) => users.length));
  }

  getByRole(roleId: string): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}?roleId=${roleId}`);
  }

  getCountByRole(roleId: string): Observable<number> {
    return this.http
      .get<User[]>(`${this.apiUrl}?roleId=${roleId}`)
      .pipe(map((users) => users.length));
  }

  searchByName(name: string): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}?name_like=${name}`);
  }
}
