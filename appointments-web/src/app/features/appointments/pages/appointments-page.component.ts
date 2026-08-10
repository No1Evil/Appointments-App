import { Component, OnInit } from '@angular/core';
import { NgIf } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AppointmentResponse, AppointmentStatus } from '../../../core/api';
import { AppointmentsFacade } from '../facades/appointments.facade';
import { PatientsFacade } from '../../patients/facades/patients.facade';
import { PractitionersFacade } from '../../practitioners/facades/practitioners.facade';
import { ServiceCategoriesFacade } from '../../service-categories/facades/service-categories.facade';
import { AppointmentFiltersComponent } from '../components/appointment-filters/appointment-filters.component';
import { AppointmentListComponent } from '../components/appointment-list/appointment-list.component';
import { CommentDialogComponent } from '../components/comment-dialog/comment-dialog.component';
import { ConfirmDialogComponent } from '../components/confirm-dialog/confirm-dialog.component';
import { RescheduleDialogComponent } from '../components/reschedule-dialog/reschedule-dialog.component';

@Component({
  selector: 'app-appointments-page',
  standalone: true,
  imports: [NgIf, AppointmentFiltersComponent, AppointmentListComponent],
  templateUrl: './appointments-page.component.html',
  styleUrl: './appointments-page.component.scss'
})
export class AppointmentsPageComponent implements OnInit {

  public readonly statuses: AppointmentStatus[] = [
    AppointmentStatus.Scheduled,
    AppointmentStatus.Cancelled,
    AppointmentStatus.Completed
  ];

  constructor(
    public readonly facade: AppointmentsFacade,
    public readonly serviceCategoriesFacade: ServiceCategoriesFacade,
    public readonly patientsFacade: PatientsFacade,
    public readonly practitionersFacade: PractitionersFacade,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  public ngOnInit(): void {
    this.serviceCategoriesFacade.loadServices();
    this.patientsFacade.loadPatients();
    this.practitionersFacade.loadPractitioners();
    this.facade.loadAppointments();
  }

  public onStatusChanged(status: AppointmentStatus): void {
    this.facade.updateFilters({ status });
  }

  public onServiceChanged(service: string): void {
    this.facade.updateFilters({ service });
  }

  public onPatientChanged(patientId: string): void {
    this.facade.updateFilters({ patientId });
  }

  public onPractitionerChanged(practitionerId: string): void {
    this.facade.updateFilters({ practitionerId });
  }

  public cancel(appointment: AppointmentResponse): void {
    this.dialog.open(ConfirmDialogComponent, {
      data: {
        title: 'Cancel appointment',
        message: `Cancel the appointment for ${appointment.patientName} on ${appointment.startTime ? new Date(appointment.startTime).toLocaleDateString('en-US') : ''}?`,
        confirmText: 'Cancel appointment'
      },
      width: '400px'
    }).afterClosed().subscribe(confirmed => {
      if (!confirmed) {
        return;
      }
      this.facade.cancelAppointment(appointment.id).subscribe({
        next: () => {
          this.snackBar.open('Appointment cancelled', 'OK', { duration: 3000 });
          this.facade.loadAppointments();
        },
        error: (err) => this.snackBar.open(this.facade.toErrorMessage(err, 'Failed to cancel appointment'), 'OK', { duration: 5000 })
      });
    });
  }

  public complete(appointment: AppointmentResponse): void {
    this.dialog.open(ConfirmDialogComponent, {
      data: {
        title: 'Complete appointment',
        message: `Complete the appointment for ${appointment.patientName} on ${appointment.startTime ? new Date(appointment.startTime).toLocaleDateString('en-US') : ''}?`,
        confirmText: 'Complete appointment'
      },
      width: '400px'
    }).afterClosed().subscribe(confirmed => {
      if (!confirmed) {
        return;
      }
      this.facade.completeAppointment(appointment.id).subscribe({
        next: () => {
          this.snackBar.open('Appointment completed', 'OK', { duration: 3000 });
          this.facade.loadAppointments();
        },
        error: (err) => this.snackBar.open(this.facade.toErrorMessage(err, 'Failed to complete appointment'), 'OK', { duration: 5000 })
      });
    });
  }

  public editComment(appointment: AppointmentResponse): void {
    this.dialog.open(CommentDialogComponent, {
      data: appointment,
      width: '480px'
    }).afterClosed().subscribe(saved => {
      if (saved) {
        this.snackBar.open('Comment updated', 'OK', { duration: 3000 });
        this.facade.loadAppointments();
      }
    });
  }

  public reschedule(appointment: AppointmentResponse): void {
    this.dialog.open(RescheduleDialogComponent, {
      data: appointment,
      width: '480px'
    }).afterClosed().subscribe(rescheduled => {
      if (rescheduled) {
        this.snackBar.open('Appointment rescheduled', 'OK', { duration: 3000 });
        this.facade.loadAppointments();
      }
    });
  }
}
