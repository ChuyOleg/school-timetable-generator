export enum EDayOfWeek {
  MONDAY = "MONDAY",
  TUESDAY = "TUESDAY",
  WEDNESDAY = "WEDNESDAY",
  THURSDAY = "THURSDAY",
  FRIDAY = "FRIDAY"
}

export const EDayOfWeekTranslations: { [key in EDayOfWeek]: string } = {
  [EDayOfWeek.MONDAY]: 'Понеділок',
  [EDayOfWeek.TUESDAY]: 'Вівторок',
  [EDayOfWeek.WEDNESDAY]: 'Середа',
  [EDayOfWeek.THURSDAY]: 'Четвер',
  [EDayOfWeek.FRIDAY]: "П'ятниця"
};
