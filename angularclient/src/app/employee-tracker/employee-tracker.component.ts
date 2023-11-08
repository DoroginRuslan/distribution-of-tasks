import { Component, OnInit } from '@angular/core';
import { Employee } from '../employee';
import { EmployeeService } from '../employee-service.service';
import { TaskLog } from '../task-log';
import { TaskLogService } from '../task-log-service.service';

@Component({
  selector: 'app-employee-tracker',
  templateUrl: './employee-tracker.component.html',
  styleUrls: ['./employee-tracker.component.css']
})
export class EmployeeTrackerComponent implements OnInit {
  currentEmployee: Employee;
  employees: Employee[];
  taskLogs: TaskLog[];
  constructor(private employeeService: EmployeeService,
  private taskLogService: TaskLogService) {
  }
  ngOnInit() {
      this.employeeService.findAll().subscribe(data => {
        this.employees = data;
        this.currentEmployee = this.employees[0];
      });
      this.taskLogService.findCurrentForEmployee(this.currentEmployee.id).subscribe(data => {
        this.taskLogs = data;
      });
    }
}
