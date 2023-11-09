import { Component } from '@angular/core';
import { InputService } from '../input-service.service';

@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.css']
})
export class InputComponent {
  constructor(private inputService: InputService) {
  }
  onManagerBtnClick() {
    this.inputService.changeValueOne();
  };

  onEmployeeBtnClick() {
    this.inputService.changeValueTwo();
  };
}
