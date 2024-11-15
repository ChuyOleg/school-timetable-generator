import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Router } from "@angular/router";
import { ErrorService } from "../error.service";
import { Constants } from "../../config/constants";
import { IShiftsIntersection } from "../../models/shifts-intersection";
import { catchError, Observable, tap, throwError } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ShiftsIntersectionService {

  private DEFAULT_ERROR_MESSAGE = "Упс, щось пішло не так...";
  private BASE_URL = Constants.API_BASE_URL;

  constructor(
    private http: HttpClient,
    private router: Router,
    private errorService: ErrorService
  ) {}

  shiftsIntersections: IShiftsIntersection[];
  shiftIntersectionToEdit: IShiftsIntersection;

  getAll(): Observable<IShiftsIntersection[]> {
    return this.http.get<IShiftsIntersection[]>(`${(this.BASE_URL)}shifts`)
      .pipe(
        tap(shiftsIntersections => this.shiftsIntersections = shiftsIntersections),
        catchError(error => this.errorHandler(error, this.DEFAULT_ERROR_MESSAGE)) );
  }

  create(shiftsIntersection: IShiftsIntersection): Observable<IShiftsIntersection> {
    return this.http.post<IShiftsIntersection>(`${this.BASE_URL}shifts`, shiftsIntersection)
      .pipe(
        tap(shiftsIntersection => this.shiftsIntersections.push(shiftsIntersection)),
        catchError(error => this.errorHandler(error, this.DEFAULT_ERROR_MESSAGE)) );
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.BASE_URL}shifts/${id}`)
      .pipe(
        tap(() => this.shiftsIntersections = this.shiftsIntersections
          .filter(intersection => intersection.id != id)),
        catchError(error => this.errorHandler(error, this.DEFAULT_ERROR_MESSAGE)) );
  }

  private errorHandler(error: HttpErrorResponse, message: string) {
    if (error.status === 403) {
      this.router.navigate(['/login']).then(r => r);
    } else {
      this.errorService.handle(message);
    }
    return throwError(() => error.message);
  }
}
