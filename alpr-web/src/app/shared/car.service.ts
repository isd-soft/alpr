import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {CarModel} from "./car.model";
import {CarListComponent} from "../car-list/car-list.component";
import {Observable} from "rxjs";


@Injectable()
export class CarService{
  private url = 'http://localhost:8080/cars';

  // @ts-ignore
  constructor(private http:HttpClient) {}

  getCars(): Observable<any[]>{
    return this.http.get<any[]>('http://localhost:8080/cars');

  registerCar(car: Car): Observable<any> {
    return this.http.post(this.url + "/add", car,{
             headers: new HttpHeaders({
             'Content-Type':'application/json'
             })
             });

    // return this.http.get('http://localhost:8080/cars')
    //   .subscribe((response:Response)=>response.json())
  }


