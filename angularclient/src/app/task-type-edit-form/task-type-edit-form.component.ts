import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TaskTypeService } from '../task-type-service.service';
import { TaskType } from '../task-type';

@Component({
  selector: 'app-task-type-edit-form',
  templateUrl: './task-type-edit-form.component.html',
  styleUrls: ['./task-type-edit-form.component.css']
})

export class TaskTypeEditFormComponent implements OnInit{
  taskTypeId: number;

  taskType: TaskType;

  constructor(
    private route: ActivatedRoute,
      private router: Router,
        private taskTypeService: TaskTypeService) {
    this.taskType = new TaskType();
  }

  ngOnInit(){
  this.route.params.subscribe(params => {
    this.taskTypeId = Number(params['id']);
    });
  }

  onSubmit() {
    this.taskTypeService.editTaskType(this.taskType.id, this.taskType.name, this.taskType.priority, this.taskType.timeReq).subscribe(result => this.gotoTaskTypeList());
  }

  gotoTaskTypeList() {
    this.router.navigate(['/tasksType']);
  }
}
