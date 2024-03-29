import { Component, Input } from '@angular/core';
import { IGroup } from "../../../models/group";
import { GroupService } from "../../../services/group/group.service";
import { GroupModalService } from "../../../services/group/group-modal.service";
import { DialogService } from "../../../services/dialog.service";

@Component({
  selector: 'app-group-block',
  templateUrl: './group-block.component.html'
})
export class GroupBlockComponent {

  @Input() group: IGroup

  constructor(
    private groupService: GroupService,
    private modalService: GroupModalService,
    private dialogService: DialogService
  ) {
  }

  delete() {
    this.dialogService.confirmDialog({
      title: "Ви впевнені?",
      message: `Хочете видалити [ ${this.group.gradeNumber + '-' + this.group.letter} ]?`,
      cancelText: "Ні",
      confirmText: "Так"
    }).subscribe(bool => {
      if (this.group.id && bool) {
        this.groupService.deleteById(this.group.id)
          .subscribe();
      }
    });
  }

  update() {
    this.groupService.groupToEdit = this.group;
    this.modalService.openUpdateModal();
  }

}
