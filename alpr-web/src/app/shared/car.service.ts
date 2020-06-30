import {Car} from "./car.model";
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from "@angular/core";

@Injectable()
export class CarService {
  private url = 'http://localhost:8080/cars';

  constructor(private httpClient: HttpClient) {
  }

  registerCar(car: Car): Observable<any> {
    return this.httpClient.post(this.url + "/add", car,{
             headers: new HttpHeaders({
             'Content-Type':'application/json'
             })
             });
  }
}
