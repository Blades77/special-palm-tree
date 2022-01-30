import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TooltipCustomClassComponent } from './tooltip-custom-class.component';

describe('TooltipCustomClassComponent', () => {
  let component: TooltipCustomClassComponent;
  let fixture: ComponentFixture<TooltipCustomClassComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TooltipCustomClassComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TooltipCustomClassComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
