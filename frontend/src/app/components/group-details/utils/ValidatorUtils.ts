import { LessonLimitsHolder } from "../model/LessonLimitsHolder";
import { FormControl } from "@angular/forms";
import { IRoom } from "../../../models/room";
import { ITeacher } from "../../../models/teacher/teacher";
import { LessonLimitsGroup } from "../model/LessonLimitsGroup";

export class ValidatorUtils {

  static areLessonsValid(pickedLessonConstraints: LessonLimitsHolder[]): boolean {
    return pickedLessonConstraints.every(lessonConstraint => {
      const controls = lessonConstraint.controlGroup.controls;

      return this.isLessonBasicallyValid(controls) && this.isLessonComplexlyValid(controls);
    });
  }

  private static isLessonBasicallyValid(controls: LessonLimitsGroup): boolean {
    return controls.hours.valid
      && controls.teacher.valid
      && (!controls.teacher2 || controls.teacher2.valid)
      && (!controls.room || controls.room.valid)
      && (!controls.room2 || controls.room2.valid)
  }

  private static isLessonComplexlyValid(controls: LessonLimitsGroup): boolean {
    return (controls.teacher2 == null || !this.areHoursNonInteger(controls.hours))
      && (controls.teacher2 == null || !this.areDuplicatedTeachers(controls.teacher, controls.teacher2))
      && (controls.room2 == null || !this.areDuplicatedRooms(controls.room, controls.room2));
  }

  static areHoursNonInteger(hours: FormControl<number | null>): boolean {
    return !Number.isInteger(Number(hours.value));
  }

  static areDuplicatedTeachers(teacher1: FormControl<ITeacher | null>, teacher2: FormControl<ITeacher | null>): boolean {
    return teacher1.value != null && teacher2.value != null && teacher1.value.id === teacher2.value.id;
  }

  static areDuplicatedRooms(room1: FormControl<IRoom | null>, room2: FormControl<IRoom | null>): boolean {
    return room1.value != null && room2.value != null && room1.value.id === room2.value.id;
  }
}
