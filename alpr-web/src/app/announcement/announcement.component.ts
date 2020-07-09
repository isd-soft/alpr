import { Component, OnInit } from '@angular/core';
import {AnnouncementService} from '../shared/announcement.service';
import {AnnouncementModel} from '../shared/announcement.model';
import {ConfirmationDialogService} from '../shared/confirmation-dialog.service';
import {AuthenticationService} from '../auth/authentication.service';
import {Role} from '../auth/role';
import {MatDialog} from "@angular/material/dialog";
import {AddCommentDialogComponent} from "../add-comment-dialog/add-comment-dialog.component";
import {CommentModel} from "../shared/comment.model";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ViewCommentDialogComponent} from "../view-comment-dialog/view-comment-dialog.component";

@Component({
  selector: 'app-announcement',
  templateUrl: './announcement.component.html',
  styleUrls: ['./announcement.component.css']
})
export class AnnouncementComponent implements OnInit {
  public announcements: AnnouncementModel[] = [];
  public showForm = false;
  public isAdmin = true;
  public showAllComments = false;
  public showCommentForm = false;
  private userEmail = '';
  private commentDescription = '';
  announcement: AnnouncementModel = new AnnouncementModel();
  constructor(private announcementService: AnnouncementService,
              private confirmationDialogService: ConfirmationDialogService,
              private authenticationService: AuthenticationService,
              private dialog: MatDialog,
              private snackBar: MatSnackBar) {
    authenticationService.currentUser
      .subscribe(user => {
        this.isAdmin = user.role === Role.Admin;
        this.userEmail = user.email;
      });
  }

  ngOnInit(): void {
    this.updateList();
  }

  updateList(): void {
    this.announcementService.getAllAnnouncements()
      .toPromise()
      .then(result => {
        this.announcements = result;
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

  showComments(id: number) {
    this.announcementService.getAllComments(id)
      .toPromise()
      .then(data => {
        this.dialog.open(ViewCommentDialogComponent, {
          width: '50vw',
          data: {comments: data}
        });
      });
  }

  addComment(id: number) {
    this.commentDescription = '';
    const dialogRef = this.dialog.open(AddCommentDialogComponent, {
      width: '400px',
      data: {userEmail: this.userEmail, description: this.commentDescription}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        const comment = new CommentModel(this.userEmail, result.slice(1));
        this.announcementService.addComment(id, comment)
          .toPromise()
          .then(_ => {
            this.snackBar.open('Comment added!', 'OK', {duration: 2000});
          });
      }
    });
  }

}
