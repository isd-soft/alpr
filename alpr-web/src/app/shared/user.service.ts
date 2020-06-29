import {User} from './user.model';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';


@Injectable()
export class UserService {
  private url = 'http://localhost:8080';
  private _currentToken;

  constructor(private httpClient: HttpClient) {
    this._currentToken = localStorage.getItem('token');
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
    // remove user from local storage to log user out
    localStorage.removeItem('token');
    this._currentToken = null;
  }

  public get currentToken() {
    return this._currentToken;
  }
}
