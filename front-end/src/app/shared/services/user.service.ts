import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RawUser, User } from '../interfaces/user';
import { map, Observable, switchMap } from 'rxjs';
import { Request } from '../interfaces/generics';
import { Role } from '../interfaces/role';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly apiUrl = 'http://localhost:3000/user';
  private readonly roleUrl = 'http://localhost:3000/role';

  public currentUser?: User;

  constructor(private http: HttpClient) { }

  changeCurrent(user: Request<RawUser> | Request<User>): Observable<User> {
    const rawUser = this.convertRequestToRaw(user);
    let observable$ = this.currentUser ? this.set(this.currentUser.id, rawUser) : this.add(rawUser);
    return observable$.pipe(
      switchMap(rawUser => this.getFromRaw(rawUser)),
      map(user => this.currentUser = user)
    );
  }

  convertRequestToRaw(user: Request<User> | Request<RawUser>): Request<RawUser> {
    if ('role' in user) {
      return {
        ...user,
        roleId: user.role.id
      };
    }
    return user;
  }

  getAll(): Observable<RawUser[]> {
    return this.http.get<RawUser[]>(this.apiUrl);
  }

  get(id: string): Observable<RawUser> {
    return this.http.get<RawUser>(`${this.apiUrl}/${id}`);
  }

  getFromRaw(rawUser: RawUser): Observable<User> {
    return this.getRole(rawUser.roleId).pipe(
      map((role) => {
        return {
          ...rawUser,
          role: role
        };
      })
    )
  }

  getAllArtists() {
    return this.http.get<RawUser[]>(`${this.apiUrl}?roleId=${2}`);
  }

  add(user: Request<RawUser>): Observable<RawUser> {
    return this.http.post<RawUser>(this.apiUrl, user);
  }

  set(id: string, user: Request<RawUser>): Observable<RawUser> {
    return this.http.put<RawUser>(`${this.apiUrl}/${id}`, user);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getCount(): Observable<number> {
    return this.getAll().pipe(map((users) => users.length));
  }

  getByRole(roleId: string): Observable<RawUser[]> {
    return this.http.get<RawUser[]>(`${this.apiUrl}?roleId=${roleId}`);
  }

  getCountByRole(roleId: string): Observable<number> {
    return this.http
      .get<RawUser[]>(`${this.apiUrl}?roleId=${roleId}`)
      .pipe(map((users) => users.length));
  }

  searchByName(name: string): Observable<RawUser[]> {
    return this.http.get<RawUser[]>(`${this.apiUrl}?name_like=${name}`);
  }

  getRole(id: string): Observable<Role> {
    return this.http.get<Role>(`${this.roleUrl}/${id}`);
  }
}
