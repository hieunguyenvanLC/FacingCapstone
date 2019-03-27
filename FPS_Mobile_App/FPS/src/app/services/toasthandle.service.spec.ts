import { TestBed } from '@angular/core/testing';

import { ToastHandleService } from './toasthandle.service';

describe('ToastHandleService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ToastHandleService = TestBed.get(ToastHandleService);
    expect(service).toBeTruthy();
  });
});
