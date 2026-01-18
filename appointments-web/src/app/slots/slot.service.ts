import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Slot } from './slot';

@Injectable({ providedIn: 'root' })
export class SlotService {

  constructor(private readonly http: HttpClient) {}

  public getSlots(): Observable<Slot[]> {
    return this.http.get<Slot[]>('/api/slots');
  }

}
