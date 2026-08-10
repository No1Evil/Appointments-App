import { Component, Inject, OnInit } from '@angular/core';
import { DatePipe, NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSelectModule } from '@angular/material/select';
import { AppointmentService, SlotResponse, SubmitAppointmentRequest } from '../../../../core/api';
import { PatientsFacade } from '../../../patients/facades/patients.facade';
import { PractitionersFacade } from '../../../practitioners/facades/practitioners.facade';

@Component({
  selector: 'slot-dialog',
  standalone: true,
  imports: [
    NgFor,
    NgIf,
    DatePipe,
    FormsModule,
    MatButtonModule,
    MatDialogModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatProgressBarModule,
    MatSelectModule
  ],
  templateUrl: 'slot-dialog.component.html',
  styleUrl: 'slot-dialog.component.scss'
})
export class SlotDialogComponent implements OnInit {

  public patientId?: string;
  public practitionerId?: string;
  public comment?: string;
  public error?: string;
  public submitting = false;

  constructor(
    public dialogRef: MatDialogRef<SlotDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public slot: SlotResponse,
    public patientsFacade: PatientsFacade,
    public practitionersFacade: PractitionersFacade,
    private appointmentService: AppointmentService
  ) {}

  public ngOnInit(): void {
    this.patientsFacade.loadPatients();
    this.practitionersFacade.selectService(this.slot.service.code);
  }

  public submit(): void {
    if (!this.patientId || !this.practitionerId) {
      return;
    }
    this.submitting = true;
    this.error = undefined;

    const request: SubmitAppointmentRequest = {
      slotId: this.slot.id,
      patientId: this.patientId,
      practitionerId: this.practitionerId,
      comment: this.comment ?? ''
    };

    this.appointmentService.submitAppointment(request).subscribe({
      next: () => this.dialogRef.close(true),
      error: (err) => {
        this.submitting = false;
        this.error = err?.error?.message ?? 'Failed to book appointment';
      }
    });
  }

}
