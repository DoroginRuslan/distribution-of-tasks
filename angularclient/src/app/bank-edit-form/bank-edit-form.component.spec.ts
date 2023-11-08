import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BankEditFormComponent } from './bank-edit-form.component';

describe('BankEditFormComponent', () => {
  let component: BankEditFormComponent;
  let fixture: ComponentFixture<BankEditFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BankEditFormComponent]
    });
    fixture = TestBed.createComponent(BankEditFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
