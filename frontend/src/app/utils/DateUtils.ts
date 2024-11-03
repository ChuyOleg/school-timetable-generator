import { EDayOfWeek } from "../models/enumeration/day-of-week";

export class DateUtils {

  static translateDayToUkrainian(day: EDayOfWeek): string {
    switch (day) {
      case EDayOfWeek.MONDAY: return 'Понеділок';
      case EDayOfWeek.TUESDAY: return 'Вівторок';
      case EDayOfWeek.WEDNESDAY: return 'Середа';
      case EDayOfWeek.THURSDAY: return 'Четвер';
      case EDayOfWeek.FRIDAY: return "П'ятниця";
      default: return '';
    }
  }
}
