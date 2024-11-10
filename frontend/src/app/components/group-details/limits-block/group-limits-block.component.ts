import { Component, Input, OnChanges } from '@angular/core';
import { IGroup } from "../../../models/group";
import { GroupService } from "../../../services/group/group.service";
import { SubjectService } from "../../../services/subject/subject.service";
import { RoomService } from "../../../services/room/room.service";
import { TeacherService } from "../../../services/teacher/teacher.service";
import { FormControl, Validators } from "@angular/forms";
import { ISubject } from "../../../models/subject";
import { EDayOfWeek } from "../../../models/enumeration/day-of-week";
import { IGroupLimits } from "../../../models/group-limits";
import { delay, firstValueFrom } from "rxjs";
import { Utils } from "../utils/Utils";
import { ValidatorUtils } from "../utils/ValidatorUtils";
import { GroupLimitsPageState } from "../model/GroupLimitsPageState";
import { CommonUtils } from "../../../utils/CommonUtils";
import { LessonLimitsHolder } from "../model/LessonLimitsHolder";
import { LessonLimitsFactory } from "../utils/LessonLimitsFactory";
import { FormControlFactory } from "../utils/FormControlFactory";
import { InterschoolCombine } from "../model/InterschoolCombine";

// todo: 16/10/24 user has no possibility to pick 'NO teacher_2' or 'NO room' when it picked some value.
//  In case, user did it accidentally - he should have choice to unset it.
// todo: 06/11/24  do not forget about possibility for user to set fixed lessons (those cannot be changed)
//  (that should be a new functionality non-related to this page, apparently).
@Component({
  selector: 'app-group-limits-block',
  templateUrl: './group-limits-block.component.html'
})
export class GroupLimitsBlockComponent implements OnChanges {

  @Input() group: IGroup

  newSubjectFormControl: FormControl<ISubject|null> = new FormControl<ISubject|null>(null, [])
  pickedLessonConstraints: LessonLimitsHolder[] = []
  unpickedSubjects: ISubject[] = []
  maxHoursPerWeek: FormControl<number | null>;
  pageState: GroupLimitsPageState;
  interschoolCombine: InterschoolCombine;

  constructor(
    private groupService: GroupService,
    public subjectService: SubjectService,
    public teacherService: TeacherService,
    public roomService: RoomService) {

    this.pageState = {
      isLoaded: false,
      isDataAlreadyCreated: false,
      isSubmitButtonPressed: false,
      isInterschoolCombinePicked: false };
    this.initInterschoolCombine();
    this.maxHoursPerWeek = FormControlFactory.createMaxHoursPerWeekFormControl();
  }

  private initInterschoolCombine(): void {
    this.interschoolCombine = {
      day: new FormControl<EDayOfWeek | null>(null, [Validators.required]),
      lessonNumber: new FormControl<number | null>(null, [Validators.required]) };
  }

  submit() {
    this.pageState.isSubmitButtonPressed = true;

    if (!this.isFormValid()) return;

    const groupLimits = this.buildGroupLimits();

    if (this.pageState.isDataAlreadyCreated) {
      this.editGroup(groupLimits);
    } else {
      this.createGroup(groupLimits);
    }
  }

  private isFormValid(): boolean {
    return this.maxHoursPerWeek.valid
      && this.isInterschoolCombineValid()
      && ValidatorUtils.areLessonsValid(this.pickedLessonConstraints);
  }

  private buildGroupLimits(): IGroupLimits {
    return {
      id: this.group.groupLimitsDto?.id,
      subjectLimitsDtoSet: Utils.buildLessonLimitsNew(this.pickedLessonConstraints, this.group),
      maxHoursPerWeek: this.maxHoursPerWeek.value as number,
      interschoolCombine: Utils.buildInterschoolCombineInstance(this.pageState, this.interschoolCombine)
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

  private isInterschoolCombineValid(): boolean {
    return !this.pageState.isInterschoolCombinePicked
      || (this.interschoolCombine.day.valid && this.interschoolCombine.lessonNumber.valid);
  }

  async ngOnChanges() {
    if (!this.group) return;

    const subjects = await firstValueFrom(this.subjectService.getAll().pipe(delay(500)));
    this.teacherService.getAll().subscribe();
    this.roomService.getAll().subscribe();
    this.unpickedSubjects = [];

    if (this.group.groupLimitsDto) {
      this.handleGroupLimitsDto(this.group.groupLimitsDto, subjects);
    } else {
      this.handleNoGroupLimitsDto(subjects);
    }

    this.setInterschoolCombineByGradeNumber(this.group.gradeNumber);
    this.pageState.isLoaded = true
  }

  handleGroupLimitsDto(groupLimits: IGroupLimits, subjects: ISubject[]) {
    const pickedSubjects: Set<ISubject> = new Set<ISubject>();
    groupLimits.subjectLimitsDtoSet.forEach(subjectLimits => {
      const lessonLimits = LessonLimitsFactory.createLessonLimitsWithValues(subjectLimits, this.pickedLessonConstraints, this.maxHoursPerWeek);
      this.pickedLessonConstraints.push(lessonLimits);

      pickedSubjects.add(subjectLimits.subjectDto);
    })

    this.unpickedSubjects = subjects.filter(subject => !pickedSubjects.has(subject))

    this.maxHoursPerWeek.setValue(groupLimits.maxHoursPerWeek);
    this.initInterschoolCombineIfPresent(groupLimits);
    this.pageState.isDataAlreadyCreated = true
  }

  handleNoGroupLimitsDto(subjects: ISubject[]) {
    subjects.forEach(subject => {
      const lessonLimits = LessonLimitsFactory.createLessonLimits(subject, this.pickedLessonConstraints, this.maxHoursPerWeek);
      this.pickedLessonConstraints.push(lessonLimits); })
  }

  pickSubject() {
    const subject = this.newSubjectFormControl.value as ISubject;
    if (subject == null) return;

    this.unpickedSubjects = this.unpickedSubjects.filter(subj => subj.id != subject.id);

    const pickedLessonConstraint = LessonLimitsFactory.createLessonLimits(subject, this.pickedLessonConstraints, this.maxHoursPerWeek);
    this.pickedLessonConstraints.push(pickedLessonConstraint);

    this.newSubjectFormControl.setValue(null);
  }

  async unpickSubject(subject: ISubject) {
    this.unpickedSubjects.push(subject);

    await CommonUtils.artificialPause(500);

    this.pickedLessonConstraints = this.pickedLessonConstraints
      .filter(lessonConstraint => lessonConstraint.subject.id != subject.id)

    Utils.calculateHoursSumAndSetIt(this.pickedLessonConstraints, this.maxHoursPerWeek);
  }

  private initInterschoolCombineIfPresent(groupLimits: IGroupLimits) {
    if (groupLimits.interschoolCombine) {
      this.pageState.isInterschoolCombinePicked = true;
      this.interschoolCombine.day.setValue(groupLimits.interschoolCombine.day);
      this.interschoolCombine.lessonNumber.setValue(groupLimits.interschoolCombine.lessonNumber);
    }
  }

  private setInterschoolCombineByGradeNumber(gradeNumber: number) {
    this.pageState.isInterschoolCombinePicked = gradeNumber >= 10;
  }
}
