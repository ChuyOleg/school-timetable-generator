import { Component, Input } from '@angular/core';
import { IShiftsIntersection } from "../../../models/shifts-intersection";
import { ShiftsIntersectionService } from "../../../services/shift/shifts-intersection.service";
import { ShiftsIntersectionModalService } from "../../../services/shift/shifts-intersection-modal.service";
import { DialogService } from "../../../services/dialog.service";
import { ConfirmDialogData } from "../../../models/confirm-dialog-data";

@Component({
  selector: 'app-shifts-intersection-block',
  templateUrl: './shifts-intersection-block.component.html'
})
export class ShiftsIntersectionBlockComponent {

  @Input() shiftsIntersection: IShiftsIntersection;

  constructor(
    private service: ShiftsIntersectionService,
    private modalService: ShiftsIntersectionModalService,
    private dialogService: DialogService
  ) {}

  delete() {
    this.dialogService.confirmDialog(this.buildConfigDialog())
      .subscribe(hasUserApproved => this.deleteIfUserApproved(hasUserApproved));
  }

  private deleteIfUserApproved(hasUserApproved: boolean) {
    if (!this.shiftsIntersection.id || !hasUserApproved) return;

    this.service.deleteById(this.shiftsIntersection.id).subscribe();
  }

  private buildConfigDialog(): ConfirmDialogData {
    return {
      title: "Ви впевнені?",
      message: `Хочете видалити [ (1) ${this.shiftsIntersection.shiftOneLessonNumber} = (2) ${this.shiftsIntersection.shiftTwoLessonNumber} ]?`,
      cancelText: "Ні",
      confirmText: "Так" };
  }
}
