import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { ErrorService } from "../error.service";
import { Constants } from "../../config/constants";
import { ITeacher } from "../../models/teacher";
import { catchError, Observable, tap, throwError } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class TeacherService {

  constructor(
    private http: HttpClient,
    private errorService: ErrorService
  ) { }

  private baseUrl = Constants.API_BASE_URL;
  teachers: ITeacher[] = []
  teacherToEdit: ITeacher

  getAll(): Observable<ITeacher[]> {
    return this.http.get<ITeacher[]>(`${this.baseUrl}teachers`)
      .pipe(
        tap(teachers => this.teachers = teachers),
        catchError(this.errorHandler.bind(this))
      )
  }

  create(teacher: ITeacher): Observable<ITeacher> {
    return this.http.post<ITeacher>(`${this.baseUrl}teachers`, teacher)
      .pipe(
        tap(teacher => this.teachers.push(teacher)),
        catchError(this.errorHandler.bind(this))
      );
  }

  edit(teacher: ITeacher): Observable<ITeacher> {
    return this.http.put<ITeacher>(`${this.baseUrl}teachers`, teacher)
      .pipe(
        tap(() => this.teachers = this.teachers.filter(t => t.id != teacher.id)),
        tap(t => this.teachers.push(t)),
        catchError(this.errorHandler.bind(this))
      )
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}teachers/${id}`)
      .pipe(
        tap(() => this.teachers = this.teachers.filter(t => t.id != id)),
        catchError(this.errorHandler.bind(this))
      )
  }

  private errorHandler(error: HttpErrorResponse) {
    this.errorService.handle(error.message);
    return throwError(() => error.message);
  }

}
