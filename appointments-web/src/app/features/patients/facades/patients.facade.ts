import { Injectable, signal } from '@angular/core';
import { PatientResponse, PatientService } from '../../../core/api';
import { finalize } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class PatientsFacade {
  private readonly _patients = signal<PatientResponse[]>([]);
  private readonly _loading = signal(false);
  private readonly _error = signal<string | null>(null);
  private loaded = false;

  public readonly patients = this._patients.asReadonly();
  public readonly loading = this._loading.asReadonly();
  public readonly error = this._error.asReadonly();

  constructor(private patientService: PatientService) {}

  public loadPatients(): void {
    if (this.loaded) {
      return;
    }

    this._loading.set(true);
    this._error.set(null);

    this.patientService.getAllPatients().pipe(
      finalize(() => this._loading.set(false))
    ).subscribe({
      next: (res) => {
        this._patients.set(res);
        this.loaded = true;
      },
      error: (err) => this._error.set(this.toMessage(err))
    });
  }

  private toMessage(err: unknown): string {
    return err instanceof Error ? err.message : 'Failed to load patients';
  }
}
