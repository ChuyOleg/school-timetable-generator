import { Pipe, PipeTransform } from '@angular/core';
import { ISubject } from "../models/subject";

@Pipe({
  name: 'filterSubjects'
})
export class FilterSubjectsPipe implements PipeTransform {

  transform(subjects: ISubject[], search: string): ISubject[] {
    return subjects.filter(s => s.name.toLowerCase().includes(search.toLowerCase()));
  }

}
