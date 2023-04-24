import { Component } from '@angular/core';
import { TeacherService } from "../../services/teacher/teacher.service";
import { firstValueFrom } from "rxjs";
import { ITeacherInvalidLoading } from "../../models/util";

@Component({
  selector: 'app-timetable-page',
  templateUrl: './timetable-page.component.html'
})
export class TimetablePageComponent {

  public teachersWithInvalidLoading: ITeacherInvalidLoading[] = []

  constructor(
    public teacherService: TeacherService
  ) {
  }

  async generate() {
    await this.checkTeachersLoading();
    if (this.teachersWithInvalidLoading.length === 0) {
      console.log("GENERATING...")
    } else {
      console.log("Teacher loading error")
    }
  }

  private async checkTeachersLoading() {
    await firstValueFrom(this.teacherService.getAll());
    const teachersActualHours = await firstValueFrom(this.teacherService.getTeachersHours());
    this.teacherService.teachers.forEach(teacher => {
      const actualHours = teachersActualHours.find(t => t.id === teacher.id)?.totalHours;
      if (teacher.maxHoursPerWeek != actualHours) {
        this.teachersWithInvalidLoading.push({
          name: teacher.name,
          expectedHours: teacher.maxHoursPerWeek,
          actualHours: actualHours as number
        })
      }
    });
  }

}
