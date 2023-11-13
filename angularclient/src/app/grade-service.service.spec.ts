import { TestBed } from '@angular/core/testing';

import { GradeService} from './grade-service.service';

describe('GradeServiceService', () => {
  let service: GradeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GradeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
