import { Injectable } from "@angular/core";

@Injectable()
export class Constants {

  public static readonly API_BASE_URL: string = 'http://localhost:8080/school-timetable-generator-api/';
  public static readonly COOKIE_ACCESS_TOKEN_NAME: string = 'school_timetable_token';
  public static readonly ACCESS_TOKEN_EXPIRATION_HOURS: number = 4;

}
