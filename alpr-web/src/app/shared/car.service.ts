import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {CarModel} from "./car.model";
import {CarListComponent} from "../car-list/car-list.component";
import {Observable} from "rxjs";
import {User} from "./user.model";


@Injectable()
export class CarService{
  constructor(private http: HttpClient) {}
  private url = 'http://localhost:8080/cars';

  getCars(): Observable<any[]>{
    return this.http.get<any[]>('http://localhost:8080/cars')};

  registerCar(car: CarModel): Observable<any> {
    return this.http.post(this.url + "/add", car);
  }

  //
  // getAll(): Observable<any[]> {
  //   return this.http.get<any[]>(this.url + '/cars');
  // }

  removeByLicensePlate(licensePlate: string): Observable<any> {
    return this.http.delete(this.url + '?licensePlate=' + licensePlate);
  }

  removeById(id: number): Observable<any> {
    return this.http.delete(this.url + '/' + id);
  }

  update(car: CarModel): Observable<any> {
    console.log(JSON.stringify(car));
    return this.http.put(this.url +
      '/' + car.id, car);
  }
}


