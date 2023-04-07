import { Pipe, PipeTransform } from '@angular/core';
import { IGroup } from "../models/group";

@Pipe({
  name: 'filterGroups'
})
export class FilterGroupsPipe implements PipeTransform {

  transform(groups: IGroup[], search: string): IGroup[] {
    if (search.length === 0) return groups;
    return groups.filter(g => (g.gradeNumber + '-' + g.letter).toLowerCase()
      .includes(search.toLowerCase()));
  }

}
