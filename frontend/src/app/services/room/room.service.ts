import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { ErrorService } from "../error.service";
import { Constants } from "../../config/constants";
import { IRoom } from "../../models/room";
import { catchError, Observable, tap, throwError } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  constructor(
    private http: HttpClient,
    private errorService: ErrorService
  ) { }

  private baseUrl = Constants.API_BASE_URL
  rooms: IRoom[] = []
  roomToEdit: IRoom

  getAll(): Observable<IRoom[]> {
    return this.http.get<IRoom[]>(`${this.baseUrl}rooms`)
      .pipe(
        tap(rooms => this.rooms = rooms),
        catchError(this.errorHandler.bind(this))
      )
  }

  create(room: IRoom): Observable<IRoom> {
    return this.http.post<IRoom>(`${this.baseUrl}rooms`, room)
      .pipe(
        tap(room => this.rooms.push(room)),
        catchError(this.errorHandler.bind(this))
      )
  }

  edit(room: IRoom): Observable<IRoom> {
    return this.http.put<IRoom>(`${this.baseUrl}rooms`, room)
      .pipe(
        tap(() => this.rooms = this.rooms.filter(r => r.id != room.id)),
        tap(room => this.rooms.push(room)),
        catchError(this.errorHandler.bind(this))
      )
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}rooms/${id}`)
      .pipe(
        tap(() => this.rooms = this.rooms.filter(r => r.id != id)),
        catchError(this.errorHandler.bind(this))
      )
  }

  private errorHandler(error: HttpErrorResponse) {
    this.errorService.handle(error.message)
    return throwError(() => error.message)
  }

}
