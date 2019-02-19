import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistorPage } from './registor.page';

describe('RegistorPage', () => {
  let component: RegistorPage;
  let fixture: ComponentFixture<RegistorPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegistorPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistorPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
