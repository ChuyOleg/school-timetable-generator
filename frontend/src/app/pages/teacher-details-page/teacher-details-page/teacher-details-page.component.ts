import { Component, OnInit } from '@angular/core';
import { ITeacher } from "../../../models/teacher";
import { TeacherService } from "../../../services/teacher/teacher.service";
import { ActivatedRoute } from "@angular/router";

@Component({
  selector: 'app-teacher-details-page',
  templateUrl: './teacher-details-page.component.html'
})
export class TeacherDetailsPageComponent implements OnInit {

  teacherId: number
  teacher: ITeacher

  constructor(
    private route: ActivatedRoute,
    private teacherService: TeacherService
  ) {}

  ngOnInit(): void {
    this.teacherId = Number(this.route.snapshot.paramMap.get('id'));
    this.teacherService.getById(this.teacherId).subscribe(teacher => this.teacher = teacher)
  }
}
