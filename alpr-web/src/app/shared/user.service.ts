import {User} from './user.model';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';


@Injectable()
export class UserService {
  private url = 'http://localhost:8080';
  currentToken(): string{
    return localStorage.getItem('token');
  }

  constructor(private httpClient: HttpClient) {
  }

  registerUser(user: User): Observable<any> {
    return this.httpClient.post(this.url + '/register', user);
  }

  login(email: string, password: string): Observable<any> {
    return this.httpClient.post(this.url + '/authenticate',
      {email: email, password: password});
  }

  hello(): Observable<string> {
    console.log(localStorage.getItem('token'));
    return this.httpClient.get<string>(this.url + '/hello', {headers: new HttpHeaders().set('Authorization', localStorage.getItem('token'))});
  }

  getAll(): Observable<any[]> {
    return this.httpClient.get<any[]>(this.url + '/users');
  }

  logout() {
    localStorage.removeItem('token');
  }

  add(user: User): Observable<any> {
    return this.httpClient.post(this.url + '/users/add', user);
  }

  removeByEmail(email: string): Observable<any> {
    return this.httpClient.delete(this.url + '/users?email=' + email);
  }

  update(user: User, isPasswordChanged: boolean): Observable<any> {
    return this.httpClient.put(this.url +
      '/users/update?isPasswordChanged=' + isPasswordChanged, user);
  }
}
