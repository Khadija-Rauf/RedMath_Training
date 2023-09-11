import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewBalanceComponent } from './view-balance.component';

describe('ViewBalanceComponent', () => {
  let component: ViewBalanceComponent;
  let fixture: ComponentFixture<ViewBalanceComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewBalanceComponent]
    });
    fixture = TestBed.createComponent(ViewBalanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
