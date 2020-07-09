import { Component, OnInit } from '@angular/core';
import {AnnouncementService} from '../shared/announcement.service';
import {AnnouncementModel} from '../shared/announcement.model';
import {MatDialog} from '@angular/material/dialog';
import {ConfirmationDialogService} from '../shared/confirmation-dialog.service';

@Component({
  selector: 'app-announcement',
  templateUrl: './announcement.component.html',
  styleUrls: ['./announcement.component.css']
})
export class AnnouncementComponent implements OnInit {
  public announcements: AnnouncementModel[] = [];
  public showForm = false;
  announcement: AnnouncementModel = new AnnouncementModel();
  constructor(private announcementService: AnnouncementService,
              private confirmationDialogService: ConfirmationDialogService) { }

  ngOnInit(): void {
    this.updateList();
  }

  updateList(): void {
    this.announcementService.getAllAnnouncements()
      .toPromise()
      .then(result => {
        this.announcements = result.reverse();
        console.log(this.announcements);
      })
      .catch(error => console.log(error));
  }

  removeAnnouncement(id: number) {
    this.confirmationDialogService.confirm('Please confirm...', 'Do you really want to remove this announcement?')
      .then(_ => {
        this.announcementService.removeAnnouncement(id)
          .toPromise()
          .then(_ => this.updateList());
      })
      .catch(() => console.log('Canceled'));
  }

  showAddForm() {
    this.announcement = new AnnouncementModel();
    this.showForm = true;
  }

  closeForm() {
    this.showForm = false;
  }

  addAnnouncement() {
    console.log(this.announcement);
    this.announcementService.addAnnouncement(this.announcement)
      .toPromise()
      .then(_ => {
        this.updateList();
        this.showForm = false;
      })
      .catch(error => console.log(error));
  }

}
