import { Injectable , OnInit} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TaskLog } from './task-log';
import { Observable } from 'rxjs';
import {Employee} from "./employee";
import {AppService} from "./app-service.service";

@Injectable()
export class TaskLogService {

  private tasksLogUrl: string;
  private tasksLogForEmployeeUrl: string;
  private formTasksUrl: string;
  constructor(private http: HttpClient, private app: AppService) {
    this.tasksLogUrl = 'https://localhost:8080/api/task-logs';
    // fix later!!
    this.tasksLogForEmployeeUrl = 'https://localhost:8080/api/task-logs/daily/employee';
    this.formTasksUrl = 'https://localhost:8080/api/task-logs/daily/distribute';
  }


  public findAll(): Observable<TaskLog[]> {
    return this.http.get<TaskLog[]>(this.tasksLogUrl, this.app.requestOptions());
  }


  public findCurrentForEmployee(id):Observable<TaskLog[]> {
     return this.http.get<TaskLog[]>(this.tasksLogForEmployeeUrl+"/"+id, this.app.requestOptions());
  }

  public formTasks(){
    return this.http.get<TaskLog[]>(this.formTasksUrl, this.app.requestOptions());
  }

  public updateStatus(id:string, taskLog: TaskLog){
  console.log(this.tasksLogUrl+"/"+id, taskLog);
    return this.http.put<any>(this.tasksLogUrl+"/"+id, taskLog, this.app.requestOptions());
  }

}
