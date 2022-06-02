import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FollowingRequestComponent } from './following-request.component';

describe('FollowingRequestComponent', () => {
  let component: FollowingRequestComponent;
  let fixture: ComponentFixture<FollowingRequestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FollowingRequestComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FollowingRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
