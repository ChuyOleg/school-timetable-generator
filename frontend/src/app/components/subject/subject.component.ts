import { Component, Input } from "@angular/core";
import { ISubject } from "../../models/subject";

@Component({
  selector: 'app-subject',
  templateUrl: './subject.component.html'
})
export class SubjectComponent {
  @Input() subject: ISubject
}
