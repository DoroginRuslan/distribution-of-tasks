import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskTypeFormComponent } from './task-type-form.component';

describe('TaskTypeFormComponent', () => {
  let component: TaskTypeFormComponent;
  let fixture: ComponentFixture<TaskTypeFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TaskTypeFormComponent]
    });
    fixture = TestBed.createComponent(TaskTypeFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
