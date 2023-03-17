import { Component, OnInit } from '@angular/core';
import { SubjectService } from "../../services/subject/subject.service";
import { SubjectModalService } from "../../services/subject/subject-modal.service";

@Component({
  selector: 'app-subject-block-page',
  templateUrl: './subject-page.component.html'
})
export class SubjectPageComponent implements OnInit{

  // subjects: ISubject[] = []
  loading: boolean = false
  // subjects$: Observable<ISubject[]>
  term = ''

  constructor(
    public subjectService: SubjectService,
    public modalService: SubjectModalService
  ) {
  }

  ngOnInit(): void {
    this.loading = true;
    // this.subjects$ = this.subjectService.getAll().pipe(
    //   tap(() => this.loading = false)
    // );
    this.subjectService.getAll().subscribe(() => {
      this.loading = false;
    })
  }

}
