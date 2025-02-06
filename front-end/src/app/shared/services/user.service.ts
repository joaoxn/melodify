import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RawUser, User } from '../interfaces/user';
import { catchError, map, Observable, switchMap, tap, throwError } from 'rxjs';
import { Request } from '../interfaces/generics';
import { Role } from '../interfaces/role';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly url = 'http://localhost:3000/user';
  private readonly roleUrl = 'http://localhost:3000/role';

  public currentUser?: User;
  lastSuccessVerifyTimestamp = 0;

  constructor(private http: HttpClient, private router: Router) { }

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
      const response: any = {
        ...user,
        roleId: user.role.id
      };
      delete response.role;
      return response;
    }
    return user;
  }

  getFromRaw(rawUser: RawUser): Observable<User> {
    return this.getRole(rawUser.roleId).pipe(
      map((role) => {
        const response: any = {
          ...rawUser,
          role: role
        };
        delete response.roleId;
        return response;
      })
    )
  }

  getAll(): Observable<RawUser[]> {
    return this.http.get<RawUser[]>(this.url);
  }

  get(id: string): Observable<RawUser> {
    return this.http.get<RawUser>(`${this.url}/${id}`);
  }

  getByEmail(email: string): Observable<User> {
    return this.http.get<User[]>(`${this.url}?email=${email}`).pipe(
      tap(() => console.log(`GET API at: ${this.url}?email=${email}`)),
      map(users => {
        if (users.length == 0)
          throw new Error("NoSuchEntityError: No user found with such email");
        if (users.length > 1)
          console.warn(`Multiple users found with the same email (${email})`, users);
        return users[0];
      })
    );
  }

  getAllArtists() {
    return this.http.get<RawUser[]>(`${this.url}?roleId=${2}`);
  }

  add(user: Request<RawUser>): Observable<RawUser> {
    return this.http.post<RawUser>(this.url, user);
  }

  set(id: string, user: Request<RawUser>): Observable<RawUser> {
    return this.http.put<RawUser>(`${this.url}/${id}`, user);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }

  getCount(): Observable<number> {
    return this.getAll().pipe(map((users) => users.length));
  }

  getByRole(roleId: string): Observable<RawUser[]> {
    return this.http.get<RawUser[]>(`${this.url}?roleId=${roleId}`);
  }

  getCountByRole(roleId: string): Observable<number> {
    return this.http
      .get<RawUser[]>(`${this.url}?roleId=${roleId}`)
      .pipe(map((users) => users.length));
  }

  searchByName(name: string): Observable<RawUser[]> {
    return this.http.get<RawUser[]>(`${this.url}?name_like=${name}`);
  }

  getRole(id: string): Observable<Role> {
    return this.http.get<Role>(`${this.roleUrl}/${id}`);
  }
  
  register(user: Request<RawUser>): Observable<string> {
    return this.getByEmail(user.email).pipe(
      switchMap(() =>
        throwError(() => new Error("DuplicateEntityError: Email already registered"))
      ),
      catchError((error: Error) => {
        if (error.message.slice(0, 18) != "NoSuchEntityError:") throw error;

        return this.add(user).pipe(
          switchMap(user => {
            console.info("Registered and logged in successfully");
            return this.logSession(user.id);
          })
        );
      })
    )
  }

  login(email: string, password: string): Observable<string> {
    return this.getByEmail(email).pipe(
      map(user => {
        if (!user || user.password != password) {
          throw new Error("UnauthorizedError: Login unauthorized");
        }
        this.currentUser = user;
        return this.logSession(user.id);
      }));
  }

  logout() {
    this.currentUser = undefined;

    localStorage.removeItem('authToken');
    console.info("Logged out successfully");
    if (this.router.url != '/register')
      this.router.navigate(['/login']);
  }

  logSession(id: string): string {
    localStorage.setItem('authToken', JSON.stringify({
      userId: id,
      auth: Date.now() / 1000
    }));

    console.info(`Logged-in with id ${id} successfully`);
    return id;
  }

  validateAuth(): Observable<User> | false {
    if (typeof window === 'undefined') return false;

    function directErrorReturnCall(): false {
      localStorage.removeItem('authToken');
      return false;
    }
    this.currentUser = undefined;

    const authToken = localStorage.getItem('authToken');
    if (!authToken) return directErrorReturnCall();

    const auth = JSON.parse(authToken).auth;
    if (!auth) return directErrorReturnCall();

    const userId: string = JSON.parse(authToken).userId;
    if (!userId) return directErrorReturnCall();

    return this.get(userId).pipe(
      switchMap(rawUser => this.getFromRaw(rawUser)),
      map(user => {
        // If auth older than 86400 seconds (24 hours) will throw
        if (Date.now() / 1000 - auth < 86400) {
          this.currentUser = user;
          return user;
        }

        localStorage.removeItem("authToken");
        this.logout();
        throw new Error("AuthExpiredError: Authentication expired. Auth: " + auth);
      })
    );
  }
}
