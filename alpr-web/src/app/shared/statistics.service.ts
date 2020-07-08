import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class StatisticsService {
  private url = 'http://localhost:8080/statistics';

  constructor(private httpClient: HttpClient) {
  }

  public getTotalAllowedRejectedCars(): Observable<{ allowedCars: number, rejectedCars: number }> {
    return this.httpClient.get<{ allowedCars: number, rejectedCars: number }>(this.url + '/all-statuses');
  }

  public getCarsStatistics(): Observable<any> {
    return this.httpClient.get(this.url + '/cars');
  }

  public getUsersStatistics(): Observable<any> {
    return this.httpClient.get(this.url + '/users');
  }

  public getAllowedRejectedCarsLastWeek(): Observable<{id: number, scanDate: string, status: string, licensePlate: string, allowed: boolean}[]> {
    return this.httpClient.get<any[]>(this.url + '/all-last-week');
  }

  public getCarsPerCompany(): Observable<{name: string, cars: number}[]> {
    return this.httpClient.get<any[]>(this.url + '/cars-per-company');
  }
}
