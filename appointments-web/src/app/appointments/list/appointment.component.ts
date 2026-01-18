import { Component, OnInit } from '@angular/core';
import { AppointmentService } from '../appointment.service';

@Component({
  standalone: true,
  selector: 'appointment',
  templateUrl: 'appointment.component.html',
  styleUrl: 'appointment.component.scss'
})
export class AppointmentComponent implements OnInit {

  constructor(private appointmentService: AppointmentService) {
  }

  public ngOnInit(): void {
    //TODO: implement logic here
  }

}
