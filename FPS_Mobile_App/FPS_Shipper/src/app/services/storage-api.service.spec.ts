import { TestBed } from '@angular/core/testing';

import { StorageApiService } from './storage-api.service';

describe('StorageApiService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: StorageApiService = TestBed.get(StorageApiService);
    expect(service).toBeTruthy();
  });
});
