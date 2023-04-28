import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Router } from "@angular/router";
import { ErrorService } from "../error.service";
import { Constants } from "../../config/constants";
import { catchError, Observable, throwError } from "rxjs";
import { ITimetable } from "../../models/timetable";

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

  generate(): Observable<ITimetable> {
    return this.http.get<ITimetable>(`${this.baseUrl}timetables/generate`)
      .pipe(
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
