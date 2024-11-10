import { Pipe, PipeTransform } from '@angular/core';
import { EImportanceLevel } from "../models/enumeration/importance-level";
import { Translation_UA_Utils } from "../utils/Translation_UA_Utils";

@Pipe({
  name: 'translateLessonsOrderImportanceLevel'
})
export class TranslateLessonsOrderImportanceLevelPipe implements PipeTransform {

  transform(value: EImportanceLevel): string {
    return Translation_UA_Utils.translateImportanceLevel(value);
  }
}
