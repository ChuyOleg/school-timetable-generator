import { Component, OnInit } from '@angular/core';
import { SubjectService } from "../../services/subject/subject.service";
import { SubjectModalService } from "../../services/subject/subject-modal.service";
import { delay } from "rxjs";

@Component({
  selector: 'app-subject-block-page',
  templateUrl: './subject-page.component.html'
})
export class SubjectPageComponent implements OnInit{

  loading: boolean = false
  term = ''
  maxSubjectCount = 40

  constructor(
    public subjectService: SubjectService,
    public modalService: SubjectModalService
  ) {
  }

  ngOnInit(): void {
    this.loading = true;
    this.subjectService.getAll().pipe(delay(500)).subscribe(() => {
      this.loading = false;
    })
  }

}
