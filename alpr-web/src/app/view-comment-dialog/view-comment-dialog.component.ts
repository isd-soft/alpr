import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {CommentModel} from "../shared/comment.model";

export interface ViewDialogData {
  comments: CommentModel[];
}

@Component({
  selector: 'app-view-comment-dialog',
  templateUrl: './view-comment-dialog.component.html',
  styleUrls: ['./view-comment-dialog.component.css']
})
export class ViewCommentDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<ViewCommentDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ViewDialogData
  ) { }

  ngOnInit(): void {
  }

  onCloseClick(): void {
    this.dialogRef.close();
  }

}
