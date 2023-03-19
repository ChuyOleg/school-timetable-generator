import { Pipe, PipeTransform } from '@angular/core';
import { ITeacher } from "../models/teacher";

@Pipe({
  name: 'sortTeachersByName'
})
export class SortTeachersByNamePipe implements PipeTransform {

  transform(teachers: ITeacher[]): ITeacher[] {
    return teachers.sort((t1: ITeacher, t2: ITeacher) => {
      return t1.name.localeCompare(t2.name);
    });
  }

}
