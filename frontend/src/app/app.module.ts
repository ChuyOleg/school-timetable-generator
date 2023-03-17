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
    ConfirmComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatIconModule,
    MatDialogModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
