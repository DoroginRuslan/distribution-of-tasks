import { Component, OnDestroy } from '@angular/core';
import { InputService } from '../input-service.service';

@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.css']
})
export class InputComponent {
  constructor(private inputService: InputService) {
  this.is_login = false;
  }

  is_login: boolean;

  onManagerBtnClick() {
    this.inputService.changeValueOne();
    this.is_login = true;
  };

  onEmployeeBtnClick() {
    this.inputService.changeValueTwo();
    this.is_login = true;
  };

}
