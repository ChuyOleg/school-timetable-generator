import { Component, OnInit } from '@angular/core';
import { ISubject } from "./models/subject";
import { SubjectService } from "./services/subject.service";
import { Observable, tap } from "rxjs";
import { ModalService } from "./services/modal.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  // subjects: ISubject[] = []
  loading: boolean = false
  subjects$: Observable<ISubject[]>
  term = ''

  constructor(
    private subjectService: SubjectService,
    public modalService: ModalService
  ) {
  }

  ngOnInit(): void {
    this.loading = true;
    this.subjects$ = this.subjectService.getAll().pipe(
      tap(() => this.loading = false)
    );
    // this.subjectService.getAll().subscribe(subjects => {
    //   this.subjects = subjects;
    //   this.loading = false;
    // })
  }

}
