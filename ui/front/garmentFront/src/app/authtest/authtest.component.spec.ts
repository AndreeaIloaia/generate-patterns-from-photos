import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthtestComponent } from './authtest.component';

describe('AuthtestComponent', () => {
  let component: AuthtestComponent;
  let fixture: ComponentFixture<AuthtestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuthtestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthtestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
