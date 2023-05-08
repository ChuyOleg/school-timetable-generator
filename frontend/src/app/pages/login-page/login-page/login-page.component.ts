import { Component } from '@angular/core';
import { Router } from "@angular/router";
import { AuthService } from "../../../services/auth/auth.service";
import { CookieOptions, CookieService } from "ngx-cookie-service";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { HttpResponse } from "@angular/common/http";
import { Constants } from "../../../config/constants";

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html'
})
export class LoginPageComponent {

  submitButtonIsPressed: boolean = false;

  constructor(
    private router: Router,
    private authService: AuthService,
    private cookieService: CookieService
  ) {}

  form = new FormGroup({
    email: new FormControl<string>('', [
      Validators.required
    ]),
    password: new FormControl<string>('', [
      Validators.required
    ])
  })

  private extractTokenAndSetCookie(response: HttpResponse<any>) {
    const expires = new Date();
    expires.setHours(expires.getHours() + Constants.ACCESS_TOKEN_EXPIRATION_HOURS);
    const options: CookieOptions = { expires };
    const token = response.headers.get('Authorization') || '';
    this.cookieService.set(Constants.COOKIE_ACCESS_TOKEN_NAME, token, options);
  }

  async submit() {
    this.submitButtonIsPressed = true;
    if (this.form.valid) {
      this.authService.authenticate({
        email: this.form.value.email as string,
        password: this.form.value.password as string
      }).subscribe((response: HttpResponse<any>) => {
        this.extractTokenAndSetCookie(response)
        this.submitButtonIsPressed = false;
        this.router.navigate(['/subjects']).then(r => r);
      })
    }
  }

  get email() {
    return this.form.controls.email as FormControl;
  }

  get password() {
    return this.form.controls.password as FormControl;
  }

}
