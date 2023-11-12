import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TaskType } from './task-type';
import { Observable } from 'rxjs';
import {TaskLog} from "./task-log";

@Injectable()
export class TaskTypeService {

  private tasksTypeUrl: string;

  constructor(private http: HttpClient) {
    this.tasksTypeUrl = 'http://158.160.113.41:8080/api/task-types';
  }

  public findAll(): Observable<TaskType[]> {
    return this.http.get<TaskType[]>(this.tasksTypeUrl);
  }

  public find(id): Observable<TaskType> {
    return this.http.get<TaskType>(this.tasksTypeUrl+'/'+id);
  }

  public save(tasksType: TaskType) {
    return this.http.post<TaskType>(this.tasksTypeUrl, tasksType);
  }

  public removeTaskType(id) {
  console.log(this.tasksTypeUrl+"/"+id);
    return this.http.delete(this.tasksTypeUrl+'/'+id);
  }
  public editTaskType(taskType: TaskType) {
    return this.http.put<TaskType>(this.tasksTypeUrl+'/' + taskType.id, taskType);
  }
}
