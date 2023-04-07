import { Component, OnInit } from '@angular/core';
import { GroupService } from "../../services/group/group.service";
import { GroupModalService } from "../../services/group/group-modal.service";

@Component({
  selector: 'app-group-page',
  templateUrl: './group-page.component.html'
})
export class GroupPageComponent implements OnInit {

  loading: boolean = false
  term = ''

  constructor(
    public groupService: GroupService,
    public modalService: GroupModalService
  ) {
  }

  ngOnInit(): void {
    this.loading = true;
    this.groupService.getAll().subscribe(() => {
      this.loading = false;
    })
  }

}
