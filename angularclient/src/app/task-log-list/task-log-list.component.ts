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
  tasksFormed: boolean = false;
  constructor(private taskLogService: TaskLogService) {
    }

    ngOnInit() {
      this.taskLogService.findAll().subscribe(data => {
        this.taskLogs = data;
        var currentDate = new Date();
        this.taskLogs.forEach(log=>{
          var year = Number(log.taskSetDate.substring(0,4));
          var mnth = Number(log.taskSetDate.substring(5,7));
          var date = Number(log.taskSetDate.substring(8,10));
          console.log(year,mnth,date);
          console.log(currentDate.getFullYear(),currentDate.getMonth()+1,currentDate.getDate());
          if (currentDate.getFullYear() == year && currentDate.getDate()==date && currentDate.getMonth()+1==mnth)
            this.tasksFormed=true;
        })

      });
    }
  formTasks(){
    this.taskLogService.formTasks().subscribe(data => {
      this.taskLogService.findAll().subscribe(data => {
        this.taskLogs = data;
      });
    });
  }
}
