import { Component, OnInit, ViewChild } from '@angular/core';
import { Employee } from '../employee';
import { EmployeeService } from '../employee-service.service';
import { TaskLog } from '../task-log';
import { TaskType } from '../task-type';
import { Bank } from '../bank';
import { TaskLogService } from '../task-log-service.service';
import {  AngularYandexMapsModule, YaConfig, YaGeocoderService, YaReadyEvent } from 'angular8-yandex-maps';
import { InputService } from '../input-service.service';
declare const ymaps: any;

//import MultiRoute = ymaps.multiRouter.MultiRoute;
@Component({
  selector: 'app-employee-tracker',
  templateUrl: './employee-tracker.component.html',
  styleUrls: ['./employee-tracker.component.css']
})
export class EmployeeTrackerComponent implements OnInit {
  @ViewChild('ymaps')
  currentEmployeeId: string;
  employees:  Employee[];
  taskLogs:   TaskLog[];
  banks:      Bank[];

  points: string[] =[];
  visible_points: string[] =[];
  isChecked: boolean[] =[];
  is_login:   number; // not login, 1 - manager, 2 - employee
  worker_id: number;
  map: ymaps.Map;
  geolocation :any;
  paramRoute: ymaps.control.IRoutePanelParameters;

  constructor(private employeeService: EmployeeService,
  private taskLogService: TaskLogService,
  private yaGeocoderService: YaGeocoderService,
  private input : InputService)
  {}
  ngOnInit() {
      ymaps.ready().done(() => this.createMap());
      this.input.data$.subscribe(data => {
        this.is_login = data;
        });

      // if (this.is_login == 1)
      //   {
          this.employeeService.findAll().subscribe(data => {
            this.employees = data;
            this.currentEmployeeId = this.employees[0].id;
            this.updateCurrentEmployeeLogs();
          });
        // }
      // else
      //   {
      //   this.input.worker_data$.subscribe(worker_data => {
      //     this.worker_id = worker_data;
      //     this.currentEmployeeId = this.worker_id.toString();
      //     console.log(this.currentEmployeeId);
      //     this.updateCurrentEmployeeLogs();
      //     });
      //   }
    //this.geolocation = ymaps.geolocation;


  }



  private createMap(): void {
    this.map = new ymaps.Map('map', {
      center: [45.051610, 38.979532],
      zoom: 14
    });
    if (this.is_login == 2) {
    ymaps.geolocation
      .get({
        provider: 'browser',
        mapStateAutoApply: true,
      })
      .then((result) => {
        result.geoObjects.options.set('preset', 'islands#blueCircleIcon');
        this.map.geoObjects.add(result.geoObjects);
      });
  }
    console.log(this.points);
  }
  updateRote()
  {
    this.map.geoObjects.removeAll();
    var multiRoute = new ymaps.multiRouter.MultiRoute({
      // Описание опорных точек мультимаршрута.
      referencePoints: this.points,
      params: {
        results: 2
      }
    }, {
      boundsAutoApply: true
    });
    this.map.geoObjects.add(multiRoute);
  }
    updateCurrentEmployeeLogs()
    {
      this.taskLogService.findCurrentForEmployee(this.currentEmployeeId).subscribe(data => {
        this.taskLogs = data;
        console.log(this.taskLogs);
        this.points = [];
        this.visible_points = [];
        this.isChecked = [];
        for (var i = 0; i < this.taskLogs.length; i++)
          this.isChecked[i] = this.taskLogs[i].completed.toString() == 'true'
        var i= 0;
        while(this.taskLogs[i]!=null && this.taskLogs[i].completed.toString()=="true")
          {
            i++;
          }
        if (this.taskLogs[i]==null)
          {
            this.points.push(this.taskLogs[i-1].bank.address);
            this.points.push(this.taskLogs[0].employee.address);
          }
        else if(i==0)
          {
            this.points.push(this.taskLogs[0].employee.address);
            this.points.push(this.taskLogs[0].bank.address);
          }
        else
          {
            this.points.push(this.taskLogs[i-1].bank.address);
            this.points.push(this.taskLogs[i].bank.address);
          }
        console.log(this.points);
        this.updateRote();
      })
    }
    checkValue(rowIndex){
      if (this.taskLogs[rowIndex].completed.toString() == 'true'){
        this.points.shift();
        if (this.taskLogs[rowIndex+1] != null) {
          console.log("11");
          this.points.push(this.taskLogs[rowIndex + 1].bank.address);
        }
        else {
          console.log("12");
          this.points.push(this.taskLogs[rowIndex].employee.address);
        }
      }
      else {
        this.points[1] = this.points[0];
        if (rowIndex-1>=0) {
          console.log("21");
          this.points[0] = this.taskLogs[rowIndex - 1].bank.address;
        }
        else {
          console.log("22");
          this.points[0] = this.taskLogs[rowIndex].employee.address;
        }
      }
      this.updateRote();
      console.log("AKJSNKS" + this.taskLogs);
      this.taskLogService.updateStatus(this.taskLogs[rowIndex].id, this.taskLogs[rowIndex]).subscribe(data => {
      });
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


