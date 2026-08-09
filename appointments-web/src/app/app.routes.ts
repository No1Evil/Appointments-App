import { Routes } from '@angular/router';
import { AppointmentComponent } from './appointments/list/appointment.component';
import { SlotListComponent } from './slots/list/slot-list.component';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'slots' },
  { path: 'slots', component: SlotListComponent },
  { path: 'appointments', component: AppointmentComponent }
];
