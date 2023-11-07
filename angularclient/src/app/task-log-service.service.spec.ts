import { TestBed } from '@angular/core/testing';

import { TaskLogServiceService } from './task-log-service.service';

describe('TaskLogServiceService', () => {
  let service: TaskLogServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TaskLogServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
