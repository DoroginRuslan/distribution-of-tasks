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
  currentEmployeeId: string;
  employees: Employee[];
  taskLogs: TaskLog[];

  parameters: ymaps.control.IRoutePanelParameters = {
      options: {
        allowSwitch: false,
        reverseGeocoding: true,
        types: { masstransit: true, pedestrian: true, taxi: true }
      },
      state: {
        type: 'masstransit',
        fromEnabled: false,
        from: 'Москва, Льва Толстого 16',
        toEnabled: true
      }
    };

  constructor(private employeeService: EmployeeService,
  private taskLogService: TaskLogService) {
  }
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
