import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskTypeEditFormComponent } from './task-type-edit-form.component';

describe('TaskTypeEditFormComponent', () => {
  let component: TaskTypeEditFormComponent;
  let fixture: ComponentFixture<TaskTypeEditFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TaskTypeEditFormComponent]
    });
    fixture = TestBed.createComponent(TaskTypeEditFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
