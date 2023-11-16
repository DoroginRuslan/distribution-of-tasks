import { Component, OnInit  } from '@angular/core';
import { TaskLog } from '../task-log';
import { TaskLogService } from '../task-log-service.service';
import * as ExcelJS from 'exceljs'
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-task-log-list',
  templateUrl: './task-log-list.component.html',
  styleUrls: ['./task-log-list.component.css']
})


export class TaskLogListComponent implements OnInit {
  fileName= 'Отчёт';
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
    const workbook = new ExcelJS.Workbook();
    const worksheet = workbook.addWorksheet('Sheet1');

    // Add headers
    const headers = ["ФИО", "Задача", "Адрес", "Число: месяц-день", "Результат", "Доп. информация"];
    worksheet.addRow(headers);

    // Add data
    this.taskLogs.forEach((item) => {
      const fios = item.employee.fio;
      const types = item.taskType.name;
      const banks = item.bank.address;
      const times = item.taskSetDate.substring(5,10);
      const comm = item.commentary;
      let compl: string;
      if(item.completed)
      compl = "Выполнено";
      else
        compl = "Не выполнено";
      const arr: string[] = [];
      arr.push( fios, types, banks, times, compl, comm);
      worksheet.addRow(arr);
    });

    worksheet.getColumn(1).width = 40;
    worksheet.getColumn(2).width = 40;
    worksheet.getColumn(3).width = 60;
    worksheet.getColumn(4).width = 20;
    worksheet.getColumn(5).width = 20;
    worksheet.getColumn(6).width = 20;

    // Generate Excel file
    workbook.xlsx.writeBuffer().then((buffer: any) => {
      const blob = new Blob([buffer], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
      saveAs(blob, `${this.fileName}.xlsx`);
    });
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
