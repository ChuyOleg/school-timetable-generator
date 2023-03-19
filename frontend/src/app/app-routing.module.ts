import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SubjectPageComponent } from "./pages/subject-page/subject-page.component";
import { AboutPageComponent } from "./pages/about-page/about-page.component";
import { TeacherPageComponent } from "./pages/teacher-page/teacher-page.component";

const routes: Routes = [
  { path: 'subjects', component: SubjectPageComponent },
  { path: 'about', component: AboutPageComponent },
  { path: 'teachers', component: TeacherPageComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }