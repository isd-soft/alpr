import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AnnouncementModel} from './announcement.model';
import {CommentModel} from './comment.model';

@Injectable()
export class AnnouncementService {
  private url = 'https://vm-alpr-server.herokuapp.com/announcements';
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

  public getAllComments(id: number): Observable<CommentModel[]> {
    return this.httpClient.get<CommentModel[]>(this.url + '/comments/' + id);
  }

  public addComment(id: number, comment: CommentModel): Observable<any> {
    return this.httpClient.post(this.url + '/add-comment/' + id, comment);
  }
}
