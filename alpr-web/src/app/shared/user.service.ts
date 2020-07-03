import {User} from './user.model';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';


@Injectable()
export class UserService {
  private url = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) {
  }

  registerUser(user: User): Observable<any> {
    return this.httpClient.post(this.url + '/register', user);
  }


  getByEmail(email: string): Observable<any> {
     return this.httpClient.get<any>(this.url+'/user?email=' + email);
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

  update(user: User, isPasswordChanged: boolean): Observable<any> {
    return this.httpClient.put(this.url +
      '/users/update?isPasswordChanged=' + isPasswordChanged, user);
  }
}
