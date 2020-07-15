import { Component, OnInit } from '@angular/core';
import {AnnouncementService} from '../shared/announcement.service';
import {AnnouncementModel} from '../shared/announcement.model';
import {ConfirmationDialogService} from '../shared/confirmation-dialog.service';
import {AuthenticationService} from '../auth/authentication.service';
import {Role} from '../auth/role';
import {MatDialog} from '@angular/material/dialog';
import {AddCommentDialogComponent} from '../add-comment-dialog/add-comment-dialog.component';
import {CommentModel} from '../shared/comment.model';
import {MatSnackBar} from '@angular/material/snack-bar';
import {ViewCommentDialogComponent} from '../view-comment-dialog/view-comment-dialog.component';
import {AddAnnouncementDialogComponent} from '../add-announcement-dialog/add-announcement-dialog.component';
import {EditAnnouncementDialogComponent} from '../edit-announcement-dialog/edit-announcement-dialog.component';

@Component({
  selector: 'app-announcement',
  templateUrl: './announcement.component.html',
  styleUrls: ['./announcement.component.css']
})
export class AnnouncementComponent implements OnInit {
  public announcements: AnnouncementModel[] = [];
  public showForm = false;
  public isAdmin = true;
  private userEmail = '';
  private commentDescription = '';
  public nrOfComments = {};
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
        this.announcements.forEach(ann => {
          this.announcementService.getAllComments(ann.id)
            .toPromise()
            .then(comments => this.nrOfComments[ann.id] = comments.length);
        });
      });
  }

  removeAnnouncement(id: number) {
    this.confirmationDialogService.confirm('Please confirm...', 'Do you really want to remove this announcement?')
      .then(_ => {
        this.announcementService.removeAnnouncement(id)
          .toPromise()
          .then(_ => this.updateList());
      });
  }

  showAddForm() {
    this.announcement = new AnnouncementModel();
    this.showForm = true;
    const dialogRef = this.dialog.open(AddAnnouncementDialogComponent, {
      data: {announcement: this.announcement}
    });

    dialogRef.afterClosed().subscribe(response => {
      if (response !== undefined) {
        this.announcement = response;
        this.addAnnouncement();
      }
      else {
        this.showForm = false;
      }
    });
  }

  addAnnouncement() {
    this.announcementService.addAnnouncement(this.announcement)
      .toPromise()
      .then(_ => {
        this.updateList();
        this.showForm = false;
      })
      .catch(error => this.snackBar.open('Announcement was not added', 'OK', {duration: 3000}));
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
            this.updateList();
          });
      }
    });
  }

  editAnnouncement(ann: AnnouncementModel) {
    this.showForm = true;
    const dialogRef = this.dialog.open(EditAnnouncementDialogComponent, {
      data: {announcement: ann}
    });

    dialogRef.afterClosed().subscribe(response => {
      if (response !== undefined) {
        this.announcementService.updateAnnouncement(response)
          .toPromise()
          .then(_ => {
            this.updateList();
            this.showForm = false;
          });
      }
      else {
        this.updateList();
        this.showForm = false;
      }
    });
  }

  getNrOfComments(announcementId: number) {
    return this.announcementService.getAllComments(announcementId)
      .toPromise()
      .then(result => result);
  }

}
