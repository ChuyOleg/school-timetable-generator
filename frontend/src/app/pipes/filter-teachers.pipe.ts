import { Pipe, PipeTransform } from '@angular/core';
import { ITeacher } from "../models/teacher";

@Pipe({
  name: 'filterTeachers'
})
export class FilterTeachersPipe implements PipeTransform {

  transform(teachers: ITeacher[], search: string): ITeacher[] {
    if (search.length === 0) return teachers;
    return teachers.filter(t => t.name.toLowerCase().includes(search.toLowerCase()));
  }

}
