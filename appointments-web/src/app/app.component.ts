import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SlotListComponent } from './slots/list/slot-list.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, SlotListComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {

}
