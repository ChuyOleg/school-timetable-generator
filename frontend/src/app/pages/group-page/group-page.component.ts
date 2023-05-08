import { Component, OnInit } from '@angular/core';
import { GroupService } from "../../services/group/group.service";
import { GroupModalService } from "../../services/group/group-modal.service";
import { delay } from "rxjs";

@Component({
  selector: 'app-group-page',
  templateUrl: './group-page.component.html'
})
export class GroupPageComponent implements OnInit {

  loading: boolean = false
  term = ''
  maxGroupCount = 40

  constructor(
    public groupService: GroupService,
    public modalService: GroupModalService
  ) {
  }

  ngOnInit(): void {
    this.loading = true;
    this.groupService.getAll().pipe(delay(500)).subscribe(() => {
      this.loading = false;
    })
  }

}
