import { Component, OnInit } from '@angular/core';
import { TeacherService } from "../../services/teacher/teacher.service";

@Component({
  selector: 'app-teacher-page',
  templateUrl: './teacher-page.component.html'
})
export class TeacherPageComponent implements OnInit {

  loading: boolean = false
  term = ''

  constructor(
    public teacherService: TeacherService
  ) {
  }

  ngOnInit(): void {
    this.loading = true;
    this.teacherService.getAll().subscribe(() => {
      this.loading = false;
    })
  }

}
