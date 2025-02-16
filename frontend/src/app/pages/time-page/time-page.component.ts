import { Component, OnInit } from '@angular/core';
import { ShiftsIntersectionService } from "../../services/shift/shifts-intersection.service";
import { ShiftsIntersectionModalService } from "../../services/shift/shifts-intersection-modal.service";
import { delay } from "rxjs";

@Component({
  selector: 'app-time-page',
  templateUrl: './time-page.component.html'
})
export class TimePageComponent implements OnInit {

  loading: boolean = false;

  constructor(
    public service: ShiftsIntersectionService,
    public modalService: ShiftsIntersectionModalService
  ) {}

  ngOnInit(): void {
    this.loading = true;
    this.service.getAll()
      .pipe(delay(500))
      .subscribe(() => this.loading = false);
  }
}
