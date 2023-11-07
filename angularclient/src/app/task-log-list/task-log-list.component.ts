import { Component, OnInit  } from '@angular/core';
import { TaskLog } from '../task-log';
import { TaskLogService } from '../task-log-service.service';

@Component({
  selector: 'app-task-log-list',
  templateUrl: './task-log-list.component.html',
  styleUrls: ['./task-log-list.component.css']
})

export class TaskLogListComponent implements OnInit {
  taskLogs: TaskLog[];
  constructor(private taskLogService: TaskLogService) {
    }

    ngOnInit() {
      this.taskLogService.findAll().subscribe(data => {
        this.taskLogs = data;
      });
    }
}
