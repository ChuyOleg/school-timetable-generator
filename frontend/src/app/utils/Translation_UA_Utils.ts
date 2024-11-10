import { EDayOfWeek } from "../models/enumeration/day-of-week";
import { EImportanceLevel } from "../models/enumeration/importance-level";

export class Translation_UA_Utils {

  static translateDay(day: EDayOfWeek): string {
    switch (day) {
      case EDayOfWeek.MONDAY: return 'Понеділок';
      case EDayOfWeek.TUESDAY: return 'Вівторок';
      case EDayOfWeek.WEDNESDAY: return 'Середа';
      case EDayOfWeek.THURSDAY: return 'Четвер';
      case EDayOfWeek.FRIDAY: return "П'ятниця";
      default: return '';
    }
  }

  static translateImportanceLevel(importanceLevel: EImportanceLevel): string {
    switch (importanceLevel) {
      case EImportanceLevel.HIGH: return 'Важливо';
      case EImportanceLevel.MEDIUM: return 'Бажано';
      case EImportanceLevel.LOW: return 'Байдуже';
      default: return '';
    }
  }
}
