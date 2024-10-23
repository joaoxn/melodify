import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../interfaces/user';
import { map, Observable } from 'rxjs';

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
    return this.http.get<User[]>(`${this.apiUrl}?papelId=${2}`);
  }

  getTeacherById(teacherId: string) {
    return this.getUserById(teacherId);
  }

  addUser(user: User): Observable<User> {
    return this.http.post<User>(this.apiUrl, user);
  }

  setUser(user: User): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${user.id}`, user);
  }

  deleteUser(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getUserCount(): Observable<number> {
    return this.getUsers().pipe(map((users) => users.length));
  }

  getUsersByRole(papelId: string): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}?papelId=${papelId}`);
  }

  getUserCountByRole(roleId: string): Observable<number> {
    return this.http
      .get<User[]>(`${this.apiUrl}?papelId=${roleId}`)
      .pipe(map((users) => users.length));
  }

  searchUsersByName(name: string): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}?name_like=${name}`);
  }
}
