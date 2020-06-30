import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {CarModel} from "./car.model";
import {CarListComponent} from "../car-list/car-list.component";
import {Observable} from "rxjs";


@Injectable()
export class CarService{
  constructor(private http: HttpClient) {}
  private url = 'http://localhost:8080/cars';

  getCars(): Observable<any[]>{
    return this.http.get<any[]>('http://localhost:8080/cars')};

  registerCar(car: CarModel): Observable<any> {
    return this.http.post(this.url + "/add", car);
  }
}


