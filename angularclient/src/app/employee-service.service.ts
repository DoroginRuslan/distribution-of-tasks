import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Employee } from './employee';
import { Observable } from 'rxjs';
import { InputService } from './input-service.service';
import {Bank} from "./bank";
import {AppService} from "./app-service.service";

@Injectable()
export class EmployeeService {

  private employeesUrl: string;
  private updateRepoUrl: string;

  constructor(private http: HttpClient,public input: InputService,  private app: AppService) {
    this.employeesUrl = 'https://localhost:8080/api/employees';
    this.updateRepoUrl = 'https://localhost:8080/api/test/prepare-repo';
  }

  public find(id): Observable<Employee> {
    return this.http.get<Employee>(this.employeesUrl+'/'+id);
  }

  public updateRepo(){
    return this.http.get(this.updateRepoUrl, this.app.requestOptions());
  }


  public findAll(): Observable<Employee[]> {
    console.log(this.employeesUrl, this.app.requestOptions());
    return this.http.get<Employee[]>(this.employeesUrl, this.app.requestOptions());
  }

  public addEmployee(employee: Employee) {
    return this.http.post<Employee>(this.employeesUrl, employee, this.app.requestOptions());
  }

  public removeEmployee(id:string) {
  console.log(this.employeesUrl+"/"+id);
    return this.http.delete<string>(this.employeesUrl+"/"+id, this.app.requestOptions());
  }

  public editEmployee(employee: Employee) {
    return this.http.put<Employee>(this.employeesUrl+"/"+employee.id, employee);
  }
}
