import { Component, OnInit  } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeeService } from '../employee-service.service';
import { GradeService } from '../grade-service.service';
import { Employee } from '../employee';
import { Grade } from '../grade';

@Component({
  selector: 'app-employee-form',
  templateUrl: './employee-form.component.html',
  styleUrls: ['./employee-form.component.css']
})
export class EmployeeFormComponent implements OnInit {

  employee: Employee;
  grades: Grade[];

  constructor(
    private route: ActivatedRoute,
      private router: Router,
        private employeeService: EmployeeService,
        private gradeService: GradeService) {
    this.employee = new Employee();
    this.employee.grade = new Grade();
  }

  ngOnInit() {
    this.gradeService.findAll().subscribe(data => {
      this.grades = data;
    });
  }

  onSubmit() {
    this.employeeService.addEmployee(this.employee).subscribe(result => this.gotoEmployeeList());
  }

  gotoEmployeeList() {
    this.router.navigate(['/employees']);
  }
}
