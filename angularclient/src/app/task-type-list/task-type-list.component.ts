import { Component } from '@angular/core';
import { TaskType } from '../task-type';
import { TaskTypeService } from '../task-type-service.service';

@Component({
  selector: 'app-taskType-list',
  templateUrl: './task-type-list.component.html',
  styleUrls: ['./task-type-list.component.css']
})

export class TaskTypeListComponent{

  tasksType: TaskType[];

  constructor(private taskTypeService: TaskTypeService) {
  }

  ngOnInit() {
    this.taskTypeService.findAll().subscribe(data => {
      this.tasksType = data;
    });
  }
  removeTaskType(id) {
    this.taskTypeService.removeTaskType(id);
  };
  editTaskType(id) {
    this.taskTypeService.editTaskType(id, "af", "af", "af");
  };
}
