import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Employee } from './employee';
import { Observable } from 'rxjs';

@Injectable()
export class EmployeeService {

  private employeesUrl: string;

  constructor(private http: HttpClient) {
    this.employeesUrl = 'http://localhost:8080/getEmployers';
  }

  public findAll(): Observable<Employee[]> {
    return this.http.get<Employee[]>(this.employeesUrl);
  }

  public save(employees: Employee) {
    return this.http.post<Employee>(this.employeesUrl, employees);
  }

  public removeEmployee(id) {
    console.log("remove employee");
    return ;
  }
  public editEmployee(id, fio, address, grade) {
    console.log("edit employee");
    return ;
  }
}
