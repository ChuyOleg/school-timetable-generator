import { Pipe, PipeTransform } from '@angular/core';
import { IGroup } from "../models/group";

@Pipe({
  name: 'sortGroups'
})
export class SortGroupsPipe implements PipeTransform {

  transform(groups: IGroup[]): IGroup[] {
    return groups.sort((g1: IGroup, g2: IGroup) => {
      return (g1.gradeNumber + g1.letter).localeCompare(g2.gradeNumber + g2.letter);
    })
  }

}
