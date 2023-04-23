import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SubjectPageComponent } from "./pages/subject-page/subject-page.component";
import { AboutPageComponent } from "./pages/about-page/about-page.component";
import { TeacherPageComponent } from "./pages/teacher-page/teacher-page.component";
import { RoomPageComponent } from "./pages/room-page/room-page.component";
import { GroupPageComponent } from "./pages/group-page/group-page.component";
import { GroupDetailsPageComponent } from "./pages/group-details-page/group-details-page.component";
import { RegisterPageComponent } from "./pages/register-page/register-page/register-page.component";
import { LoginPageComponent } from "./pages/login-page/login-page/login-page.component";
import { TimetablePageComponent } from "./pages/timetable-page/timetable-page.component";

const routes: Routes = [
  { path: 'registration', component: RegisterPageComponent },
  { path: 'login', component: LoginPageComponent },
  { path: 'subjects', component: SubjectPageComponent },
  { path: 'about', component: AboutPageComponent },
  { path: 'teachers', component: TeacherPageComponent },
  { path: 'rooms', component: RoomPageComponent },
  { path: 'groups', component: GroupPageComponent },
  { path: 'groups/:id', component: GroupDetailsPageComponent },
  { path: 'timetable', component: TimetablePageComponent },
  { path: '**', redirectTo: '/about' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
