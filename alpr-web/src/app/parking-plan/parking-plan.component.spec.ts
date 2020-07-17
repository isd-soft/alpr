import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ParkingPlanComponent } from './parking-plan.component';

describe('ParkingPlanComponent', () => {
  let component: ParkingPlanComponent;
  let fixture: ComponentFixture<ParkingPlanComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ParkingPlanComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ParkingPlanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
