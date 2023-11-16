import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskTypeListComponent } from './task-type-list.component';

describe('TaskTypeListComponent', () => {
  let component: TaskTypeListComponent;
  let fixture: ComponentFixture<TaskTypeListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TaskTypeListComponent]
    });
    fixture = TestBed.createComponent(TaskTypeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
