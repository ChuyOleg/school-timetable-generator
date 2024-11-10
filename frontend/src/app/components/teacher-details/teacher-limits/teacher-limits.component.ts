import { Component, Input, OnChanges, OnInit } from '@angular/core';
import { ITeacher } from "../../../models/teacher/teacher";
import { FormControl, Validators } from "@angular/forms";
import { TeacherLimitsPageState } from "../model/TeacherLimitsPageState";
import { IMaxLessonsLimit } from "../../../models/teacher/limits/max-lessons-limit";
import { ILessonsOrderLimit } from "../../../models/teacher/limits/lessons-order-limit";
import { IFreeDayLimit } from "../../../models/teacher/limits/free-day-limit";
import { IDesiredPeriodLimit } from "../../../models/teacher/limits/desired-period-limit";
import { FormControlFactory } from "../utils/FormControlFactory";
import { ValidatorUtils } from "../utils/ValidatorUtils";
import { TeacherConstraints } from "../model/TeacherConstraints";
import { TeacherLimitsMapper } from "../utils/TeacherLimitsMapper";
import { ITeacherLimits } from "../../../models/teacher/teacher-limits";
import { TeacherService } from "../../../services/teacher/teacher.service";
import { MaxLessonsConstraint } from "../model/constraint/MaxLessonsConstraint";
import { LessonsOrderConstraint } from "../model/constraint/LessonsOrderConstraint";
import { FreeDayConstraint } from "../model/constraint/FreeDayConstraint";
import { PeriodConstraint } from "../model/constraint/PeriodConstraint";

@Component({
  selector: 'app-teacher-limits',
  templateUrl: './teacher-limits.component.html'
})
export class TeacherLimitsComponent implements OnInit, OnChanges{

  @Input() teacher: ITeacher;

  pageState: TeacherLimitsPageState;

  constraints: TeacherConstraints;

  constructor(private teacherService: TeacherService) {}

  submit(): void {
    if (!this.areLimitsValid()) return;

    this.pageState.isSubmitButtonPressed = true;

    const limits = TeacherLimitsMapper.map(this.constraints);

    this.save(limits);
  }

  private areLimitsValid(): boolean {
    return ValidatorUtils.areFreeDayLimitsValid(this.constraints.freeDayConstraints)
      && ValidatorUtils.areDesiredPeriodLimitsValid(this.constraints.desiredPeriodConstraints)
      && ValidatorUtils.isMaxLessonPerDayLimitValid(this.constraints.maxLessonPerDayConstraint)
      && ValidatorUtils.isLessonsOrderLimitValid(this.constraints.lessonsOrderConstraint);
  }

  private save(limits: ITeacherLimits): void {
    this.teacher.limits = limits;
    this.teacherService.edit(this.teacher).subscribe(() => window.location.reload());
  }

  ngOnInit(): void {
    this.pageState = { isLoaded: false, isSubmitButtonPressed: false }
  }

  ngOnChanges(): void {
    if (!this.teacher || !this.teacher.limits.id) return;

    this.constraints = {
      id: this.teacher.limits.id,
      maxLessonPerDayConstraint: this.initMaxLessonsLimit(this.teacher.limits.maxLessonsLimit),
      lessonsOrderConstraint: this.initLessonsOrderLimit(this.teacher.limits.lessonsOrderLimit),
      freeDayConstraints: this.initFreeDayLimits(this.teacher.limits.freeDayLimits),
      desiredPeriodConstraints: this.initDesiredPeriodLimits(this.teacher.limits.desiredPeriodLimits) }

    this.pageState.isLoaded = true;
  }

  private initMaxLessonsLimit(limit: IMaxLessonsLimit | undefined): MaxLessonsConstraint {
    return  {
      id: limit?.id,
      count: new FormControl<number | null>(limit?.count ?? null, Validators.required),
      isActive: (limit != null) }
  }

  private initLessonsOrderLimit(limit: ILessonsOrderLimit): LessonsOrderConstraint {
    return  {
      id: limit.id,
      importanceLevel: new FormControl(limit.importanceLevel, { nonNullable: true }) }
  }

  private initFreeDayLimits(limits: IFreeDayLimit[]): FreeDayConstraint[] {
    const freeDayConstraints: FreeDayConstraint[] = [];

    limits.forEach(limit => freeDayConstraints.push({
      id: limit.id,
      day: FormControlFactory.dayFormControl(limit.day) }));

    return freeDayConstraints;
  }

  private initDesiredPeriodLimits(limits: IDesiredPeriodLimit[]): PeriodConstraint[] {
    const desiredPeriodConstraints: PeriodConstraint[] = [];

    limits.forEach(limit => desiredPeriodConstraints.push({
      id: limit.id,
      day: FormControlFactory.dayFormControl(limit.day),
      lessonFrom: FormControlFactory.lessonNumberFormControl(limit.lessonFrom),
      lessonTo: FormControlFactory.lessonNumberFormControl(limit.lessonTo) }));

    return desiredPeriodConstraints;
  }
}
