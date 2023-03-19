import { Component, OnInit } from '@angular/core';
import { SubjectService } from "../../services/subject/subject.service";
import { SubjectModalService } from "../../services/subject/subject-modal.service";

@Component({
  selector: 'app-subject-block-page',
  templateUrl: './subject-page.component.html'
})
export class SubjectPageComponent implements OnInit{

  loading: boolean = false
  term = ''

  constructor(
    public subjectService: SubjectService,
    public modalService: SubjectModalService
  ) {
  }

  ngOnInit(): void {
    this.loading = true;
    this.subjectService.getAll().subscribe(() => {
      this.loading = false;
    })
  }

}
