import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class AppointmentService {

  constructor(private readonly http: HttpClient) {}

  //TODO: define methods here
}
