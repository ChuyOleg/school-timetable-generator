import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-shared-create-cancel-buttons',
  templateUrl: './create-cancel-buttons.component.html'
})
export class CreateCancelButtonsComponent {

  @Output() buttonClick = new EventEmitter<void>();

  handleClick() {
    this.buttonClick.emit();
  }
}
