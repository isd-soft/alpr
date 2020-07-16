import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CarModel} from './car.model';
import {Observable} from 'rxjs';
import {ParkingPlanModel} from "./parking.plan.model";


@Injectable()
export class ParkingPlanService{
  constructor(private http: HttpClient) {}
  // private url = 'https://vm-alpr-server.herokuapp.com/cars';
  private url = 'http://localhost:8080/parkingplan/get';

  getParkingPlan(): Observable<any>{
    return this.http.get<any>(this.url);
  }

}
