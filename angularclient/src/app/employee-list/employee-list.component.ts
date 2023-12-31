import { Component, OnInit } from '@angular/core';
import { Employee } from '../employee';
import { EmployeeService } from '../employee-service.service';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {

  employees: Employee[];

  constructor(private employeeService: EmployeeService) {
  }

  ngOnInit() {
    this.employeeService.findAll().subscribe(data => {
      this.employees = data;
    });

  }
  removeEmployee(id) {
    if(confirm("Вы уверены что хотите удалить этого сотрудника?")) {
      this.employeeService.removeEmployee(id).subscribe(data=>{
        this.employeeService.findAll().subscribe(data => {
          this.employees = data;
        });
      });
    }
  };
}
