import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyCarsComponent } from './company-cars.component';

describe('CompanyCarsComponent', () => {
  let component: CompanyCarsComponent;
  let fixture: ComponentFixture<CompanyCarsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CompanyCarsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompanyCarsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
