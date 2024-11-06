import { Component, Input, OnChanges, OnInit } from '@angular/core';
import { IRoom } from "../../../models/room";
import { FormControl, Validators } from "@angular/forms";
import { EDayOfWeek } from "../../../models/enumeration/day-of-week";
import { RoomConstraint } from "../model/RoomConstraint";
import { RoomLimitsPageState } from "../model/RoomLimitsPageState";
import { IRoomLimit } from "../../../models/room-limit";
import { RoomService } from "../../../services/room/room.service";
import { CommonUtils } from "../../../utils/CommonUtils";
import { RoomLimitMapper } from "../utils/RoomLimitMapper";

@Component({
  selector: 'app-room-limits',
  templateUrl: './room-limits.component.html'
})
export class RoomLimitsComponent implements OnInit, OnChanges {

  @Input() room: IRoom;

  pageState: RoomLimitsPageState;
  constraints: RoomConstraint[];
  days: EDayOfWeek[];
  lessonNumbers: number[];
  temporaryDeletedLimit: RoomConstraint | null;

  constructor(private roomService: RoomService) {}

  addNewConstraint(): void {
    this.constraints.push({
      day: new FormControl<EDayOfWeek | null>(null, [Validators.required]),
      lessonFrom: new FormControl<number | null>(null, [Validators.required]),
      lessonTo: new FormControl<number | null>(null, [Validators.required])
    })
  }

  async deleteConstraint(constraint: RoomConstraint) {
    this.temporaryDeletedLimit = constraint;
    await CommonUtils.artificialPause(500);
    this.temporaryDeletedLimit = null;

    this.constraints = this.constraints.filter(roomConstraint => roomConstraint != constraint);
  }

  submit(): void {
    if (this.constraints.length === 0 || !this.areLimitsValid()) return;

    this.pageState.isSubmitButtonPressed = true;
    const limits = RoomLimitMapper.mapMany(this.constraints);

    if (this.pageState.isDataAlreadyCreated) {
      this.editRoom(limits);
    } else {
      this.saveRoom(limits);
    }
  }

  private areLimitsValid(): boolean {
    return this.constraints.every(constraint => {
      const lessonFrom = constraint.lessonFrom;
      const lessonTo = constraint.lessonTo;

      return constraint.day.valid && lessonFrom.valid && lessonTo.valid
        && this.areLessonNumbersValid(constraint)});
  }

  areLessonNumbersValid(constraint: RoomConstraint): boolean {
    const lessonFrom = constraint.lessonFrom.value;
    const lessonTo = constraint.lessonTo.value;

    if (lessonFrom == null || lessonTo  == null) return true;

    return lessonTo > lessonFrom;
  }

  private saveRoom(limits: IRoomLimit[]): void {
    this.room.roomLimitDtoSet = limits;
    this.roomService.create(this.room).subscribe(() => window.location.reload())
  }

  private editRoom(limits: IRoomLimit[]): void {
    this.room.roomLimitDtoSet = limits;
    this.roomService.edit(this.room).subscribe(() => window.location.reload());
  }

  ngOnInit(): void {
    this.constraints = [];
    this.lessonNumbers = [1, 2, 3, 4, 5, 6, 7, 8];
    this.days = Object.values(EDayOfWeek);
    this.pageState = { isLoaded: false, isDataAlreadyCreated: false, isSubmitButtonPressed: false }
  }

  ngOnChanges(): void {
    if (!this.room) return;

    if (this.room.roomLimitDtoSet && this.room.roomLimitDtoSet.length > 0) {
      this.initLimits(this.room.roomLimitDtoSet);
      this.pageState.isDataAlreadyCreated = true;
    }

    this.pageState.isLoaded = true;
  }

  private initLimits(limits: IRoomLimit[]): void {
    limits.forEach(limit => {
      const constraint: RoomConstraint = {
        id: limit.id,
        day: new FormControl<EDayOfWeek | null>(limit.day, [Validators.required]),
        lessonFrom: new FormControl<number | null>(limit.lessonNumberFrom, [Validators.required]),
        lessonTo: new FormControl<number | null>(limit.lessonNumberTo, [Validators.required])
      }

      this.constraints.push(constraint);
    })
  }

  isDeleted(constraint: RoomConstraint): boolean {
    return constraint === this.temporaryDeletedLimit;
  }
}
