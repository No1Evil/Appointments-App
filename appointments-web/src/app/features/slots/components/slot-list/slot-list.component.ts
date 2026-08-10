import { Component, EventEmitter, Input, Output } from '@angular/core';
import { DatePipe, NgFor, NgIf } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { SlotResponse } from '../../../../core/api';
import { SlotDayGroup } from '../../models/slot-day-group';

@Component({
  selector: 'app-slot-list',
  standalone: true,
  imports: [NgFor, NgIf, DatePipe, MatIconModule, MatProgressBarModule],
  templateUrl: './slot-list.component.html',
  styleUrl: './slot-list.component.scss'
})
export class SlotListComponent {
  @Input() slotGroups: SlotDayGroup[] = [];
  @Input() selectedService?: string;
  @Input() loading = false;
  @Input() error: string | null = null;

  @Output() bookSlot = new EventEmitter<SlotResponse>();

  public book(slot: SlotResponse): void {
    this.bookSlot.emit(slot);
  }
}
