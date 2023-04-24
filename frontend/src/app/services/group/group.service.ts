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

  private baseUrl = Constants.API_BASE_URL
  groups: IGroup[] = []
  groupToEdit: IGroup

  getAll(): Observable<IGroup[]> {
    return this.http.get<IGroup[]>(`${this.baseUrl}groups`)
      .pipe(
        tap(groups => this.groups = groups),
        catchError(this.errorHandler.bind(this))
      )
  }

  getById(id: number): Observable<IGroup> {
    return this.http.get<IGroup>(`${this.baseUrl}groups/${id}`)
      .pipe(
        catchError(this.errorHandler.bind(this))
      )
  }

  create(group: IGroup): Observable<IGroup> {
    return this.http.post<IGroup>(`${this.baseUrl}groups`, group)
      .pipe(
        tap(group => this.groups.push(group)),
        catchError(this.errorHandler.bind(this))
      );
  }

  edit(group: IGroup): Observable<IGroup> {
    return this.http.put<IGroup>(`${this.baseUrl}groups`, group)
      .pipe(
        tap(() => this.groups = this.groups.filter(g => g.id != group.id)),
        tap(g => this.groups.push(g)),
        catchError(this.errorHandler.bind(this))
      )
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}groups/${id}`)
      .pipe(
        tap(() => this.groups = this.groups.filter(g => g.id != id)),
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
