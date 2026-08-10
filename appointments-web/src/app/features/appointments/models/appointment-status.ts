import { AppointmentStatus } from '../../../core/api';

export function statusLabel(status: AppointmentStatus): string {
  switch (status) {
    case AppointmentStatus.Scheduled: return 'Scheduled';
    case AppointmentStatus.Cancelled: return 'Cancelled';
    case AppointmentStatus.Completed: return 'Completed';
    default: return status;
  }
}

export function statusClass(status: AppointmentStatus): string {
  return status.toLowerCase();
}
