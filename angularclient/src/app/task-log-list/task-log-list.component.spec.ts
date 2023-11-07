import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskLogListComponent } from './task-log-list.component';

describe('TaskLogListComponent', () => {
  let component: TaskLogListComponent;
  let fixture: ComponentFixture<TaskLogListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TaskLogListComponent]
    });
    fixture = TestBed.createComponent(TaskLogListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
