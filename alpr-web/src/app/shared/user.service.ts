import {User} from './user.model';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {ConnectionURL} from "./url";


@Injectable()
export class UserService {
  private url = ConnectionURL.url;

  constructor(private httpClient: HttpClient) {
  }

  registerUser(user: User): Observable<any> {
    return this.httpClient.post<any>(this.url + '/register', user);
  }

  getByEmail(email: string): Observable<any> {
    return this.httpClient.get<any>(this.url + '/user?email=' + email);
  }

  getAll(): Observable<any[]> {
    return this.httpClient.get<any[]>(this.url + '/users');
  }

  add(user: User): Observable<any> {
    return this.httpClient.post(this.url + '/users/add', user);
  }

  removeByEmail(email: string): Observable<any> {
    return this.httpClient.delete(this.url + '/users?email=' + email);
  }

  getCarsByEmail(email: string): Observable<any> {
       return this.httpClient.get<any[]>(this.url + '/users/cars?email=' + email);
  }

  update(user: User, isPasswordChanged: boolean): Observable<any> {
    return this.httpClient.put(this.url +
      '/users/update?isPasswordChanged=' + isPasswordChanged, user);
  }

  hasCars(email: string): Observable<any> {
    return this.httpClient.get(this.url + '/users/hascars?email=' + email);
  }

  changePassword(oldPassword: string,
                 newPassword: string,
                 licensePlate: string) {
    return this.httpClient.put(this.url + '/users/password?oldPassword=' +
      oldPassword + '&newPassword=' + newPassword +
      '&licensePlate=' + licensePlate, null);
  }
}
