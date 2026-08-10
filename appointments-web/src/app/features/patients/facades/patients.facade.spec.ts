import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { PatientService } from '../../../core/api';
import { PatientsFacade } from './patients.facade';

describe('PatientsFacade', () => {
  let facade: PatientsFacade;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        PatientsFacade,
        { provide: PatientService, useValue: { getAllPatients: jasmine.createSpy('getAllPatients').and.returnValue(of([])) } }
      ]
    });
    facade = TestBed.inject(PatientsFacade);
  });

  it('should create an instance', () => {
    expect(facade).toBeTruthy();
  });
});
