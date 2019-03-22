import { TestBed } from '@angular/core/testing';

import { ToasthandleService } from './toasthandle.service';

describe('ToasthandleService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ToasthandleService = TestBed.get(ToasthandleService);
    expect(service).toBeTruthy();
  });
});
