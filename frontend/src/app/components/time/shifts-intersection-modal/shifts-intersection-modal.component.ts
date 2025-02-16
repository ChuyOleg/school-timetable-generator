import { Component, Input } from '@angular/core';
import { ShiftsIntersectionModalService } from "../../../services/shift/shifts-intersection-modal.service";

@Component({
  selector: 'app-shifts-intersection-modal',
  templateUrl: './shifts-intersection-modal.component.html'
})
export class ShiftsIntersectionModalComponent {

  @Input() title: string;

  constructor(public modalService: ShiftsIntersectionModalService) {}
}
