import { Component, EventEmitter, Input, Output } from '@angular/core';
import { DatePipe, NgFor, NgIf } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { AppointmentResponse, AppointmentStatus } from '../../../../core/api';
import { statusClass, statusLabel } from '../../models/appointment-status';

@Component({
  selector: 'app-appointment-list',
  standalone: true,
  imports: [NgFor, NgIf, DatePipe, MatButtonModule, MatIconModule, MatProgressBarModule],
  templateUrl: './appointment-list.component.html',
  styleUrl: './appointment-list.component.scss'
})
export class AppointmentListComponent {
  @Input() appointments: AppointmentResponse[] = [];
  @Input() loading = false;
  @Input() error: string | null = null;
  @Input() hasActiveFilters = false;

  @Output() reschedule = new EventEmitter<AppointmentResponse>();
  @Output() editComment = new EventEmitter<AppointmentResponse>();
  @Output() cancel = new EventEmitter<AppointmentResponse>();
  @Output() complete = new EventEmitter<AppointmentResponse>();

  public statusLabel(status: AppointmentStatus): string {
    return statusLabel(status);
  }

  public statusClass(status: AppointmentStatus): string {
    return statusClass(status);
  }

  public isScheduled(appointment: AppointmentResponse): boolean {
    return appointment.status === AppointmentStatus.Scheduled;
  }
}
