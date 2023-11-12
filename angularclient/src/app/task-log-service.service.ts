import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TaskLog } from './task-log';
import { Observable } from 'rxjs';
import {Employee} from "./employee";

@Injectable()
export class TaskLogService {

  private tasksLogUrl: string;
  private tasksLogForEmployeeUrl: string;
  private formTasksUrl: string;
  constructor(private http: HttpClient) {
    this.tasksLogUrl = 'https://distribution-of-tasks.onrender.com/api/task-logs';
    // fix later!!
    this.tasksLogForEmployeeUrl = 'https://distribution-of-tasks.onrender.com/api/task-logs/daily/employee';
    this.formTasksUrl = 'https://distribution-of-tasks.onrender.com/api/task-logs/daily/distribute';
  }

  public findAll(): Observable<TaskLog[]> {
    return this.http.get<TaskLog[]>(this.tasksLogUrl);
  }


  public findCurrentForEmployee(id):Observable<TaskLog[]> {
     return this.http.get<TaskLog[]>(this.tasksLogForEmployeeUrl+"/"+id);
  }

  public formTasks(){
    return this.http.get<TaskLog[]>(this.formTasksUrl);
  }

  public updateStatus(id:string, taskLog: TaskLog){
  console.log(this.tasksLogUrl+"/"+id, taskLog);
    return this.http.put<any>(this.tasksLogUrl+"/"+id, taskLog);
  }

}
