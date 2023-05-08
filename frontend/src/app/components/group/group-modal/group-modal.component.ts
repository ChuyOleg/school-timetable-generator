import { Component, Input } from '@angular/core';
import { GroupModalService } from "../../../services/group/group-modal.service";

@Component({
  selector: 'app-group-modal',
  templateUrl: './group-modal.component.html'
})
export class GroupModalComponent {

  @Input() title: string

  constructor(public modalService: GroupModalService) {
  }

}
