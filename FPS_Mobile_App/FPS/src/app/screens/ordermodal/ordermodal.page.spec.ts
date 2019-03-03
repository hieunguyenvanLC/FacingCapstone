import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrdermodalPage } from './ordermodal.page';

describe('OrdermodalPage', () => {
  let component: OrdermodalPage;
  let fixture: ComponentFixture<OrdermodalPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrdermodalPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrdermodalPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
