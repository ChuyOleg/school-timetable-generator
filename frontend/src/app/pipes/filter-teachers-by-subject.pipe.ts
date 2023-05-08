import { Pipe, PipeTransform } from '@angular/core';
import { ITeacher } from "../models/teacher";
import { ISubject } from "../models/subject";

@Pipe({
  name: 'filterTeachersBySubject'
})
export class FilterTeachersBySubjectPipe implements PipeTransform {

  transform(teachers: ITeacher[], subject: ISubject): ITeacher[] {
    if (subject == null) return [];
    return teachers.filter(t => this.teacherHasSubject(t, subject));
  }

  private teacherHasSubject(teacher: ITeacher, subject: ISubject): boolean {
    return teacher.subjectDtoSet.find(s => s.id === subject.id) != null;
  }

}
