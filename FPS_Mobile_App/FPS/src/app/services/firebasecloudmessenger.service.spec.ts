import { TestBed } from '@angular/core/testing';

import { FirebasecloudmessengerService } from './firebasecloudmessenger.service';

describe('FirebasecloudmessengerService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FirebasecloudmessengerService = TestBed.get(FirebasecloudmessengerService);
    expect(service).toBeTruthy();
  });
});
