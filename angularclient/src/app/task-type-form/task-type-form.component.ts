import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TaskTypeService } from '../task-type-service.service';
import { TaskType } from '../task-type';

@Component({
  selector: 'app-task-type-form',
  templateUrl: './task-type-form.component.html',
  styleUrls: ['./task-type-form.component.css']
})

export class TaskTypeFormComponent {

  taskType: TaskType;

  constructor(
    private route: ActivatedRoute,
      private router: Router,
        private taskTypeService: TaskTypeService) {
    this.taskType = new TaskType();
  }

  onSubmit() {
    this.taskTypeService.save(this.taskType).subscribe(result => this.gotoTaskTypeList());
  }

  gotoTaskTypeList() {
    this.router.navigate(['/tasksType']);
  }
}
