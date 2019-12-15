import { TestBed } from '@angular/core/testing';

import { ActionSheetHandleService } from './action-sheet-handle.service';

describe('ActionSheetHandleService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ActionSheetHandleService = TestBed.get(ActionSheetHandleService);
    expect(service).toBeTruthy();
  });
});
