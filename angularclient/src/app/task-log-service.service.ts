import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TaskLog } from './task-log';
import { Observable } from 'rxjs';

@Injectable()
export class TaskLogService {

  private tasksLogUrl: string;

  constructor(private http: HttpClient) {
    this.tasksLogUrl = 'http://localhost:8080/getTasksLog';
  }

  public findAll(): Observable<TaskLog[]> {
    return this.http.get<TaskLog[]>(this.tasksLogUrl);
  }

}
