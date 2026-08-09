import { Component, OnInit } from '@angular/core';
import { DatePipe, NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import {
  AppointmentResponse,
  AppointmentService,
  AppointmentStatus,
  PatientResponse,
  PatientService,
  PractitionerResponse,
  PractitionerService,
  ServiceCategory,
  ServiceCategoryService
} from '../../api';
import { CommentDialogComponent } from '../dialog/comment-dialog.component';
import { ConfirmDialogComponent } from '../dialog/confirm-dialog.component';
import { RescheduleDialogComponent } from '../dialog/reschedule-dialog.component';

@Component({
  selector: 'appointment',
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
    MatProgressBarModule,
    MatSelectModule,
    MatSnackBarModule
  ],
  templateUrl: 'appointment.component.html',
  styleUrl: 'appointment.component.scss'
})
export class AppointmentComponent implements OnInit {

  public appointments: AppointmentResponse[] = [];
  public statuses: AppointmentStatus[] = [
    AppointmentStatus.Scheduled,
    AppointmentStatus.Cancelled,
    AppointmentStatus.Completed
  ];
  public services: ServiceCategory[] = [];
  public patients: PatientResponse[] = [];
  public practitioners: PractitionerResponse[] = [];

  public selectedStatus?: AppointmentStatus;
  public selectedService?: string;
  public selectedPatientId?: string;
  public selectedPractitionerId?: string;

  public loading = false;

  constructor(
    private appointmentService: AppointmentService,
    private serviceCategoryService: ServiceCategoryService,
    private patientService: PatientService,
    private practitionerService: PractitionerService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  public ngOnInit(): void {
    this.serviceCategoryService.getAllServices().subscribe(services => this.services = services);
    this.patientService.getAllPatients().subscribe(patients => this.patients = patients);
    this.practitionerService.getPractitioners().subscribe(practitioners => this.practitioners = practitioners);
    this.loadAppointments();
  }

  public loadAppointments(): void {
    this.loading = true;
    this.appointmentService.getAppointments(
      this.selectedPractitionerId,
      this.selectedStatus,
      this.selectedService,
      this.selectedPatientId
    ).subscribe({
      next: (appointments) => {
        this.appointments = appointments;
        this.loading = false;
      },
      error: (err) => {
        this.loading = false;
        this.snackBar.open(this.errorMessage(err, 'Failed to load appointments'), 'OK', { duration: 5000 });
      }
    });
  }

  public cancel(appointment: AppointmentResponse): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        title: 'Cancel appointment',
        message: `Cancel the appointment for ${appointment.patientName} on ${appointment.startTime ? new Date(appointment.startTime).toLocaleDateString('en-US') : ''}?`,
        confirmText: 'Cancel appointment'
      },
      width: '400px'
    });
    dialogRef.afterClosed().subscribe(confirmed => {
      if (confirmed) {
        this.appointmentService.cancelAppointment(appointment.id).subscribe({
          next: () => {
            this.snackBar.open('Appointment cancelled', 'OK', { duration: 3000 });
            this.loadAppointments();
          },
          error: (err) => this.snackBar.open(this.errorMessage(err, 'Failed to cancel appointment'), 'OK', { duration: 5000 })
        });
      }
    });
  }

  public complete(appointment: AppointmentResponse): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        title: 'Complete appointment',
        message: `Complete the appointment for ${appointment.patientName} on ${appointment.startTime ? new Date(appointment.startTime).toLocaleDateString('en-US') : ''}?`,
        confirmText: 'Complete appointment'
      },
      width: '400px'
    });
    dialogRef.afterClosed().subscribe(confirmed => {
      if (confirmed) {
        this.appointmentService.markAppointmentCompleted(appointment.id).subscribe({
          next: () => {
            this.snackBar.open('Appointment completed', 'OK', { duration: 3000 });
            this.loadAppointments();
          },
          error: (err) => this.snackBar.open(this.errorMessage(err, 'Failed to complete appointment'), 'OK', { duration: 5000 })
        })
      }
    });
  }

  private errorMessage(err: any, fallback: string): string {
    const errors = err?.error?._embedded?.errors;
    const detail = Array.isArray(errors) && errors[0]?.message ? errors[0].message : undefined;
    return detail ?? err?.error?.message ?? fallback;
  }

  public editComment(appointment: AppointmentResponse): void {
    this.dialog.open(CommentDialogComponent, {
      data: appointment,
      width: '480px'
    }).afterClosed().subscribe(saved => {
      if (saved) {
        this.snackBar.open('Comment updated', 'OK', { duration: 3000 });
        this.loadAppointments();
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
        this.loadAppointments();
      }
    });
  }

  public statusLabel(status: AppointmentStatus): string {
    switch (status) {
      case AppointmentStatus.Scheduled: return 'Scheduled';
      case AppointmentStatus.Cancelled: return 'Cancelled';
      case AppointmentStatus.Completed: return 'Completed';
      default: return status;
    }
  }

  public statusClass(status: AppointmentStatus): string {
    return status.toLowerCase();
  }

  public get hasActiveFilters(): boolean {
    return !!this.selectedStatus || !!this.selectedService || !!this.selectedPatientId || !!this.selectedPractitionerId;
  }

}
