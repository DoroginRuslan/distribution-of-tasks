import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeeService } from '../employee-service.service';
import { Employee } from '../employee';

@Component({
  selector: 'app-employee-edit-form',
  templateUrl: './employee-edit-form.component.html',
  styleUrls: ['./employee-edit-form.component.css']
})
export class EmployeeEditFormComponent implements OnInit{
  employeeId: number;

  employee: Employee;

  constructor(
    private route: ActivatedRoute,
      private router: Router,
        private employeeService: EmployeeService) {
  }

  ngOnInit(){
  this.route.params.subscribe(params => {
    this.employeeId = Number(params['id']);
    });
  }

  onSubmit() {
    this.employeeService.editEmployee(this.employee.id, this.employee.fio, this.employee.address, this.employee.grade).subscribe(result => this.gotoEmployeeList());
  }

  gotoEmployeeList() {
    this.router.navigate(['/employees']);
  }
}
