import { Pipe, PipeTransform } from '@angular/core';
import { ITeacher } from "../models/teacher/teacher";
import { IGroup } from "../models/group";

@Pipe({
  name: 'addClassTeacherForPrimarySchool'
})
export class AddClassTeacherForPrimarySchoolPipe implements PipeTransform {

  transform(teachers: ITeacher[], group: IGroup): ITeacher[] {
    if (group.gradeNumber <= 4 && !teachers.find(t => t.id == group.teacherDto.id)) {
      teachers.unshift(group.teacherDto);
    }

    return teachers;
  }

}
