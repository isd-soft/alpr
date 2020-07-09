import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AnnouncementModel} from './announcement.model';

@Injectable()
export class AnnouncementService {
  private url = 'http://localhost:8080/announcements';
  constructor(private httpClient: HttpClient) {
  }

  public getAllAnnouncements(): Observable<AnnouncementModel[]> {
    return this.httpClient.get<AnnouncementModel[]>(this.url);
  }

  public addAnnouncement(announcement: AnnouncementModel): Observable<any> {
    return this.httpClient.post(this.url, announcement);
  }

  public removeAnnouncement(id: number): Observable<any> {
    return this.httpClient.delete(this.url + `/${id}`);
  }
}
