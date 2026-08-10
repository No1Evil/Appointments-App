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
import {
  AppointmentResponse,
  RescheduleAppointmentRequest,
  SlotResponse
} from '../../../../core/api';
import { AppointmentsFacade } from '../../facades/appointments.facade';
import { SlotsFacade } from '../../../slots/facades/slots.facade';

@Component({
  selector: 'reschedule-dialog',
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
  templateUrl: 'reschedule-dialog.component.html',
  styleUrl: 'reschedule-dialog.component.scss'
})
export class RescheduleDialogComponent implements OnInit {

  public slots: SlotResponse[] = [];
  public selectedSlotId?: number;
  public comment = '';
  public loading = false;
  public submitting = false;
  public error?: string;

  constructor(
    public dialogRef: MatDialogRef<RescheduleDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public appointment: AppointmentResponse,
    private slotsFacade: SlotsFacade,
    private appointmentsFacade: AppointmentsFacade
  ) {}

  public ngOnInit(): void {
    this.loading = true;
    this.slotsFacade.loadFreeSlots().subscribe({
      next: (slots) => {
        this.slots = slots.filter(slot => slot.service.name === this.appointment.serviceName);
        this.loading = false;
      },
      error: () => {
        this.loading = false;
        this.error = 'Failed to load available slots';
      }
    });
  }

  public submit(): void {
    if (!this.selectedSlotId) {
      return;
    }
    this.submitting = true;
    this.error = undefined;

    const request: RescheduleAppointmentRequest = {
      appointmentId: this.appointment.id,
      slotId: this.selectedSlotId,
      comment: this.comment
    };

    this.appointmentsFacade.rescheduleAppointment(request).subscribe({
      next: () => this.dialogRef.close(true),
      error: (err) => {
        this.submitting = false;
        this.error = this.appointmentsFacade.toErrorMessage(err, 'Failed to reschedule appointment');
      }
    });
  }

}
