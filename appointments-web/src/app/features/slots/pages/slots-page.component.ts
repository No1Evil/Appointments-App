import { Component, OnInit } from '@angular/core';
import { AsyncPipe, NgIf } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SlotResponse } from '../../../core/api';
import { SlotsFacade } from '../facades/slots.facade';
import { ServiceCategoriesFacade } from '../../service-categories/facades/service-categories.facade';
import { SlotsFiltersComponent } from '../components/slot-filters/slots-filters.component';
import { SlotListComponent } from '../components/slot-list/slot-list.component';
import { SlotDialogComponent } from '../components/slot-dialog/slot-dialog.component';

@Component({
  selector: 'app-slots-page',
  standalone: true,
  imports: [NgIf, AsyncPipe, SlotsFiltersComponent, SlotListComponent],
  templateUrl: './slots-page.component.html',
  styleUrl: './slots-page.component.scss'
})
export class SlotsPageComponent implements OnInit {

  constructor(
    public readonly facade: SlotsFacade,
    public readonly serviceCategoriesFacade: ServiceCategoriesFacade,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  public ngOnInit(): void {
    this.serviceCategoriesFacade.loadServices();
    this.facade.loadSlots();
  }

  public onServiceChanged(code: string): void {
    this.facade.selectService(code);
  }

  public book(slot: SlotResponse): void {
    this.dialog.open(SlotDialogComponent, { data: slot, width: '480px' })
      .afterClosed()
      .subscribe(booked => {
        if (!booked) {
          return;
        }
        this.snackBar.open('Appointment booked', 'OK', { duration: 4000 });
        this.facade.loadSlots();
      });
  }
}
