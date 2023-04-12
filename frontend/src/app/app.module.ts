import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SubjectBlockComponent } from "./components/subject/subject-block/subject-block.component";
import { HttpClientModule } from "@angular/common/http";
import { GlobalErrorComponent } from './components/global-error/global-error.component';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { FilterSubjectsPipe } from './pipes/filter-subjects.pipe';
import { SubjectModalComponent } from './components/subject/subject-modal/subject-modal.component';
import { CreateSubjectComponent } from './components/subject/create-subject/create-subject.component';
import { FocusDirective } from './directives/focus.directive';
import { MatIconModule } from "@angular/material/icon";
import { SubjectPageComponent } from './pages/subject-page/subject-page.component';
import { AboutPageComponent } from './pages/about-page/about-page.component';
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
import { LimitsBlockComponent } from './components/group-details/limits-block/limits-block.component';
import { FilterTeachersBySubjectPipe } from './pipes/filter-teachers-by-subject.pipe';
import { AddClassTeacherForPrimarySchoolPipe } from './pipes/add-class-teacher-for-primary-school.pipe';

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
    AboutPageComponent,
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
    LimitsBlockComponent,
    FilterTeachersBySubjectPipe,
    AddClassTeacherForPrimarySchoolPipe
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
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
