import { TestBed } from '@angular/core/testing';

import { ApihttpService } from './apihttp.service';

describe('ApihttpService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ApihttpService = TestBed.get(ApihttpService);
    expect(service).toBeTruthy();
  });
});
