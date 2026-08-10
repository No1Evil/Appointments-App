import { Injectable, signal } from '@angular/core';
import { PractitionerResponse, PractitionerService } from '../../../core/api';
import { finalize } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class PractitionersFacade {
  private readonly _practitioners = signal<PractitionerResponse[]>([]);
  private readonly _loading = signal(false);
  private readonly _error = signal<string | null>(null);
  private readonly _selectedService = signal<string | undefined>(undefined);

  public readonly practitioners = this._practitioners.asReadonly();
  public readonly loading = this._loading.asReadonly();
  public readonly error = this._error.asReadonly();
  public readonly selectedService = this._selectedService.asReadonly();

  constructor(private practitionerService: PractitionerService) {}

  public selectService(serviceCode: string): void {
    this._selectedService.set(serviceCode);
    this.loadPractitioners();
  }

  public loadPractitioners(): void {
    this._loading.set(true);
    this._error.set(null);

    this.practitionerService.getPractitioners(this._selectedService()).pipe(
      finalize(() => this._loading.set(false))
    ).subscribe({
      next: (res) => this._practitioners.set(res),
      error: (err) => this._error.set(this.toMessage(err))
    });
  }

  private toMessage(err: unknown): string {
    return err instanceof Error ? err.message : 'Failed to load practitioners';
  }
}
