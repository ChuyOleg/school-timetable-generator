import { Component, OnInit } from '@angular/core';
import { IGroup } from "../../models/group";
import { ActivatedRoute } from "@angular/router";
import { GroupService } from "../../services/group/group.service";

@Component({
  selector: 'app-group-details-page',
  templateUrl: './group-details-page.component.html'
})
export class GroupDetailsPageComponent implements OnInit {

  groupId: number;
  group: IGroup;

  constructor(
    private route: ActivatedRoute,
    private groupService: GroupService,
  ) {
  }

  ngOnInit(): void {
    this.groupId = Number(this.route.snapshot.paramMap.get('id'));
    this.groupService.getById(this.groupId).subscribe(group => this.group = group);
  }

}
