import { Pipe, PipeTransform } from '@angular/core';
import { EDayOfWeek } from "../models/enumeration/day-of-week";
import { Translation_UA_Utils } from "../utils/Translation_UA_Utils";

@Pipe({
  name: 'translateDay'
})
export class TranslateDayPipe implements PipeTransform {

  transform(value: EDayOfWeek): string {
    return Translation_UA_Utils.translateDay(value);
  }
}
