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
    if(confirm("Вы уверены что хотите удалить этот тип задач?")) {
      this.taskTypeService.removeTaskType(id).subscribe(data=>{
        this.taskTypeService.findAll().subscribe(data => {
          this.tasksType = data;
        });
      });
    }
  };
}
