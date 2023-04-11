import { Component, Input, OnChanges } from '@angular/core';
import { IGroup } from "../../../models/group";
import { GroupService } from "../../../services/group/group.service";
import { SubjectService } from "../../../services/subject/subject.service";
import { RoomService } from "../../../services/room/room.service";
import { TeacherService } from "../../../services/teacher/teacher.service";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ISubject } from "../../../models/subject";
import { ITeacher } from "../../../models/teacher";
import { IRoom } from "../../../models/room";
import { EDayOfWeek } from "../../../models/enumeration/day-of-week";
import { IGroupLimits } from "../../../models/group-limits";
import { ITimeSlot } from "../../../models/time-slot";
import { EWeekType } from "../../../models/enumeration/week-type";
import { ISubjectLimits } from "../../../models/subject-limits";

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

    if (this.maxHoursPerWeek.valid && this.isInterschoolCombineValid() && this.isEveryPickedSubjectGroupValid()) {
      const groupLimits: IGroupLimits = {
        id: this.group.groupLimitsDto?.id,
        subjectLimitsDtoSet: this.buildSubjectLimitsSet(),
        maxHoursPerWeek: this.maxHoursPerWeek.value as number,
        interschoolCombine: this.buildInterschoolCombineInstance()
      }

      console.log(groupLimits)

      if (this.isCreated) {
        this.group.groupLimitsDto = groupLimits;
        this.groupService.edit(this.group).subscribe(() => window.location.reload());
      } else {
        this.group.groupLimitsDto = groupLimits;
        this.groupService.create(this.group).subscribe(() => window.location.reload());
      }
    }

  }

  private isEveryPickedSubjectGroupValid(): boolean {
    return this.pickedSubjects.every(s => {
      if (s.id) {
        let valid = this.subjectFormGroupsRecord[s.id].hours.valid && this.subjectFormGroupsRecord[s.id].teacher.valid;

        if (valid && this.subjectFormGroupsRecord[s.id].teacher2) {
          valid = this.subjectFormGroupsRecord[s.id].teacher2?.valid || false;
        }

        return valid;
      }
      return false;
    });
  }

  private isInterschoolCombineValid(): boolean {
    if (this.interschoolCombineIsPicked) {
      return this.interschoolCombineFormGroup.valid;
    } else {
      return true;
    }
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

  ngOnChanges() {
    if (this.group) {

      if (this.group.groupLimitsDto) {
        this.subjectService.getAll().subscribe(subjects => {
          this.pickedSubjects = []
          this.unpickedSubjects = []

          this.group.groupLimitsDto?.subjectLimitsDtoSet.forEach(dto => {
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

          if (this.group.groupLimitsDto?.interschoolCombine) {
            this.interschoolCombineIsPicked = true;
            this.interschoolCombineFormGroup.controls.day.setValue(this.group.groupLimitsDto.interschoolCombine.day);
            this.interschoolCombineFormGroup.controls.lessonNumber.setValue(this.group.groupLimitsDto.interschoolCombine.lessonNumber);
          }

          this.isLoaded = true;
        })
        this.isCreated = true;

      } else {
        this.subjectService.getAll().subscribe(subjects => {
          this.pickedSubjects = subjects;
          this.unpickedSubjects = [];

          this.pickedSubjects.forEach(subject => {
            this.createSubjectFormGroup(subject);
          })
          this.isLoaded = true;
        })
      }

      if (this.group.gradeNumber >= 9) {
        this.interschoolCombineIsPicked = true;
      }

      this.teacherService.getAll().subscribe();
      this.roomService.getAll().subscribe();
    }
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
    if (subject.id) {
      return this.subjectFormGroupsRecord[subject.id].hours;
    } else {
      return new FormControl;
    }
  }

  getSubjectTeacherFormControl(subject: ISubject): FormControl<ITeacher|null> {
    if (subject.id) {
      return this.subjectFormGroupsRecord[subject.id].teacher;
    } else {
      return new FormControl;
    }
  }

  getSubjectTeacherSubGroup2FormControl(subject: ISubject): FormControl<ITeacher|null> {
    if (subject.id) {
      return this.subjectFormGroupsRecord[subject.id].teacher2 || new FormControl;
    } else {
      return new FormControl;
    }
  }

  getSubjectRoomFormControl(subject: ISubject): FormControl<IRoom|null> {
    if (subject.id) {
      return this.subjectFormGroupsRecord[subject.id].room;
    } else {
      return new FormControl;
    }
  }

  getSubjectRoomSubGroup2FormControl(subject: ISubject): FormControl<IRoom|null>{
    if (subject.id) {
      return this.subjectFormGroupsRecord[subject.id].room2 || new FormControl;
    } else {
      return new FormControl;
    }
  }

  get interschoolCombineDay() {
    return this.interschoolCombineFormGroup.controls.day as FormControl;
  }

  get interschoolCombineLessonNumber() {
    return this.interschoolCombineFormGroup.controls.lessonNumber as FormControl;

  }

  subGroup2IsOpen(subject: ISubject): boolean {
    if (subject.id) {
      return (this.subjectFormGroupsRecord[subject.id].teacher2 != undefined);
    }
    return false;
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
      Validators.max(10)]);

    formControl.valueChanges.subscribe(() => this.calculateSum());
    return formControl;
  }

  private createSubjectTeacherFormControl(): FormControl<ITeacher|null> {
    return new FormControl<ITeacher|null>(null, [
      Validators.required
    ]);
  }

  private createSubjectRoomFormControl(): FormControl<IRoom|null> {
    return new FormControl<IRoom|null>(null, []);
  }

  compareTeachers(teacher1: ITeacher, teacher2: ITeacher) {
    return teacher1 && teacher2 ? teacher1.id === teacher2.id : teacher1 === teacher2;
  }

  compareRooms(room1: IRoom, room2: IRoom) {
    return room1 && room2 ? room1.id === room2.id : room1 === room2;
  }

}
