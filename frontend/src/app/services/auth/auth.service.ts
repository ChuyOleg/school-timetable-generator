import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpParams, HttpResponse } from "@angular/common/http";
import { ErrorService } from "../error.service";
import { catchError, Observable, throwError } from "rxjs";
import { IUserRegister } from "../../models/auth/user-register";
import { Constants } from "../../config/constants";
import { IUserAuth } from "../../models/auth/user-auth";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient,
    private errorService: ErrorService
  ) { }

  private baseUrl = Constants.API_BASE_URL
  private AUTH_URL_PREFIX = 'v1/auth/';

  register(userRegister: IUserRegister): Observable<HttpResponse<any>> {
    return this.http.post<HttpResponse<any>>(`${this.baseUrl + this.AUTH_URL_PREFIX}register`, userRegister, { observe: 'response' })
      .pipe(
        catchError(error => this.errorHandler(error, 'Registration error'))
      )
  }

  authenticate(userAuth: IUserAuth): Observable<HttpResponse<any>> {
    return this.http.post<HttpResponse<any>>(`${this.baseUrl + this.AUTH_URL_PREFIX}authenticate`, userAuth, { observe: 'response' })
      .pipe(
        catchError(error => this.errorHandler(error, 'Invalid email or password. Please try again.'))
      )
  }

  existsByEmail(email: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.baseUrl + this.AUTH_URL_PREFIX}existsByEmail`, {
      params: new HttpParams().set('email', email)
    }).pipe(
      catchError(error => this.errorHandler(error, 'User with this email already exists'))
    )
  }

  private errorHandler(error: HttpErrorResponse, customErrorMessage: string) {
    this.errorService.handle(customErrorMessage);
    return throwError(() => error.message);
  }

}
