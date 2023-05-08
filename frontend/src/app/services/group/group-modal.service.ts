import { Injectable } from '@angular/core';
import { BehaviorSubject } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class GroupModalService {

  createIsVisible$ = new BehaviorSubject(false)
  updateIsVisible$ = new BehaviorSubject(false)

  constructor() { }

  openCreateModal() {
    this.createIsVisible$.next(true)
    this.updateIsVisible$.next(false)
  }

  closeCreateModal() {
    this.createIsVisible$.next(false)
  }

  openUpdateModal() {
    this.updateIsVisible$.next(true)
  }

  closeUpdateModal() {
    this.updateIsVisible$.next(false)
  }

  closeAllModals() {
    this.createIsVisible$.next(false)
    this.updateIsVisible$.next(false)
  }

}
