import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Router } from "@angular/router";
import { ErrorService } from "../error.service";
import { Constants } from "../../config/constants";
import { catchError, Observable, tap, throwError } from "rxjs";
import { ITimeSlot } from "../../models/time-slot";

@Injectable({
  providedIn: 'root'
})
export class TimeslotService {

  constructor(
    private http: HttpClient,
    private router: Router,
    private errorService: ErrorService
  ) { }

  private baseUrl = Constants.API_BASE_URL;
  timeslots: ITimeSlot[] = []

  getAll(): Observable<ITimeSlot[]> {
    return this.http.get<ITimeSlot[]>(`${this.baseUrl}timeslots`)
      .pipe(
        tap(timeslots => this.timeslots = timeslots),
        catchError(this.errorHandler.bind(this))
      );
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
