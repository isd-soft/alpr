import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OnUsersActionsComponent } from './on-users-actions.component';

describe('OnUsersActionsComponent', () => {
  let component: OnUsersActionsComponent;
  let fixture: ComponentFixture<OnUsersActionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OnUsersActionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OnUsersActionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
