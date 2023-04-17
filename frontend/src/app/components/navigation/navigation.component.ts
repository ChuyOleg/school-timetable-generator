import { Component } from '@angular/core';
import { CookieService } from "ngx-cookie-service";
import { Router } from "@angular/router";
import { Constants } from "../../config/constants";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html'
})
export class NavigationComponent {

  constructor(
    private router: Router,
    private cookieService: CookieService
  ) {}

  logout(): void {
    this.cookieService.delete(Constants.COOKIE_ACCESS_TOKEN_NAME);
    this.router.navigate(['/login']).then(r => r);
  }

}
