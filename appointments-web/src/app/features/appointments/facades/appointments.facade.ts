import { Injectable, computed, signal } from '@angular/core';
import { Observable, finalize } from 'rxjs';
import {
  AppointmentResponse,
  AppointmentService,
  AppointmentStatus,
  RescheduleAppointmentRequest,
  UpdateAppointmentCommentRequest
} from '../../../core/api';

export interface AppointmentFilters {
  practitionerId?: string;
  status?: AppointmentStatus;
  service?: string;
  patientId?: string;
}

@Injectable({ providedIn: 'root' })
export class AppointmentsFacade {
  private readonly _appointments = signal<AppointmentResponse[]>([]);
  private readonly _loading = signal(false);
  private readonly _error = signal<string | null>(null);

  private readonly _status = signal<AppointmentStatus | undefined>(undefined);
  private readonly _service = signal<string | undefined>(undefined);
  private readonly _patientId = signal<string | undefined>(undefined);
  private readonly _practitionerId = signal<string | undefined>(undefined);

  public readonly appointments = this._appointments.asReadonly();
  public readonly loading = this._loading.asReadonly();
  public readonly error = this._error.asReadonly();

  public readonly status = this._status.asReadonly();
  public readonly service = this._service.asReadonly();
  public readonly patientId = this._patientId.asReadonly();
  public readonly practitionerId = this._practitionerId.asReadonly();

  public readonly totalCount = computed(() => this._appointments().length);
  public readonly hasActiveFilters = computed(() =>
    !!this._status() || !!this._service() || !!this._patientId() || !!this._practitionerId()
  );

  constructor(private api: AppointmentService) {}

  public updateFilters(filters: Partial<AppointmentFilters>): void {
    if ('status' in filters) this._status.set(filters.status);
    if ('service' in filters) this._service.set(filters.service);
    if ('patientId' in filters) this._patientId.set(filters.patientId);
    if ('practitionerId' in filters) this._practitionerId.set(filters.practitionerId);
    this.loadAppointments();
  }

  public loadAppointments(): void {
    this._loading.set(true);
    this._error.set(null);

    this.api.getAppointments(
      this._practitionerId(),
      this._status(),
      this._service(),
      this._patientId()
    ).pipe(
      finalize(() => this._loading.set(false))
    ).subscribe({
      next: (data) => this._appointments.set(data),
      error: (err) => this._error.set(this.toErrorMessage(err, 'Failed to load appointments'))
    });
  }

  public cancelAppointment(appointmentId: string): Observable<AppointmentResponse> {
    return this.api.cancelAppointment(appointmentId);
  }

  public completeAppointment(appointmentId: string): Observable<AppointmentResponse> {
    return this.api.markAppointmentCompleted(appointmentId);
  }

  public updateComment(request: UpdateAppointmentCommentRequest): Observable<AppointmentResponse> {
    return this.api.updateAppointmentComment(request);
  }

  public rescheduleAppointment(request: RescheduleAppointmentRequest): Observable<AppointmentResponse> {
    return this.api.rescheduleAppointment(request);
  }

  public getAppointment(appointmentId: string): Observable<AppointmentResponse> {
    return this.api.getAppointmentById(appointmentId);
  }

  public toErrorMessage(err: any, fallback: string): string {
    const errors = err?.error?._embedded?.errors;
    const detail = Array.isArray(errors) && errors[0]?.message ? errors[0].message : undefined;
    return detail ?? err?.error?.message ?? fallback;
  }
}
