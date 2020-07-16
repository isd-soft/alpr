import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CompanyModel} from './company.model';
import {Observable} from 'rxjs';
import {ConnectionURL} from "./url";


@Injectable()
export class CompanyService {
  private url = ConnectionURL.local + 'companies';

  constructor(private httpClient: HttpClient) {
  }

  public getAll(): Observable<any[]> {
    return this.httpClient.get<any[]>(this.url);
  }

  add(companyModel: CompanyModel): Observable<any> {
    return this.httpClient.post(this.url + '/add', companyModel);
  }

  removeById(id: number): Observable<any> {
    return this.httpClient.delete(this.url + '/' + id);
  }

  update(companyModel: CompanyModel): Observable<any> {
    return this.httpClient.put(this.url + '/' + companyModel.id, companyModel);
  }
}
