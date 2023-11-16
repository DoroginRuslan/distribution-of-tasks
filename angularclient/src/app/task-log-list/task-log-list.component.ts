import { Component, OnInit  } from '@angular/core';
import { TaskLog } from '../task-log';
import { TaskLogService } from '../task-log-service.service';
import * as XLSX from 'xlsx';
import * as FileSaver from 'file-saver';

@Component({
  selector: 'app-task-log-list',
  templateUrl: './task-log-list.component.html',
  styleUrls: ['./task-log-list.component.css']
})


export class TaskLogListComponent implements OnInit {
  fileName= 'Отчёт.xlsx';
  taskLogs: TaskLog[];
  tasksFormed: boolean = false;
  loading: boolean = false;
  constructor(private taskLogService: TaskLogService) {
    }

    ngOnInit() {
      this.taskLogService.findAll().subscribe(data => {
        this.taskLogs = data;



        var currentDate = new Date();
        //console.log(currentDate.getDate().toString());
        //console.log(this.taskLogs[0].taskSetDate.substring(8,10));
        this.taskLogs = this.taskLogs.sort((a, b) => Number(b.taskSetDate.substring(8,10)) - Number(a.taskSetDate.substring(8,10)));
        this.taskLogs.forEach(log=>{
          var year = Number(log.taskSetDate.substring(0,4));
          var mnth = Number(log.taskSetDate.substring(5,7));
          var date = Number(log.taskSetDate.substring(8,10));
          //console.log(year,mnth,date);
          //console.log(currentDate.getFullYear(),currentDate.getMonth()+1,currentDate.getDate());
          if (currentDate.getFullYear() == year && currentDate.getDate()==date && currentDate.getMonth()+1==mnth)
            this.tasksFormed=true;
        })

      });
    }
  exportexcel(): void
  {
    /* table id is passed over here */
    let element = document.getElementById('excel-table');
    const ws: XLSX.WorkSheet =XLSX.utils.table_to_sheet(element);

    /* generate workbook and add the worksheet */
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');

    /* save to file */
    XLSX.writeFile(wb, this.fileName);

  }
  formTasks(){
    this.loading = true;
    this.tasksFormed = true;
    this.taskLogService.formTasks().subscribe(data => {
      this.loading = false;
      this.taskLogService.findAll().subscribe(data => {
        this.taskLogs = data;
      });
    });
  }
}
