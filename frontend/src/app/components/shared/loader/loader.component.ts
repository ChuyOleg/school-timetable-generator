import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-shared-loader',
  templateUrl: './loader.component.html',
})
export class LoaderComponent {
  @Input() isLoading: boolean = true;
}
