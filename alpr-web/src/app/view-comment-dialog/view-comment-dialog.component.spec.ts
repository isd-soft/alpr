import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewCommentDialogComponent } from './view-comment-dialog.component';

describe('ViewCommentDialogComponent', () => {
  let component: ViewCommentDialogComponent;
  let fixture: ComponentFixture<ViewCommentDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewCommentDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewCommentDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
