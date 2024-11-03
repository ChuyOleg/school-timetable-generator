import { Component, EventEmitter, Input, Output } from '@angular/core';
import { IGroup } from "../../../../models/group";
import { TeacherService } from "../../../../services/teacher/teacher.service";
import { RoomService } from "../../../../services/room/room.service";
import { FormControlFactory } from "../../utils/FormControlFactory";
import { FormControl } from "@angular/forms";
import { ITeacher } from "../../../../models/teacher";
import { IRoom } from "../../../../models/room";
import { LessonLimitsHolder } from "../../model/LessonLimitsHolder";
import { ValidatorUtils } from "../../utils/ValidatorUtils";
import { GroupLimitsPageState } from "../../model/GroupLimitsPageState";

@Component({
  selector: 'app-group-details-lesson-form',
  templateUrl: './group-details-lesson-form.component.html'
})
export class GroupDetailsLessonFormComponent {

  @Input() group: IGroup
  @Input() pageState: GroupLimitsPageState
  @Input() lessonDetailsForm: LessonLimitsHolder
  @Output() unpickSubjectEvent = new EventEmitter<void>();

  isSubjectUnpicked: boolean

  constructor(
    public teacherService: TeacherService,
    public roomService: RoomService
  ) {}

  addSubGroup2() {
    this.lessonDetailsForm.controlGroup.controls.teacher2 = FormControlFactory.createTeacherFormControl(null);
    this.lessonDetailsForm.controlGroup.controls.room2 = FormControlFactory.createRoomFormControl(null);
  }

  removeSubGroup2() {
    delete this.lessonDetailsForm.controlGroup.controls.teacher2;
    delete this.lessonDetailsForm.controlGroup.controls.room2;
  }

  compareTeachers(teacher1: ITeacher, teacher2: ITeacher) {
    return teacher1 && teacher2 ? teacher1.id === teacher2.id : teacher1 === teacher2;
  }

  compareRooms(room1: IRoom, room2: IRoom) {
    return room1 && room2 ? room1.id === room2.id : room1 === room2;
  }

  unpickSubject() {
    this.isSubjectUnpicked = true
    this.unpickSubjectEvent.emit();
  }

  get isHoursFormActive(): boolean {
    return this.hoursForm.dirty || this.pageState.isSubmitButtonPressed;
  }

  get areInvalidHoursForSubgroupsError(): boolean {
    if (this.teacher2Form == null) return false;

    return ValidatorUtils.areHoursNonInteger(this.hoursForm);
  }

  get isDuplicateTeachersError(): boolean {
    if (this.teacher2Form == null) return false;

    return ValidatorUtils.areDuplicatedTeachers(this.teacherForm, this.teacher2Form);
  }

  get isDuplicateRoomsError(): boolean {
    if (this.room2Form == null) return false;

    return ValidatorUtils.areDuplicatedRooms(this.roomForm, this.room2Form);
  }

  get isSubGroupOpen(): boolean {
    return Boolean(this.lessonDetailsForm.controlGroup.controls.teacher2);
  }

  get hoursForm(): FormControl<number | null> {
    return this.lessonDetailsForm.controlGroup.controls.hours;
  }

  get teacherForm(): FormControl<ITeacher | null> {
    return this.lessonDetailsForm.controlGroup.controls.teacher;
  }

  get roomForm(): FormControl<IRoom | null> {
    return this.lessonDetailsForm.controlGroup.controls.room;
  }

  get teacher2Form(): FormControl<ITeacher | null> | null {
    return this.lessonDetailsForm.controlGroup.controls.teacher2 || null;
  }

  get room2Form(): FormControl<IRoom | null> | null {
    return this.lessonDetailsForm.controlGroup.controls.room2 || null;
  }
}
