import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { ErrorService } from "../error.service";
import { Constants } from "../../config/constants";
import { IRoom } from "../../models/room";
import { catchError, Observable, tap, throwError } from "rxjs";
import { Router } from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  constructor(
    private http: HttpClient,
    private router: Router,
    private errorService: ErrorService
  ) { }

  private defaultErrorMsg: string = 'Упс, щось пішло не так...';
  private roomIsInUsingErrorMsg: string = 'Дана кімната вже використовується або щось пішло не так...';

  private baseUrl = Constants.API_BASE_URL
  rooms: IRoom[] = []
  roomToEdit: IRoom

  getAll(): Observable<IRoom[]> {
    return this.http.get<IRoom[]>(`${this.baseUrl}rooms`)
      .pipe(
        tap(rooms => this.rooms = rooms),
        catchError(error => this.errorHandler(error, this.defaultErrorMsg))
      )
  }

  getById(id: number): Observable<IRoom> {
    return this.http.get<IRoom>(`${this.baseUrl}rooms/${id}`)
      .pipe(
        catchError(error => this.errorHandler(error, this.defaultErrorMsg))
      )
  }
  create(room: IRoom): Observable<IRoom> {
    return this.http.post<IRoom>(`${this.baseUrl}rooms`, room)
      .pipe(
        tap(room => this.rooms.push(room)),
        catchError(error => this.errorHandler(error, this.defaultErrorMsg))
      )
  }

  edit(room: IRoom): Observable<IRoom> {
    return this.http.put<IRoom>(`${this.baseUrl}rooms`, room)
      .pipe(
        tap(() => this.rooms = this.rooms.filter(r => r.id != room.id)),
        tap(room => this.rooms.push(room)),
        catchError(error => this.errorHandler(error, this.defaultErrorMsg))
      )
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}rooms/${id}`)
      .pipe(
        tap(() => this.rooms = this.rooms.filter(r => r.id != id)),
        catchError(error => this.errorHandler(error, this.roomIsInUsingErrorMsg))
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
