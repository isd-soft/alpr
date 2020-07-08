import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class StatisticsService {
  private url = 'http://localhost:8080/statistics';
  private metricsUrl ="http://localhost:8080/actuator/metrics"

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

  public getInfo(): Observable<any> {
    return  this.httpClient.get( 'http://localhost:8080/actuator/info');
  }
  public getHealth(): Observable<any> {
    return  this.httpClient.get( 'http://localhost:8080/actuator/health');
  }
  public getCpuCount(): Observable<any> {
    return  this.httpClient.get( this.metricsUrl +'/system.cpu.count');
  }
  public getUptime(): Observable<any> {
    return  this.httpClient.get( this.metricsUrl +'/process.uptime');
  }

  public getHttpRequest(): Observable<any> {
    return  this.httpClient.get( this.metricsUrl +'/http.server.requests');
  }

  public getTotalMemory(): Observable<any> {
    return  this.httpClient.get( this.metricsUrl +'/jvm.memory.max');
  }

  public getUsedMemory(): Observable<any> {
    return  this.httpClient.get( this.metricsUrl +'/jvm.memory.used');
  }

}
