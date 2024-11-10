import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { ErrorService } from "../error.service";
import { Constants } from "../../config/constants";
import { ITeacher } from "../../models/teacher/teacher";
import { catchError, Observable, tap, throwError } from "rxjs";
import { Router } from "@angular/router";
import { ITeacherProjection } from "../../models/projection/teacher-projection";

@Injectable({
  providedIn: 'root'
})
export class TeacherService {

  constructor(
    private http: HttpClient,
    private router: Router,
    private errorService: ErrorService
  ) { }

  private defaultErrorMsg: string = 'Упс, щось пішло не так...';
  private teacherIsInUsingErrorMsg: string = 'Даний вчитель вже використовується або щось пішло не так...';

  private baseUrl = Constants.API_BASE_URL;
  teachers: ITeacher[] = []
  freeClassTeachers: ITeacher[] = []
  teacherToEdit: ITeacher

  getAll(): Observable<ITeacher[]> {
    return this.http.get<ITeacher[]>(`${this.baseUrl}teachers`)
      .pipe(
        tap(teachers => this.teachers = teachers),
        catchError(error => this.errorHandler(error, this.defaultErrorMsg))
      )
  }

  getById(id: number): Observable<ITeacher> {
    return this.http.get<ITeacher>(`${this.baseUrl}teachers/${id}`)
      .pipe(
        catchError(error => this.errorHandler(error, this.defaultErrorMsg))
      )
  }

  getFreeClassTeachers(): Observable<ITeacher[]> {
    return this.http.get<ITeacher[]>(`${this.baseUrl}teachers/freeClassTeachers`)
      .pipe(
        tap(teachers => this.freeClassTeachers = teachers),
        catchError(error => this.errorHandler(error, this.defaultErrorMsg))
      )
  }

  getTeachersHours(): Observable<ITeacherProjection[]> {
    return this.http.get<ITeacherProjection[]>(`${this.baseUrl}teachers/actualHours`)
      .pipe(
        catchError(error => this.errorHandler(error, this.defaultErrorMsg))
      )
  }

  create(teacher: ITeacher): Observable<ITeacher> {
    return this.http.post<ITeacher>(`${this.baseUrl}teachers`, teacher)
      .pipe(
        tap(teacher => this.teachers.push(teacher)),
        catchError(error => this.errorHandler(error, this.defaultErrorMsg))
      );
  }

  edit(teacher: ITeacher): Observable<ITeacher> {
    return this.http.put<ITeacher>(`${this.baseUrl}teachers`, teacher)
      .pipe(
        tap(() => this.teachers = this.teachers.filter(t => t.id != teacher.id)),
        tap(t => this.teachers.push(t)),
        catchError(error => this.errorHandler(error, this.defaultErrorMsg))
      )
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}teachers/${id}`)
      .pipe(
        tap(() => this.teachers = this.teachers.filter(t => t.id != id)),
        catchError(error => this.errorHandler(error, this.teacherIsInUsingErrorMsg))
      )
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
