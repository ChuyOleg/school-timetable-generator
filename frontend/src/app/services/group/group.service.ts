import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { ErrorService } from "../error.service";
import { Constants } from "../../config/constants";
import { IGroup } from "../../models/group";
import { catchError, Observable, tap, throwError } from "rxjs";
import { Router } from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  constructor(
    private http: HttpClient,
    private router: Router,
    private errorService: ErrorService
  ) { }

  private defaultErrorMsg: string = 'Упс, щось пішло не так...';
  private groupIsInUsingErrorMsg: string = 'Дана група вже використовується або щось пішло не так...';

  private baseUrl = Constants.API_BASE_URL
  groups: IGroup[] = []
  groupToEdit: IGroup

  getAll(): Observable<IGroup[]> {
    return this.http.get<IGroup[]>(`${this.baseUrl}groups`)
      .pipe(
        tap(groups => this.groups = groups),
        catchError(error => this.errorHandler(error, this.defaultErrorMsg))
      )
  }

  getById(id: number): Observable<IGroup> {
    return this.http.get<IGroup>(`${this.baseUrl}groups/${id}`)
      .pipe(
        catchError(error => this.errorHandler(error, this.defaultErrorMsg))
      )
  }

  create(group: IGroup): Observable<IGroup> {
    return this.http.post<IGroup>(`${this.baseUrl}groups`, group)
      .pipe(
        tap(group => this.groups.push(group)),
        catchError(error => this.errorHandler(error, this.defaultErrorMsg))
      );
  }

  edit(group: IGroup): Observable<IGroup> {
    return this.http.put<IGroup>(`${this.baseUrl}groups`, group)
      .pipe(
        tap(() => this.groups = this.groups.filter(g => g.id != group.id)),
        tap(g => this.groups.push(g)),
        catchError(error => this.errorHandler(error, this.defaultErrorMsg))
      )
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}groups/${id}`)
      .pipe(
        tap(() => this.groups = this.groups.filter(g => g.id != id)),
        catchError(error => this.errorHandler(error, this.groupIsInUsingErrorMsg))
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
