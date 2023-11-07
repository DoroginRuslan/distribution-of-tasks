import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TaskType } from './task-type';
import { Observable } from 'rxjs';

@Injectable()
export class TaskTypeService {

  private tasksTypeUrl: string;

  constructor(private http: HttpClient) {
    this.tasksTypeUrl = 'http://localhost:8080/getTasksType';
  }

  public findAll(): Observable<TaskType[]> {
    return this.http.get<TaskType[]>(this.tasksTypeUrl);
  }

  public save(tasksType: TaskType) {
    return this.http.post<TaskType>(this.tasksTypeUrl, tasksType);
  }

    public removeTaskType(id) {
      console.log("remove task type");
      return ;
    }
    public editTaskType(id) {
      console.log("edit task type");
      return ;
    }
}
