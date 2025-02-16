import { Injectable } from '@angular/core';
import { CookieService } from "ngx-cookie-service";
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Observable } from "rxjs";
import { Constants } from "../config/constants";

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptorService implements HttpInterceptor {
  constructor(private cookieService: CookieService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.tokenIsNotRequired(req)) {
      return next.handle(req);
    }

    const token = this.cookieService.get(Constants.COOKIE_ACCESS_TOKEN_NAME);
    if (token) {
      req = req.clone({
        setHeaders: {
          Authorization: token
        }
      });
    }
    return next.handle(req);
  }

  private tokenIsNotRequired(req: HttpRequest<any>): boolean {
    return req.url.includes('register') ||
      req.url.includes('authenticate') ||
      req.url.includes('existsByEmail');
  }
}
