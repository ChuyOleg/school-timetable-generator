import { Component } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { firstValueFrom } from "rxjs";
import { AuthService } from "../../../services/auth/auth.service";
import { HttpResponse } from "@angular/common/http";
import { CookieOptions, CookieService } from "ngx-cookie-service";
import { Constants } from "../../../config/constants";

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html'
})
export class RegisterPageComponent {

  submitButtonIsPressed: boolean = false;
  emailIsUnique: boolean = true;

  constructor(
    private router: Router,
    private authService: AuthService,
    private cookieService: CookieService
  ) {}

  form = new FormGroup({
    email: new FormControl<string>('', [
      Validators.required,
      Validators.email
    ]),
    password: new FormControl<string>('', [
      Validators.required,
      Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d!@#$%^&*()_+]{8,}$')
    ]),
    confirmPassword: new FormControl<string>('', [])
  }, {
    validators: this.passwordMatchValidator.bind(this)
  })

  async submit() {
    this.submitButtonIsPressed = true;
    if (this.form.valid) {
      this.emailIsUnique = !(await firstValueFrom(this.authService.existsByEmail(this.emailValue)));

      if (this.emailIsUnique) {
        this.authService.register({
          email: this.emailValue,
          password: this.form.value.password as string
        }).subscribe((response: HttpResponse<any>) => {
          this.extractTokenAndSetCookie(response)
          this.submitButtonIsPressed = false;
          this.router.navigate(['/subjects']).then(r => r);
        })
      }
    }
  }

  private extractTokenAndSetCookie(response: HttpResponse<any>) {
    const expires = new Date();
    expires.setHours(expires.getHours() + Constants.ACCESS_TOKEN_EXPIRATION_HOURS);
    const options: CookieOptions = { expires };
    const token = response.headers.get('Authorization') || '';
    this.cookieService.set(Constants.COOKIE_ACCESS_TOKEN_NAME, token, options);
  }

  get email() {
    return this.form.controls.email as FormControl;
  }

  get emailValue() {
    return this.form.value.email as string;
  }

  get password() {
    return this.form.controls.password as FormControl;
  }

  get confirmPassword() {
    return this.form.controls.confirmPassword as FormControl;
  }

  passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
    const password = control.get('password');
    const confirmPassword = control.get('confirmPassword');

    if (password?.value !== confirmPassword?.value) {
      confirmPassword?.setErrors({ passwordMatch: true });
      return { passwordMatch: true };
    } else {
      confirmPassword?.setErrors(null);
      return null;
    }
  }

}
