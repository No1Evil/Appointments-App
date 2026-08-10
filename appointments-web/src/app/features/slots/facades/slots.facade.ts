import { Injectable, computed, signal } from '@angular/core';
import { SlotResponse, SlotService, SlotStatus } from '../../../core/api';
import { Observable, finalize } from 'rxjs';
import { SlotDayGroup } from '../models/slot-day-group';

@Injectable({ providedIn: 'root' })
export class SlotsFacade {
  private readonly _slots = signal<SlotResponse[]>([]);
  private readonly _selectedService = signal<string | undefined>(undefined);
  private readonly _loading = signal(false);
  private readonly _error = signal<string | null>(null);

  public readonly slots = this._slots.asReadonly();
  public readonly selectedService = this._selectedService.asReadonly();
  public readonly loading = this._loading.asReadonly();
  public readonly error = this._error.asReadonly();

  public readonly groupedSlots = computed<SlotDayGroup[]>(() => {
    const slots = this._slots();
    if (slots.length === 0) return [];

    const sorted = [...slots].sort((a, b) => (a.startTime ?? '').localeCompare(b.startTime ?? ''));
    const byDay = new Map<string, SlotResponse[]>();

    for (const slot of sorted) {
      const key = (slot.startTime ?? '').slice(0, 10);
      if (!byDay.has(key)) byDay.set(key, []);
      byDay.get(key)!.push(slot);
    }

    return Array.from(byDay.entries()).map(([key, daySlots]) => {
      const [year, month, day] = key.split('-').map(Number);
      const date = new Date(year, month - 1, day);
      return {
        date,
        label: date.toLocaleDateString('en-US', { weekday: 'long', month: 'long', day: 'numeric' }),
        slots: daySlots
      };
    });
  });

  constructor(private slotService: SlotService) {}

  public selectService(code?: string): void {
    this._selectedService.set(code);
    this.loadSlots();
  }

  public loadSlots(): void {
    this._loading.set(true);
    this._error.set(null);

    this.slotService.getSlots(
      SlotStatus.Free,
      this._selectedService(),
      new Date().toISOString()
    ).pipe(
      finalize(() => this._loading.set(false))
    ).subscribe({
      next: (res) => this._slots.set(res),
      error: (err) => this._error.set(this.toMessage(err))
    });
  }

  public loadFreeSlots(serviceCode?: string): Observable<SlotResponse[]> {
    return this.slotService.getSlots(SlotStatus.Free, serviceCode, new Date().toISOString());
  }

  private toMessage(err: unknown): string {
    return err instanceof Error ? err.message : 'Failed to load slots';
  }
}
