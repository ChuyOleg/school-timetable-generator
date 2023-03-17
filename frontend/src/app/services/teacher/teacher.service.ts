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
        tap(teacher => console.log(teacher)),
        tap(teachers => this.teachers = teachers),
        catchError(this.errorHandler.bind(this))
      )
  }

  private errorHandler(error: HttpErrorResponse) {
    this.errorService.handle(error.message);
    return throwError(() => error.message);
  }

}
