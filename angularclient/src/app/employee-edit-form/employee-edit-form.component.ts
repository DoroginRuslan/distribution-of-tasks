import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeeService } from '../employee-service.service';
import { Employee } from '../employee';
import {Grade} from "../grade";
import {GradeService} from "../grade-service.service";

@Component({
  selector: 'app-employee-edit-form',
  templateUrl: './employee-edit-form.component.html',
  styleUrls: ['./employee-edit-form.component.css']
})
export class EmployeeEditFormComponent implements OnInit{
  employeeId: number;

  employee: Employee;
  grades: Grade[];

  constructor(
    private route: ActivatedRoute,
      private router: Router,
        private employeeService: EmployeeService,
    private gradeService: GradeService

  ) {
    this.employee = new Employee();
    this.employee.grade = new Grade();
  }

  ngOnInit(){
  this.route.params.subscribe(params => {
    this.employeeId = Number(params['id']);
    });
    this.employeeService.find(this.employeeId).subscribe(data => {
      this.employee = data;
    });
    this.gradeService.findAll().subscribe(data => {
      this.grades = data;
    });
  }

  onSubmit() {
    this.employeeService.editEmployee(this.employee).subscribe(result => this.gotoEmployeeList());
  }

  gotoEmployeeList() {
    this.router.navigate(['/employees']);
  }
}
