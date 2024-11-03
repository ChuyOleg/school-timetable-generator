import { Pipe, PipeTransform } from '@angular/core';
import { LessonLimitsHolder } from "../components/group-details/model/LessonLimitsHolder";

@Pipe({
  name: 'sortLessonLimitsBySubjectName'
})
export class SortLessonLimitsBySubjectNamePipe implements PipeTransform {

  transform(lessonLimits: LessonLimitsHolder[]): LessonLimitsHolder[] {
    return lessonLimits.sort((limit1, limit2) =>
      limit1.subject.name.localeCompare(limit2.subject.name))
  }
}
