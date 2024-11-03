import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SubjectBlockComponent } from "./components/subject/subject-block/subject-block.component";
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";
import { GlobalErrorComponent } from './components/global-error/global-error.component';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { FilterSubjectsPipe } from './pipes/filter-subjects.pipe';
import { SubjectModalComponent } from './components/subject/subject-modal/subject-modal.component';
import { CreateSubjectComponent } from './components/subject/create-subject/create-subject.component';
import { FocusDirective } from './directives/focus.directive';
import { MatIconModule } from "@angular/material/icon";
import { SubjectPageComponent } from './pages/subject-page/subject-page.component';
import { NavigationComponent } from './components/navigation/navigation.component';
import { EditSubjectComponent } from './components/subject/edit-subject/edit-subject.component';
import { FooterComponent } from './components/footer/footer.component';
import { ConfirmComponent } from './components/dialogs/confirm/confirm.component';
import { MatDialogModule } from "@angular/material/dialog";
import { TeacherPageComponent } from './pages/teacher-page/teacher-page.component';
import { TeacherBlockComponent } from './components/teacher/teacher-block/teacher-block.component';
import { FilterTeachersPipe } from './pipes/filter-teachers.pipe';
import { CreateTeacherComponent } from './components/teacher/create-teacher/create-teacher.component';
import { EditTeacherComponent } from './components/teacher/edit-teacher/edit-teacher.component';
import { TeacherModalComponent } from './components/teacher/teacher-modal/teacher-modal.component';
import { MatInputModule } from "@angular/material/input";
import { MatSelectModule } from "@angular/material/select";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { SortSubjectsByNamePipe } from './pipes/sort-subjects-by-name.pipe';
import { SortTeachersByNamePipe } from './pipes/sort-teachers-by-name.pipe';
import { RoomPageComponent } from './pages/room-page/room-page.component';
import { FilterRoomsPipe } from './pipes/filter-rooms.pipe';
import { SortRoomsByNamePipe } from './pipes/sort-rooms-by-name.pipe';
import { RoomBlockComponent } from './components/room/room-block/room-block.component';
import { CreateRoomComponent } from './components/room/create-room/create-room.component';
import { RoomModalComponent } from './components/room/room-modal/room-modal.component';
import { EditRoomComponent } from './components/room/edit-room/edit-room.component';
import { GroupPageComponent } from './pages/group-page/group-page.component';
import { GroupBlockComponent } from './components/group/group-block/group-block.component';
import { CreateGroupComponent } from './components/group/create-group/create-group.component';
import { GroupModalComponent } from './components/group/group-modal/group-modal.component';
import { FilterGroupsPipe } from './pipes/filter-groups.pipe';
import { SortGroupsPipe } from './pipes/sort-groups.pipe';
import { EditGroupComponent } from './components/group/edit-group/edit-group.component';
import { GroupDetailsPageComponent } from './pages/group-details-page/group-details-page.component';
import { GroupLimitsBlockComponent } from './components/group-details/limits-block/group-limits-block.component';
import { FilterTeachersBySubjectPipe } from './pipes/filter-teachers-by-subject.pipe';
import { AddClassTeacherForPrimarySchoolPipe } from './pipes/add-class-teacher-for-primary-school.pipe';
import { RegisterPageComponent } from './pages/register-page/register-page/register-page.component';
import { LoginPageComponent } from './pages/login-page/login-page/login-page.component';
import { TokenInterceptorService } from "./services/token-interceptor.service";
import { TimetablePageComponent } from './pages/timetable-page/timetable-page.component';
import { InvalidWorkingLoadingBlockComponent } from './components/timetable/invalid-working-loading-block/invalid-working-loading-block.component';
import { GroupTimetableBlockComponent } from './components/timetable/group-timtable-block/group-timetable-block.component';
import { TeacherLimitsBlockComponent } from './components/teacher-details/limits-block/teacher-limits-block.component';
import { TeacherDetailsPageComponent } from './pages/teacher-details-page/teacher-details-page/teacher-details-page.component';
import { TranslateDayPipe } from './pipes/translate-day.pipe';
import { LoaderComponent } from "./components/shared/loader/loader.component";
import { CreateCancelButtonsComponent } from './components/shared/button/create-cancel-buttons/create-cancel-buttons.component';
import { GroupDetailsMnvkComponent } from './components/group-details/components/mnvk/group-details-mnvk.component';
import { GroupDetailsLessonFormComponent } from './components/group-details/components/lesson-form/group-details-lesson-form.component';
import { SortLessonLimitsBySubjectNamePipe } from './pipes/sort-lesson-limits-by-subject-name.pipe';

@NgModule({
  declarations: [
    AppComponent,
    SubjectBlockComponent,
    GlobalErrorComponent,
    FilterSubjectsPipe,
    SubjectModalComponent,
    CreateSubjectComponent,
    FocusDirective,
    SubjectPageComponent,
    NavigationComponent,
    EditSubjectComponent,
    FooterComponent,
    ConfirmComponent,
    TeacherPageComponent,
    TeacherBlockComponent,
    FilterTeachersPipe,
    CreateTeacherComponent,
    EditTeacherComponent,
    TeacherModalComponent,
    SortSubjectsByNamePipe,
    SortTeachersByNamePipe,
    RoomPageComponent,
    FilterRoomsPipe,
    SortRoomsByNamePipe,
    RoomBlockComponent,
    CreateRoomComponent,
    RoomModalComponent,
    EditRoomComponent,
    GroupPageComponent,
    GroupBlockComponent,
    CreateGroupComponent,
    GroupModalComponent,
    FilterGroupsPipe,
    SortGroupsPipe,
    EditGroupComponent,
    GroupDetailsPageComponent,
    GroupLimitsBlockComponent,
    FilterTeachersBySubjectPipe,
    AddClassTeacherForPrimarySchoolPipe,
    RegisterPageComponent,
    LoginPageComponent,
    TimetablePageComponent,
    InvalidWorkingLoadingBlockComponent,
    GroupTimetableBlockComponent,
    TeacherLimitsBlockComponent,
    TeacherDetailsPageComponent,
    TranslateDayPipe,
    LoaderComponent,
    CreateCancelButtonsComponent,
    GroupDetailsMnvkComponent,
    GroupDetailsLessonFormComponent,
    SortLessonLimitsBySubjectNamePipe,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatIconModule,
    MatDialogModule,
    MatInputModule,
    MatSelectModule,
    BrowserAnimationsModule
  ],
  providers: [
    TokenInterceptorService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
