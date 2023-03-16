import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SubjectComponent } from "./components/subject/subject.component";
import { HttpClientModule } from "@angular/common/http";
import { GlobalErrorComponent } from './components/global-error/global-error.component';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { FilterSubjectsPipe } from './pipes/filter-subjects.pipe';
import { ModalComponent } from './components/modal/modal.component';
import { CreateSubjectComponent } from './components/create-subject/create-subject.component';
import { FocusDirective } from './directives/focus.directive';
import { MatIconModule } from "@angular/material/icon";

@NgModule({
  declarations: [
    AppComponent,
    SubjectComponent,
    GlobalErrorComponent,
    FilterSubjectsPipe,
    ModalComponent,
    CreateSubjectComponent,
    FocusDirective
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatIconModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
