import { Injectable } from '@angular/core';
import { BehaviorSubject } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ShiftsIntersectionModalService {

  createIsVisible$ = new BehaviorSubject(false)

  openCreateModal() {
    this.createIsVisible$.next(true)
  }

  closeCreateModal() {
    this.createIsVisible$.next(false)
  }

  closeAllModals() {
    this.createIsVisible$.next(false)
  }
}
