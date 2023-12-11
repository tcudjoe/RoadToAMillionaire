import { Component } from '@angular/core';

@Component({
  selector: 'app-pricing',
  templateUrl: './pricing.component.html',
  styleUrls: ['./pricing.component.scss']
})
export class PricingComponent {
  selectedSwitch: string = 'monthly';

  switchInputChanged(value: string) {
    this.selectedSwitch = value;
  }
}
