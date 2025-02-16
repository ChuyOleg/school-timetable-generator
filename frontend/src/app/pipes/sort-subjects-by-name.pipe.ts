import { Pipe, PipeTransform } from '@angular/core';
import { ISubject } from "../models/subject";

@Pipe({
  name: 'sortSubjectsByName'
})
export class SortSubjectsByNamePipe implements PipeTransform {

  transform(subjects: ISubject[]): ISubject[] {
    return subjects.sort((s1: ISubject, s2:ISubject) => {
      return s1.name.localeCompare(s2.name);
    })
  }
}
