import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Employee } from './employee';
import { Observable } from 'rxjs';

@Injectable()
export class EmployeeService {

  private employeesUrl: string;


  constructor(private http: HttpClient) {
    this.employeesUrl = 'http://localhost:8080/api/employees';
  }

  public findAll(): Observable<Employee[]> {
    return this.http.get<Employee[]>(this.employeesUrl);
  }

  public addEmployee(employee: Employee) {
    return this.http.post<Employee>(this.employeesUrl, employee);
  }

  public removeEmployee(id) {
    console.log("remove employee");
  }
  public editEmployee(employee: Employee) {
    return this.http.put<Employee>(this.employeesUrl+'/${employee.id}', employee);
  }
}
