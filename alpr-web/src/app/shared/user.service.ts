
import {User} from "./user.model";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";


@Injectable()
export class UserService {
  private url = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) {

  }

  registerUser(user: User): Observable<any> {
    return this.httpClient.post(this.url + "/register", user);
  }
}
