import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { catchError, Observable, throwError } from "rxjs";
import { ISubject } from "../models/subject";
import { ErrorService } from "./error.service";

@Injectable({
  providedIn: 'root'
})
export class SubjectService {
  constructor(
    private http: HttpClient,
    private errorService: ErrorService) {
  }

  getAll(): Observable<ISubject[]> {
    return this.http.get<ISubject[]>('http://localhost:8080/school-timetable-generator-api/subjects')
      .pipe(
        catchError(this.errorHandler.bind(this))
      );
  }

  create(subject: ISubject): Observable<ISubject> {
    return this.http.post<ISubject>('http://localhost:8080/school-timetable-generator-api/subjects', subject)
      .pipe(
        catchError(this.errorHandler.bind(this))
      );
  }

  private errorHandler(error: HttpErrorResponse) {
    this.errorService.handle(error.message)
    return throwError(() => error.message)
  }

}
