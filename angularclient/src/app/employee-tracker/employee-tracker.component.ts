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
  taskTypes:  TaskType[];
  banks:      Bank[];
  centerPoint: number[];
  points: string[] =[];
  isChecked: boolean[] =[];

  geocodeResult: YaGeocoderService["geocode"];
  is_login:   number; // not login, 1 - manager, 2 - employee
  worker_id: number;

  placemarkPropertiesHigh: ymaps.IPlacemarkProperties;
  optionMultiRoute: ymaps.multiRouter.IMultiRouteOptions;
  //paramMultiRoute: IMultiRouteParams;
  //paramMultiRoute: { params: ymaps.IMultiRouteParams } = {
  //    params: {
  //      avoidTrafficJams: false,
   //     routingMode: 'auto',
   //   },
   // };
  modelMultiRoute: ymaps.multiRouter.MultiRouteModel;
  //modelMultiRoute: {referencePoints: ymaps.multiRouter.MultiRouteModel};
  //pointMultiRoute:  ymaps.IMultiRouteReferencePoint[];

  constructor(private employeeService: EmployeeService,
  private taskLogService: TaskLogService,
  private yaGeocoderService: YaGeocoderService,
  private input : InputService) //{
  //private taskLogService: TaskLogService, private input : InputService)
  {
  }
  ngOnInit() {
  //this.paramMultiRoute = new IMultiRouteParams();
  //(,
      this.employeeService.findAll().subscribe(data => {
        this.employees = data;
        this.currentEmployeeId = this.employees[0].id;
      });
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
    //this.modelMultiRoute = new ymaps.multiRouter.MultiRouteModel(['Moscow','Tver'],{ routingMode: 'pedestrian' });
    this.taskLogService.findCurrentForEmployee(this.currentEmployeeId).subscribe(data => {
            this.taskLogs = data;
            console.log(this.taskLogs);

            this.points = [];
            this.isChecked = [];
            for (var i = 0; i < this.taskLogs.length; i++)
              this.isChecked[i] = this.taskLogs[i].completed.toString() == 'true'

            console.log(this.taskLogs);
            this.taskLogs.forEach( (element) => {
            this.points.push(element.bank.address);
            });

            this.placemarkPropertiesHigh =
                      {
                              hintContent: 'Высокий приоритет',
                              balloonContentBody: [
                                    '<address>',
                                    '<strong> Высокий приоритет </strong>',
                                    '<br/>',
                                    'Address: ', this.taskLogs[0].bank.address ,
                                    '<br/>',
                                    'For more information, see: <a href="https://company.yandex.com">https://company.yandex.com</a>',
                                    '</address>',
                                  ].join(''),
                            };
            this.optionMultiRoute =
            {
               viaIndexes: [2],
            };
            //this.modelMultiRoute = {  referencePoints: [this.taskLogs[2].employee.address, this.taskLogs[2].bank.address],
            //{
            //    avoidTrafficJams: true,
            //    viaIndexes: [1]
            //}
            //};

              //this.pointMultiRoute = [[45.061507, 38.979534], [45.161507, 38.979534], [45.191507, 38.979534]];
              //setReferencePoints(referencePoints[, viaIndexes[, clearRequests]])
            //this.modelMultiRoute.setReferencePoints(['Moscow','Tver']);


          });
         // var taskTmp     = new TaskLog();
         // var employeeTmp = new Employee();
         // var taskTypeTmp = new TaskType();
         // var bankTmp     = new Bank();
          //taskTmp.employee = employeeTmp;
         // taskTmp.taskType = taskTypeTmp;
          //taskTmp.bank = bankTmp;


    }
    checkValue(rowIndex, id, checked){
      this.taskLogs[rowIndex].completed = checked;
      this.taskLogService.updateStatus(id, this.taskLogs[rowIndex]).subscribe(data => {
              });
    }

    currentEmployeeChanged(event) {
        this.currentEmployeeId = event.target.value;
        this.updateCurrentEmployeeLogs();
      }


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


//   public checkResChanging(res: boolean, taskLogId: string){
//     this.taskLogService.updateResult(res,taskLogId);
//   }
}
