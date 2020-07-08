import { Component, OnInit } from '@angular/core';
import {AnnouncementService} from '../shared/announcement.service';
import {AnnouncementModel} from '../shared/announcement.model';

@Component({
  selector: 'app-announcement',
  templateUrl: './announcement.component.html',
  styleUrls: ['./announcement.component.css']
})
export class AnnouncementComponent implements OnInit {
  public announcements: AnnouncementModel[] = [];
  constructor(private announcementService: AnnouncementService) { }

  ngOnInit(): void {
    this.announcementService.getAllAnnouncements()
      .toPromise()
      .then(result => {
        this.announcements = result;
        console.log(this.announcements);
      })
      .catch(error => console.log(error));
  }

}
