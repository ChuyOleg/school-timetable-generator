import { Pipe, PipeTransform } from '@angular/core';
import { EDayOfWeek } from "../models/enumeration/day-of-week";
import { DateUtils } from "../utils/DateUtils";

@Pipe({
  name: 'translateDay'
})
export class TranslateDayPipe implements PipeTransform {

  transform(value: EDayOfWeek): string {
    return DateUtils.translateDayToUkrainian(value);
  }
}
