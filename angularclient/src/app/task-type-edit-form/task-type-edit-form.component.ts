import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TaskTypeService } from '../task-type-service.service';
import { TaskType } from '../task-type';
import { GradeService } from '../grade-service.service';
import { Grade } from '../grade';

@Component({
  selector: 'app-task-type-edit-form',
  templateUrl: './task-type-edit-form.component.html',
  styleUrls: ['./task-type-edit-form.component.css']
})

export class TaskTypeEditFormComponent implements OnInit{
  taskTypeId: number;

  taskType: TaskType;
  grades: Grade[];

  constructor(
    private route: ActivatedRoute,
      private router: Router,
        private taskTypeService: TaskTypeService,
        private gradeService: GradeService) {
    this.taskType = new TaskType();
    this.taskType.grade = new Grade();
  }

  ngOnInit(){
  this.route.params.subscribe(params => {
    this.taskTypeId = Number(params['id']);
    });
    this.gradeService.findAll().subscribe(data => {
            this.grades = data;
    });
  }

  onSubmit() {
    this.taskTypeService.editTaskType(this.taskType).subscribe(result => this.gotoTaskTypeList());
  }

  gotoTaskTypeList() {
    this.router.navigate(['/tasksType']);
  }
}
