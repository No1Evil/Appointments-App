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
  ServiceCategory,
  ServiceCategoryService,
  SlotResponse,
  SlotService,
  SlotStatus
} from '../../api';
import { SlotDialogComponent } from '../dialog/slot-dialog.component';

interface SlotDayGroup {
  date: Date;
  label: string;
  slots: SlotResponse[];
}

@Component({
  selector: 'slot-list',
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
  templateUrl: 'slot-list.component.html',
  styleUrl: 'slot-list.component.scss'
})
export class SlotListComponent implements OnInit {

  public slots: SlotResponse[] = [];
  public services: ServiceCategory[] = [];
  public selectedService?: string;
  public loading = false;
  public slotGroups: SlotDayGroup[] = [];

  constructor(
    private slotService: SlotService,
    private serviceCategoryService: ServiceCategoryService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  public ngOnInit(): void {
    this.serviceCategoryService.getAllServices().subscribe(services => this.services = services);
    this.loadSlots();
  }

  public loadSlots(): void {
    this.loading = true;
    this.slotService.getSlots(SlotStatus.Free, this.selectedService, new Date().toISOString()).subscribe({
      next: (slots) => {
        this.slots = slots;
        this.groupSlots(slots);
        this.loading = false;
      },
      error: () => {
        this.loading = false;
        this.snackBar.open('Failed to load slots', 'OK', { duration: 4000 });
      }
    });
  }

  public book(slot: SlotResponse): void {
    const dialogRef = this.dialog.open(SlotDialogComponent, {
      data: slot,
      width: '480px'
    });
    dialogRef.afterClosed().subscribe(booked => {
      if (booked) {
        this.snackBar.open('Appointment booked', 'OK', { duration: 3000 });
        this.loadSlots();
      }
    });
  }

  private groupSlots(slots: SlotResponse[]): void {
    const sorted = [...slots].sort((a, b) => (a.startTime ?? '').localeCompare(b.startTime ?? ''));
    const byDay = new Map<string, SlotResponse[]>();
    for (const slot of sorted) {
      const key = (slot.startTime ?? '').slice(0, 10);
      if (!byDay.has(key)) {
        byDay.set(key, []);
      }
      byDay.get(key)!.push(slot);
    }
    this.slotGroups = Array.from(byDay.entries()).map(([key, daySlots]) => {
      const [year, month, day] = key.split('-').map(Number);
      const date = new Date(year, month - 1, day);
      return {
        date,
        label: date.toLocaleDateString('en-US', { weekday: 'long', month: 'long', day: 'numeric' }),
        slots: daySlots
      };
    });
  }
}
