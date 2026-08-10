import { Routes } from '@angular/router';
import { SlotsPageComponent } from './features/slots/pages/slots-page.component';
import { AppointmentsPageComponent } from './features/appointments/pages/appointments-page.component';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'slots' },
  { path: 'slots', component: SlotsPageComponent },
  { path: 'appointments', component: AppointmentsPageComponent }
];
