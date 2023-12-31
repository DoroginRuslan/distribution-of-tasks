import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Employee } from './employee';
import { Observable } from 'rxjs';
import { InputService } from './input-service.service';
import {Bank} from "./bank";

@Injectable()
export class EmployeeService {

  private employeesUrl: string;
  private updateRepoUrl: string;

  constructor(private http: HttpClient,public input: InputService) {
    this.employeesUrl = 'https://localhost:8080/api/employees';
    this.updateRepoUrl = 'https://localhost:8080/api/test/prepare-repo';
  }

  public find(id): Observable<Employee> {
    return this.http.get<Employee>(this.employeesUrl+'/'+id);
  }

  public updateRepo(){
    return this.http.get(this.updateRepoUrl);
  }


  public findAll(): Observable<Employee[]> {
    return this.http.get<Employee[]>(this.employeesUrl);
  }

  public addEmployee(employee: Employee) {
    return this.http.post<Employee>(this.employeesUrl, employee);
  }

  public removeEmployee(id:string) {
  console.log(this.employeesUrl+"/"+id);
    return this.http.delete<string>(this.employeesUrl+"/"+id);
  }

  public editEmployee(employee: Employee) {
    return this.http.put<Employee>(this.employeesUrl+"/"+employee.id, employee);
  }
}
