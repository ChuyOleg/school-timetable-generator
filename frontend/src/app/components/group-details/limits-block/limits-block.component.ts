import { Component, Input, OnChanges } from '@angular/core';
import { IGroup } from "../../../models/group";
import { GroupService } from "../../../services/group/group.service";
import { SubjectService } from "../../../services/subject/subject.service";
import { RoomService } from "../../../services/room/room.service";
import { TeacherService } from "../../../services/teacher/teacher.service";
import { AbstractControl, FormControl, FormGroup, ValidationErrors, Validators } from "@angular/forms";
import { ISubject } from "../../../models/subject";
import { ITeacher } from "../../../models/teacher";
import { IRoom } from "../../../models/room";
import { EDayOfWeek } from "../../../models/enumeration/day-of-week";
import { IGroupLimits } from "../../../models/group-limits";
import { ITimeSlot } from "../../../models/time-slot";
import { EWeekType } from "../../../models/enumeration/week-type";
import { ISubjectLimits } from "../../../models/subject-limits";
import { delay, firstValueFrom } from "rxjs";

interface SubjectFormGroup {
  id?: number
  hours: FormControl<number | null>
  teacher: FormControl<ITeacher | null>
  room: FormControl<IRoom | null>
  teacher2?: FormControl<ITeacher | null>
  room2?: FormControl<IRoom | null>
}

@Component({
  selector: 'app-limits-block',
  templateUrl: './limits-block.component.html'
})
export class LimitsBlockComponent implements OnChanges {

  @Input() group: IGroup
  submitButtonIsPressed: boolean = false

  constructor(
    private groupService: GroupService,
    public subjectService: SubjectService,
    public teacherService: TeacherService,
    public roomService: RoomService
  ) {
  }

  pickedSubjects: ISubject[] = []
  unpickedSubjects: ISubject[] = []
  subjectFormGroupsRecord: Record<number, SubjectFormGroup> = []
  newSubjectFormControl: FormControl<ISubject|null> = new FormControl<ISubject|null>(null, [])

  maxHoursPerWeek: FormControl<number | null> = new FormControl<number | null>(0, [
    Validators.required,
    Validators.pattern("^[0-9]*$"),
    Validators.min(1),
    Validators.max(40)
  ])
  isLoaded: boolean = false;
  isCreated: boolean = false;
  days: EDayOfWeek[] = Object.values(EDayOfWeek)
  lessonNumbers: number[] = [1, 2, 3, 4, 5, 6, 7, 8]


  interschoolCombineFormGroup = new FormGroup({
    day: new FormControl<EDayOfWeek | null>(null, [Validators.required]),
    lessonNumber: new FormControl<number | null>(null, [Validators.required])
  })
  interschoolCombineIsPicked: boolean = false

  submit() {
    this.submitButtonIsPressed = true;

    if (this.isFormValid()) {
      const groupLimits = this.buildGroupLimits();

      if (this.isCreated) {
        this.editGroup(groupLimits);
      } else {
        this.createGroup(groupLimits);
      }
    }
  }

  private isFormValid(): boolean {
    return this.maxHoursPerWeek.valid && this.isInterschoolCombineValid() && this.isEveryPickedSubjectGroupValid();
  }

  private buildGroupLimits(): IGroupLimits {
    return {
      id: this.group.groupLimitsDto?.id,
      subjectLimitsDtoSet: this.buildSubjectLimitsSet(),
      maxHoursPerWeek: this.maxHoursPerWeek.value as number,
      interschoolCombine: this.buildInterschoolCombineInstance()
    };
  }

  private editGroup(groupLimits: IGroupLimits): void {
    this.group.groupLimitsDto = groupLimits;
    this.groupService.edit(this.group).subscribe(() => window.location.reload());
  }

  private createGroup(groupLimits: IGroupLimits): void {
    this.group.groupLimitsDto = groupLimits;
    this.groupService.create(this.group).subscribe(() => window.location.reload());
  }

  private isEveryPickedSubjectGroupValid(): boolean {
    return this.pickedSubjects.every(s => {
      if (s.id) {
        let valid = this.subjectFormGroupsRecord[s.id].hours.valid && this.subjectFormGroupsRecord[s.id].teacher.valid;

        if (valid && this.subjectFormGroupsRecord[s.id].teacher2) {
          valid = this.subjectFormGroupsRecord[s.id].teacher2?.valid || false;
        }

        if (valid && this.subjectFormGroupsRecord[s.id].room) {
          valid = this.subjectFormGroupsRecord[s.id].room?.valid || false;
        }

        if (valid && this.subjectFormGroupsRecord[s.id].room2) {
          valid = this.subjectFormGroupsRecord[s.id].room2?.valid || false;
        }

        return valid;
      }
      return false;
    });
  }

  private isInterschoolCombineValid(): boolean {
    return !this.interschoolCombineIsPicked || this.interschoolCombineFormGroup.valid;
  }

  private buildSubjectLimitsSet(): ISubjectLimits[] {
    const set: ISubjectLimits[] = []

    this.pickedSubjects.forEach(s => {
      if (s.id) {
        set.push({
          id: this.subjectFormGroupsRecord[s.id].id || this.findPossibleSubjectHoursId(s),
          subjectDto: s,
          hours: this.subjectFormGroupsRecord[s.id].hours.value as number,
          teacherDto: this.subjectFormGroupsRecord[s.id].teacher.value as ITeacher,
          roomDto: this.subjectFormGroupsRecord[s.id].room.value as IRoom,
          teacherDto2: this.subjectFormGroupsRecord[s.id].teacher2?.value as ITeacher,
          roomDto2: this.subjectFormGroupsRecord[s.id].room2?.value as IRoom
        });
      }
    })

    return set;
  }

  private buildInterschoolCombineInstance(): ITimeSlot | undefined {
    if (this.interschoolCombineIsPicked) {
      return {
        weekType: EWeekType.BOTH,
        day: this.interschoolCombineFormGroup.value.day as EDayOfWeek,
        lessonNumber: this.interschoolCombineFormGroup.value.lessonNumber as number
      }
    } else {
      return undefined;
    }
  }

  private findPossibleSubjectHoursId(subjectDto: ISubject): number | undefined {
    return  this.group.groupLimitsDto?.subjectLimitsDtoSet.find(dto => dto.subjectDto.id == subjectDto.id)?.id;
  }

  async ngOnChanges() {
    if (this.group) {

      const subjects = await firstValueFrom(this.subjectService.getAll().pipe(delay(500)));
      this.teacherService.getAll().subscribe();
      this.roomService.getAll().subscribe();

      this.pickedSubjects = [];
      this.unpickedSubjects = [];

      if (this.group.groupLimitsDto) {
        this.handleGroupLimitsDto(this.group.groupLimitsDto, subjects);
      } else {
        this.handleNoGroupLimitsDto(subjects);
      }

      this.setInterschoolCombineByGradeNumber(this.group.gradeNumber);
      this.isLoaded = true;
    }
  }

  handleGroupLimitsDto(groupLimits: IGroupLimits, subjects: ISubject[]) {
    groupLimits.subjectLimitsDtoSet.forEach(dto => {
      this.pickedSubjects.push(dto.subjectDto);
      this.createSubjectFormGroup(dto.subjectDto);
      if (dto.subjectDto.id) {
        this.subjectFormGroupsRecord[dto.subjectDto.id].id = dto.id;
        this.subjectFormGroupsRecord[dto.subjectDto.id].hours.setValue(dto.hours);
        this.subjectFormGroupsRecord[dto.subjectDto.id].teacher.setValue(dto.teacherDto);
        this.subjectFormGroupsRecord[dto.subjectDto.id].room?.setValue(dto.roomDto || null);
        if (dto.teacherDto2) {
          this.addSubGroup2(dto.subjectDto);
          this.subjectFormGroupsRecord[dto.subjectDto.id].teacher2?.setValue(dto.teacherDto2 || null);
          this.subjectFormGroupsRecord[dto.subjectDto.id].room2?.setValue(dto.roomDto2 || null);
        }
      }
    })

    this.unpickedSubjects = subjects.filter(s => {
      return this.pickedSubjects.find(subj => subj.id === s.id) == null;
    });

    this.initInterschoolCombineIfPresent(groupLimits);
    this.isCreated = true;
  }

  handleNoGroupLimitsDto(subjects: ISubject[]) {
    this.pickedSubjects = subjects;

    this.pickedSubjects.forEach(subject => {
      this.createSubjectFormGroup(subject);
    })
  }

  pickSubject() {
    const subject = this.newSubjectFormControl.value as ISubject;
    if (this.newSubjectFormControl.valid) {
      this.createSubjectFormGroup(subject);

      this.unpickedSubjects = this.unpickedSubjects.filter(subj => subj.id != subject.id);
      this.pickedSubjects.push(subject);

      this.newSubjectFormControl.setValue(null);
    }
  }

  async unpickSubject(subject: ISubject) {
    if (subject.id) {
      this.unpickedSubjects.push(subject);
      await new Promise(resolve => setTimeout(resolve, 500));
      this.pickedSubjects = this.pickedSubjects.filter(subj => subj.id != subject.id)

      delete this.subjectFormGroupsRecord[subject.id];
      this.calculateSum();
    }
  }

  getSubjectHoursFormControl(subject: ISubject): FormControl<number|null> {
    return subject.id ? this.subjectFormGroupsRecord[subject.id].hours : new FormControl;
  }

  getSubjectTeacherFormControl(subject: ISubject): FormControl<ITeacher|null> {
    return subject.id ? this.subjectFormGroupsRecord[subject.id].teacher : new FormControl;
  }

  getSubjectTeacherSubGroup2FormControl(subject: ISubject): FormControl<ITeacher|null> {
    return subject.id ? (this.subjectFormGroupsRecord[subject.id].teacher2 || new FormControl) : new FormControl;
  }

  getSubjectRoomFormControl(subject: ISubject): FormControl<IRoom|null> {
    return subject.id ? this.subjectFormGroupsRecord[subject.id].room : new FormControl;
  }

  getSubjectRoomSubGroup2FormControl(subject: ISubject): FormControl<IRoom|null>{
    return subject.id ? (this.subjectFormGroupsRecord[subject.id].room2 || new FormControl) : new FormControl;
  }

  get interschoolCombineDay() {
    return this.interschoolCombineFormGroup.controls.day as FormControl;
  }

  get interschoolCombineLessonNumber() {
    return this.interschoolCombineFormGroup.controls.lessonNumber as FormControl;

  }

  subGroup2IsOpen(subject: ISubject): boolean {
    return Boolean(subject.id && this.subjectFormGroupsRecord[subject.id].teacher2);
  }

  addSubGroup2(subject: ISubject) {
    if (subject.id) {
      this.subjectFormGroupsRecord[subject.id].teacher2 = this.createSubjectTeacherFormControl();
      this.subjectFormGroupsRecord[subject.id].room2 = this.createSubjectRoomFormControl();
    }
  }

  removeSubGroup2(subject: ISubject) {
    if (subject.id) {
      this.subjectFormGroupsRecord[subject.id].teacher2 = undefined;
      this.subjectFormGroupsRecord[subject.id].room2 = undefined;
    }
  }

  private calculateSum(): void {
    let sum: number = 0;

    Object.values(this.subjectFormGroupsRecord).forEach(subjectFormGroup => {
      if (subjectFormGroup.hours.valid) {
        sum += Number(subjectFormGroup.hours.value);
      }
    });

    this.maxHoursPerWeek.setValue(sum);
  }

  private initInterschoolCombineIfPresent(groupLimits: IGroupLimits) {
    if (groupLimits.interschoolCombine) {
      this.interschoolCombineIsPicked = true;
      this.interschoolCombineFormGroup.controls.day.setValue(groupLimits.interschoolCombine.day);
      this.interschoolCombineFormGroup.controls.lessonNumber.setValue(groupLimits.interschoolCombine.lessonNumber);
    }
  }

  private setInterschoolCombineByGradeNumber(gradeNumber: number) {
    this.interschoolCombineIsPicked = gradeNumber >= 10;
  }

  toggleInterschoolCombine() {
    this.interschoolCombineIsPicked = !this.interschoolCombineIsPicked;
  }

  private createSubjectFormGroup(subject: ISubject) {
    if (subject.id) {
      this.subjectFormGroupsRecord[subject.id] = {
        hours: this.createSubjectHoursFormControl(),
        teacher: this.createSubjectTeacherFormControl(),
        room: this.createSubjectRoomFormControl()
      }
    }
  }

  private createSubjectHoursFormControl(): FormControl<number|null> {
    const formControl: FormControl<number|null> = new FormControl<number|null>(null, [
      Validators.required,
      Validators.pattern("^[0-9]+(\\.5)?$"),
      Validators.min(0.5),
      Validators.max(10),
      this.subgroupsHoursIsIntegerValidator.bind(this)]
    );

    formControl.valueChanges.subscribe(() => this.calculateSum());
    return formControl;
  }

  private subgroupsHoursIsIntegerValidator(control: AbstractControl): ValidationErrors | null {
    const hours: number = control.value;

    const teacher2 = Object.values(this.subjectFormGroupsRecord)
      .find(formGroup => formGroup.hours === control)?.teacher2 || null;

    if (teacher2 && !Number.isInteger(Number(hours))) {
      control.setErrors({ subgroupHoursIsInteger: true })
      return { subgroupHoursIsInteger: true };
    } else {
      control.setErrors(null)
      return null;
    }
  }

  private createSubjectTeacherFormControl(): FormControl<ITeacher|null> {
    return new FormControl<ITeacher|null>(null, [
      Validators.required,
      this.subgroupsTeachersAreDifferentValidator.bind(this)]
    );
  }

  private subgroupsTeachersAreDifferentValidator(control: AbstractControl): ValidationErrors | null {
    const formGroup = Object.values(this.subjectFormGroupsRecord)
      .find(formGroup => (formGroup.teacher === control) || (formGroup.teacher2 === control));

    if (formGroup && formGroup.teacher2?.value && (formGroup.teacher.value?.id === formGroup.teacher2.value?.id)) {
      control.setErrors({ subgroupsTeachersAreSame: true })
      return { subgroupsTeachersAreSame: true };
    } else {
      control.setErrors(null);
      return null;
    }
  }

  private createSubjectRoomFormControl(): FormControl<IRoom|null> {
    return new FormControl<IRoom|null>(null, [
      this.subgroupsRoomsAreDifferentValidator.bind(this)
    ]);
  }

  private subgroupsRoomsAreDifferentValidator(control: AbstractControl): ValidationErrors | null {
    const formGroup = Object.values(this.subjectFormGroupsRecord)
      .find(formGroup => (formGroup.room === control) || (formGroup.room2 === control));

    if (formGroup && formGroup.room2?.value && (formGroup.room.value?.id === formGroup.room2.value?.id)) {
      control.setErrors({ subgroupsRoomsAreSame: true })
      return { subgroupsRoomsAreSame: true };
    } else {
      control.setErrors(null);
      return null;
    }
  }

  compareTeachers(teacher1: ITeacher, teacher2: ITeacher) {
    return teacher1 && teacher2 ? teacher1.id === teacher2.id : teacher1 === teacher2;
  }

  compareRooms(room1: IRoom, room2: IRoom) {
    return room1 && room2 ? room1.id === room2.id : room1 === room2;
  }

  translateDayToUkrainian(day: EDayOfWeek): string {
    switch (day) {
      case EDayOfWeek.MONDAY: return 'Понеділок';
      case EDayOfWeek.TUESDAY: return 'Вівторок';
      case EDayOfWeek.WEDNESDAY: return 'Середа';
      case EDayOfWeek.THURSDAY: return 'Четвер';
      case EDayOfWeek.FRIDAY: return "П'ятниця";
    }
  }

}
