import { Component, OnInit } from '@angular/core';
import { NgFor } from '@angular/common';
import { Slot } from '../slot';
import { SlotService } from '../slot.service';

@Component({
  standalone: true,
  selector: 'slot-list',
  imports: [NgFor],
  templateUrl: 'slot-list.component.html',
  styleUrl: 'slot-list.component.scss'
})
export class SlotListComponent implements OnInit {

  public slots: Slot[] = [];

  constructor(private slotService: SlotService) {
  }

  public ngOnInit(): void {
    this.slotService.getSlots().subscribe(response => this.slots = response);
  }

}
