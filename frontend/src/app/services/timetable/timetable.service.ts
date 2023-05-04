import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Router } from "@angular/router";
import { ErrorService } from "../error.service";
import { Constants } from "../../config/constants";
import { catchError, Observable, tap, throwError } from "rxjs";
import { ITimetable } from "../../models/timetable";
import { ITimetableFines } from "../../models/util/timetable-fines";

@Injectable({
  providedIn: 'root'
})
export class TimetableService {

  constructor(
    private http: HttpClient,
    private router: Router,
    private errorService: ErrorService
  ) { }

  private baseUrl = Constants.API_BASE_URL
  timetable: ITimetable | null
  timetableFines: ITimetableFines | null

  getForUser(): Observable<ITimetable> {
    return this.http.get<ITimetable>(`${this.baseUrl}timetables`)
      .pipe(
        tap(timetable => this.timetable = timetable),
        catchError(this.errorHandler.bind(this))
      )
  }

  generate(): Observable<ITimetable> {
    return this.http.get<ITimetable>(`${this.baseUrl}timetables/generate`)
      .pipe(
        tap(timetable => this.timetable = timetable),
        catchError(this.errorHandler.bind(this))
      )
  }

  checkFitness(timetable: ITimetable): Observable<ITimetableFines> {
    return this.http.post<ITimetableFines>(`${this.baseUrl}timetables/checkFitness`, timetable)
      .pipe(
        tap(timetableFines => this.timetableFines = timetableFines),
        catchError(this.errorHandler.bind(this))
      )
  }

  save(timetable: ITimetable): Observable<ITimetable> {
    return this.http.post<ITimetable>(`${this.baseUrl}timetables`, timetable)
      .pipe(
        tap(timetable => this.timetable = timetable),
        catchError(this.errorHandler.bind(this))
      )
  }

  update(timetable: ITimetable): Observable<ITimetable> {
    return this.http.put<ITimetable>(`${this.baseUrl}timetables`, timetable)
      .pipe(
        tap(timetable => this.timetable = timetable),
        catchError(this.errorHandler.bind(this))
      )
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}timetables/${id}`)
      .pipe(
        tap(() => this.timetable = null),
        catchError(this.errorHandler.bind(this))
      )
  }

  private errorHandler(error: HttpErrorResponse) {
    if (error.status === 403) {
      this.router.navigate(['/login']).then(r => r);
    } else {
      this.errorService.handle(error.message);
    }
    return throwError(() => error.message);
  }

}
