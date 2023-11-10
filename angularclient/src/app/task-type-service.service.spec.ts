import { TestBed } from '@angular/core/testing';

import { TaskTypeServiceService } from './task-type-service.service';

describe('TaskTypeServiceService', () => {
  let service: TaskTypeServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TaskTypeServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
