import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {User} from '../shared/user.model';

@Injectable({providedIn: 'root'})
export class AuthenticationService {
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;
  private url = 'https://vm-alpr-server.herokuapp.com';

  constructor(private http: HttpClient) {
    this.currentUserSubject =
      new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  updateUser(user: User){
    user.token = this.currentUserSubject.value.token;
    localStorage.setItem('currentUser', JSON.stringify(user))
    this.currentUserSubject.next(user);
  }

  login(email: string, password: string) {
    return this.http.post<any>(this.url + `/authenticate`,
      {email, password})
      .pipe(map(jwtAuthResponse => {
        if (jwtAuthResponse.user && jwtAuthResponse.token) {
          const user: User = jwtAuthResponse.user;
          user.token = jwtAuthResponse.token;
          localStorage.setItem('currentUser', JSON.stringify(user));
          this.currentUserSubject.next(user);
        }

        return jwtAuthResponse;
      }));
  }

  logout() {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }
}
