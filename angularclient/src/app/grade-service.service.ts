import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Grade } from './grade';
import { Observable } from 'rxjs';

@Injectable()
export class GradeService {
  private gradeUrl: string;

   constructor(private http: HttpClient) {
       this.gradeUrl = 'https://distribution-of-tasks.onrender.com/api/grades';
//        this.tasksLogForEmployeeUrl = 'http://localhost:8080/api/employees/1/tasks/current';
     }
   public findAll(): Observable<Grade[]> {
       return this.http.get<Grade[]>(this.gradeUrl);
   }
}
