import { Component, Inject, OnInit } from '@angular/core';
import { DatePipe, NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import {
  AppointmentService,
  PatientResponse,
  PatientService,
  PractitionerResponse,
  PractitionerService,
  SlotResponse,
  SubmitAppointmentRequest
} from '../../api';

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
    MatSelectModule
  ],
  templateUrl: 'slot-dialog.component.html',
  styleUrl: 'slot-dialog.component.scss'
})
export class SlotDialogComponent implements OnInit {

  public patients: PatientResponse[] = [];
  public practitioners: PractitionerResponse[] = [];
  public patientId?: string;
  public practitionerId?: string;
  public comment?: string;
  public error?: string;
  public submitting = false;

  constructor(
    public dialogRef: MatDialogRef<SlotDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public slot: SlotResponse,
    private patientService: PatientService,
    private practitionerService: PractitionerService,
    private appointmentService: AppointmentService
  ) {}

  public ngOnInit(): void {
    this.patientService.getAllPatients().subscribe(patients => this.patients = patients);
    this.practitionerService.getPractitioners(this.slot.service.code).subscribe(practitioners => this.practitioners = practitioners);
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
