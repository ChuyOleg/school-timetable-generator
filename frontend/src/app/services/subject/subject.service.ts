import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { catchError, Observable, tap, throwError } from "rxjs";
import { ISubject } from "../../models/subject";
import { ErrorService } from "../error.service";

@Injectable({
  providedIn: 'root'
})
export class SubjectService {
  constructor(
    private http: HttpClient,
    private errorService: ErrorService) {
  }

  subjects: ISubject[] = []
  subjectToEdit: ISubject

  getAll(): Observable<ISubject[]> {
    return this.http.get<ISubject[]>('http://localhost:8080/school-timetable-generator-api/subjects')
      .pipe(
        tap(subjects => this.subjects = subjects),
        catchError(this.errorHandler.bind(this))
      );
  }

  create(subject: ISubject): Observable<ISubject> {
    return this.http.post<ISubject>('http://localhost:8080/school-timetable-generator-api/subjects', subject)
      .pipe(
        tap(subject => this.subjects.push(subject)),
        catchError(this.errorHandler.bind(this))
      );
  }

  edit(subject: ISubject): Observable<ISubject> {
    return this.http.put<ISubject>('http://localhost:8080/school-timetable-generator-api/subjects', subject)
      .pipe(
        tap(() => this.subjects = this.subjects.filter(subj => subj.id != subject.id)),
        tap(subj => this.subjects.push(subj)),
        catchError(this.errorHandler.bind(this))
      );
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`http://localhost:8080/school-timetable-generator-api/subjects/${id}`)
      .pipe(
        tap(() => this.subjects = this.subjects.filter(subject => subject.id != id)),
        catchError(this.errorHandler.bind(this))
      );
  }

  private errorHandler(error: HttpErrorResponse) {
    this.errorService.handle(error.message)
    return throwError(() => error.message)
  }

}
