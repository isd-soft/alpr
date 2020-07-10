import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {AnnouncementModel} from '../shared/announcement.model';

export interface AddAnnouncementDialogData {
  announcement: AnnouncementModel;
}

@Component({
  selector: 'app-add-announcement-dialog',
  templateUrl: './add-announcement-dialog.component.html',
  styleUrls: ['./add-announcement-dialog.component.css']
})
export class AddAnnouncementDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<AddAnnouncementDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: AddAnnouncementDialogData
  ) { }

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  areEmptyFields(): boolean {
    return this.data.announcement.title.length === 0 || this.data.announcement.priority.length === 0 || this.data.announcement.description.length === 0;
  }

}
