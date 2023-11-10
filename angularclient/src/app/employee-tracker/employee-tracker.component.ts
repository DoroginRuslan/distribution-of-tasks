import { Component, OnInit } from '@angular/core';
import { Employee } from '../employee';
import { EmployeeService } from '../employee-service.service';
import { TaskLog } from '../task-log';
import { TaskType } from '../task-type';
import { Bank } from '../bank';
import { TaskLogService } from '../task-log-service.service';
import {  AngularYandexMapsModule, YaConfig, YaGeocoderService, YaReadyEvent } from 'angular8-yandex-maps';
import { InputService } from '../input-service.service';


@Component({
  selector: 'app-employee-tracker',
  templateUrl: './employee-tracker.component.html',
  styleUrls: ['./employee-tracker.component.css']
})
export class EmployeeTrackerComponent implements OnInit {
  currentEmployeeId: string;
  employees:  Employee[];
  taskLogs:   TaskLog[];
  banks:      Bank[];
  points: string[] =[];
  isChecked: boolean[] =[];
  is_login:   number; // not login, 1 - manager, 2 - employee
  worker_id: number;
  constructor(private employeeService: EmployeeService,
  private taskLogService: TaskLogService,
  private yaGeocoderService: YaGeocoderService,
  private input : InputService)

  {
  }



  ngOnInit() {

       this.input.data$.subscribe(data => {
        this.is_login = data;
      });

      if (this.is_login == 1)
      {
        this.employeeService.findAll().subscribe(data => {
          this.employees = data;
          this.currentEmployeeId = this.employees[0].id;
          this.updateCurrentEmployeeLogs();
        });
      }
      else
      {
      this.input.worker_data$.subscribe(worker_data => {
        this.worker_id = worker_data;
        this.currentEmployeeId = this.worker_id.toString();
        this.updateCurrentEmployeeLogs();
      });

      }
    }

    updateCurrentEmployeeLogs()
    {
    this.taskLogService.findCurrentForEmployee(this.currentEmployeeId).subscribe(data => {
            this.taskLogs = data;
            console.log(this.taskLogs);

            this.points = [];
            this.isChecked = [];
            for (var i = 0; i < this.taskLogs.length; i++)
              this.isChecked[i] = this.taskLogs[i].completed.toString() == 'true'

            console.log(this.taskLogs);
            this.taskLogs.forEach( (element) => {
              this.points.shift();
              this.points.unshift(element.employee.address);
              this.points.push(element.bank.address);
            });
          })


    }
    checkValue(rowIndex){
      // this.taskLogs[rowIndex].completed = checked;
      this.taskLogService.updateStatus(this.taskLogs[rowIndex].id, this.taskLogs[rowIndex]).subscribe(data => {});
    }
  checkValueCommentary(rowIndex){
    // this.taskLogs[rowIndex].completed = checked;
    this.taskLogService.updateStatus(this.taskLogs[rowIndex].id, this.taskLogs[rowIndex]).subscribe(data => {});
  }

    currentEmployeeChanged(event) {
        this.currentEmployeeId = event.target.value;
        this.updateCurrentEmployeeLogs();

      }

}
