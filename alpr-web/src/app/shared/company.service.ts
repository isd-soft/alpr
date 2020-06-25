import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {CompanyModel} from "./company.model";
import {Observable} from "rxjs";


@Injectable()
export class CompanyService {
  private url = "http://localhost:8080/companies";

  constructor(private httpClient: HttpClient) {
  }

  public getAll(): Observable<any[]>{
    return this.httpClient.get<any[]>(this.url);
  }
}
