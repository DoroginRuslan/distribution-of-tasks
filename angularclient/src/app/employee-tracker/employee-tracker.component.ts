import { Component, OnInit } from '@angular/core';
import { Employee } from '../employee';
import { EmployeeService } from '../employee-service.service';
import { TaskLog } from '../task-log';
import { TaskType } from '../task-type';
import { Bank } from '../bank';
import { TaskLogService } from '../task-log-service.service';

@Component({
  selector: 'app-employee-tracker',
  templateUrl: './employee-tracker.component.html',
  styleUrls: ['./employee-tracker.component.css']
})
export class EmployeeTrackerComponent implements OnInit {
  currentEmployeeId: string;
  employees:  Employee[];
  taskLogs:   TaskLog[];
  taskTypes:  TaskType[];
  banks:      Bank[];




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
          var taskTmp = new TaskLog();
          var employeeTmp = new Employee();
          var taskTypeTmp = new TaskType();
          var bankTmp = new Bank();
          taskTmp.employee = employeeTmp;
          taskTmp.taskType.name = taskTypeTmp.name;

    }
    currentEmployeeCHanged(event) {
        this.currentEmployeeId = event.target.value;
        this.updateCurrentEmployeeLogs();
      }
  placemarkPropertiesHigh: ymaps.IPlacemarkProperties = {
        hintContent: 'Высокий приоритет',
        balloonContentBody: [
              '<address>',
              '<strong> Высокий приоритет </strong>',
              '<br/>',
              'Address: ', ,
              '<br/>',
              'For more information, see: <a href="https://company.yandex.com">https://company.yandex.com</a>',
              '</address>',
            ].join(''),
      };

    placemarkPropertiesMid: ymaps.IPlacemarkProperties = {
         hintContent: 'Средний приоритет',
         balloonContent: 'Baloon content',
       };

    placemarkPropertiesLow: ymaps.IPlacemarkProperties = {
          hintContent: 'Низкий приоритет',
          balloonContent: 'Baloon content',
        };

      //https://cdn3.iconfinder.com/data/icons/web-ui-color/128/Marker_green-512.png    green
      //https://cdn4.iconfinder.com/data/icons/web-ui-color/128/Marker-512.png          blue
      placemarkHigh: ymaps.IPlacemarkOptions = {
        iconLayout: 'default#image',
        iconImageHref:
          'https://cdn4.iconfinder.com/data/icons/web-ui-color/128/Marker_red-512.png',
        iconImageSize: [32, 32],
      };

      placemarkMid: ymaps.IPlacemarkOptions = {
            iconLayout: 'default#image',
            iconImageHref:
              'https://cdn4.iconfinder.com/data/icons/web-ui-color/128/Marker-512.png',
            iconImageSize: [32, 32],
          };

      placemarkLow: ymaps.IPlacemarkOptions = {
                iconLayout: 'default#image',
                iconImageHref:
                  'https://cdn3.iconfinder.com/data/icons/web-ui-color/128/Marker_green-512.png',
                iconImageSize: [32, 32],
              };

    parameters: ymaps.control.IRoutePanelParameters = {
        options: {
          allowSwitch: false,
          reverseGeocoding: true,
          types: { masstransit: true, pedestrian: true, taxi: true }
        },
        state: {
          type: 'taxi',
          fromEnabled: true,
          toEnabled: true
        }
      };
}
