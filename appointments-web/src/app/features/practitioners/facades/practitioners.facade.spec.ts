import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { PractitionerService } from '../../../core/api';
import { PractitionersFacade } from './practitioners.facade';

describe('PractitionersFacade', () => {
  let facade: PractitionersFacade;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        PractitionersFacade,
        { provide: PractitionerService, useValue: { getPractitioners: jasmine.createSpy('getPractitioners').and.returnValue(of([])) } }
      ]
    });
    facade = TestBed.inject(PractitionersFacade);
  });

  it('should create an instance', () => {
    expect(facade).toBeTruthy();
  });
});
