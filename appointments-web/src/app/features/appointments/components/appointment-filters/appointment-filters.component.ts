import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import {
  AppointmentStatus,
  PatientResponse,
  PractitionerResponse,
  ServiceCategoryResponse
} from '../../../../core/api';
import { statusLabel } from '../../models/appointment-status';

@Component({
  selector: 'app-appointment-filters',
  standalone: true,
  imports: [CommonModule, FormsModule, MatFormFieldModule, MatSelectModule],
  templateUrl: 'appointment-filters.component.html',
  styleUrl: 'appointment-filters.component.scss'
})
export class AppointmentFiltersComponent {
  @Input() statuses: AppointmentStatus[] = [];
  @Input() services: ServiceCategoryResponse[] = [];
  @Input() patients: PatientResponse[] = [];
  @Input() practitioners: PractitionerResponse[] = [];

  @Input() selectedStatus?: AppointmentStatus;
  @Input() selectedService?: string;
  @Input() selectedPatientId?: string;
  @Input() selectedPractitionerId?: string;

  @Output() statusChanged = new EventEmitter<AppointmentStatus>();
  @Output() serviceChanged = new EventEmitter<string>();
  @Output() patientChanged = new EventEmitter<string>();
  @Output() practitionerChanged = new EventEmitter<string>();

  public statusLabel(status: AppointmentStatus): string {
    return statusLabel(status);
  }
}
