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

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }

  getUserById(id: string): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}?id=${id}`);
  }

  getAllArtists() {
    return this.http.get<User[]>(`${this.apiUrl}?roleId=${2}`);
  }

  addUser(user: UserRequest): Observable<User> {
    return this.http.post<User>(this.apiUrl, user);
  }

  setUser(id: string, user: UserRequest): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${id}`, user);
  }

  deleteUser(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getUserCount(): Observable<number> {
    return this.getUsers().pipe(map((users) => users.length));
  }

  getUsersByRole(roleId: string): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}?roleId=${roleId}`);
  }

  getUserCountByRole(roleId: string): Observable<number> {
    return this.http
      .get<User[]>(`${this.apiUrl}?roleId=${roleId}`)
      .pipe(map((users) => users.length));
  }

  searchUsersByName(name: string): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}?name_like=${name}`);
  }
}
