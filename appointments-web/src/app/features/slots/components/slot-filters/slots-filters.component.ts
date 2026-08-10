import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { ServiceCategoryResponse } from '../../../../core/api';

@Component({
  selector: 'app-slot-filters',
  standalone: true,
  imports: [CommonModule, FormsModule, MatFormFieldModule, MatSelectModule],
  templateUrl: 'slots-filters.component.html',
  styleUrl: 'slots-filters.component.scss'
})
export class SlotsFiltersComponent {
  @Input() services: ServiceCategoryResponse[] = [];
  @Input() selectedService?: string;

  @Output() serviceChanged = new EventEmitter<string>();

  onServiceChange(code: string) {
    this.serviceChanged.emit(code);
  }
}
