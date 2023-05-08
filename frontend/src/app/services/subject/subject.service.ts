import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { catchError, Observable, tap, throwError } from "rxjs";
import { ISubject } from "../../models/subject";
import { ErrorService } from "../error.service";
import { Constants } from "../../config/constants";
import { Router } from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class SubjectService {
  constructor(
    private http: HttpClient,
    private router: Router,
    private errorService: ErrorService,
  ) {}

  private defaultErrorMsg: string = 'Упс, щось пішло не так...';
  private subjectIsInUsingErrorMsg: string = 'Даний предмет вже використовується або щось пішло не так...';

  private baseUrl = Constants.API_BASE_URL;
  subjects: ISubject[] = []
  subjectToEdit: ISubject

  getAll(): Observable<ISubject[]> {
    return this.http.get<ISubject[]>(`${this.baseUrl}subjects`)
      .pipe(
        tap(subjects => this.subjects = subjects),
        catchError(error => this.errorHandler(error, this.defaultErrorMsg))
      );
  }

  create(subject: ISubject): Observable<ISubject> {
    return this.http.post<ISubject>(`${this.baseUrl}subjects`, subject)
      .pipe(
        tap(subject => this.subjects.push(subject)),
        catchError(error => this.errorHandler(error, this.defaultErrorMsg))
      );
  }

  edit(subject: ISubject): Observable<ISubject> {
    return this.http.put<ISubject>(`${this.baseUrl}subjects`, subject)
      .pipe(
        tap(() => this.subjects = this.subjects.filter(subj => subj.id != subject.id)),
        tap(subj => this.subjects.push(subj)),
        catchError(error => this.errorHandler(error, this.defaultErrorMsg))
      );
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}subjects/${id}`)
      .pipe(
        tap(() => this.subjects = this.subjects.filter(subject => subject.id != id)),
        catchError(error => this.errorHandler(error, this.subjectIsInUsingErrorMsg))
      );
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
