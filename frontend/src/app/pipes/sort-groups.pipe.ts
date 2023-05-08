import { Pipe, PipeTransform } from '@angular/core';
import { IGroup } from "../models/group";

@Pipe({
  name: 'sortGroups'
})
export class SortGroupsPipe implements PipeTransform {

  transform(groups: IGroup[]): IGroup[] {
    return groups.sort((g1: IGroup, g2: IGroup) => {
      if (g1.gradeNumber === g2.gradeNumber) {
        return g1.letter.toLowerCase().localeCompare(g2.letter.toLowerCase());
      } else {
        return g2.gradeNumber - g1.gradeNumber;
      }
    })
  }

}
