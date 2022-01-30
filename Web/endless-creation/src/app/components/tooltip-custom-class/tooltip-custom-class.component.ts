import { Component, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'link-tooltip',
  templateUrl: './tooltip-custom-class.component.html',
  encapsulation: ViewEncapsulation.None,
  styleUrls: ['./tooltip-custom-class.component.scss'],
  styles: [`
    .my-custom-class .tooltip-inner {
      background-color: darkgreen;
      font-size: 100%;
      width:100%;
      height:100%;

    }
    .my-custom-class .arrow::before {
      border-top-color: darkgreen;
    }
  `]
})
export class TooltipCustomClassComponent {
}
