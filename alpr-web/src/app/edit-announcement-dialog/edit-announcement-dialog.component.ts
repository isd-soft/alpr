import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {AddAnnouncementDialogData} from '../add-announcement-dialog/add-announcement-dialog.component';

@Component({
  selector: 'app-edit-announcement-dialog',
  templateUrl: './edit-announcement-dialog.component.html',
  styleUrls: ['./edit-announcement-dialog.component.css']
})
export class EditAnnouncementDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<EditAnnouncementDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: AddAnnouncementDialogData) { }

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  areEmptyFields(): boolean {
    return this.data.announcement.title.length === 0 || this.data.announcement.priority.length === 0 || this.data.announcement.description.length === 0;
  }

}
