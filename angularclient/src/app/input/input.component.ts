import { Component, OnDestroy } from '@angular/core';
import { InputService } from '../input-service.service';
import { Employee } from '../employee';
import { EmployeeService } from '../employee-service.service';
import { TaskLog } from '../task-log';
import { TaskLogService } from '../task-log-service.service';

@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.css']
})
export class InputComponent {
  constructor(private inputService: InputService,private employeeService: EmployeeService, private taskLogService: TaskLogService) {
  this.is_login = false;
  }

  is_login: boolean;
  employees: Employee[];
  currentEmployeeId: string;
  taskLogs: TaskLog[];

  onManagerBtnClick() {
    this.inputService.changeValueOne();
    this.is_login = true;
    this.inputService.changeWorkerId(Number(this.currentEmployeeId));
  };

  onEmployeeBtnClick() {
    this.inputService.changeValueTwo();
    this.is_login = true;
    this.inputService.changeWorkerId(Number(this.currentEmployeeId));
  };

  ngOnInit() {
      this.employeeService.findAll().subscribe(data => {
        this.employees = data;
        this.currentEmployeeId = this.employees[0].id;
      });
      this.updateCurrentEmployeeLogs();
    }
    updateCurrentEmployeeLogs()
    {
    this.taskLogService.findCurrentForEmployee(this.currentEmployeeId).subscribe(data => {
            this.taskLogs = data;
          });
    }
    currentEmployeeCHanged(event) {
        this.currentEmployeeId = event.target.value;
        this.updateCurrentEmployeeLogs();
      }
}
