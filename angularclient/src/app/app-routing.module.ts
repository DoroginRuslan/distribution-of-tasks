import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginFormComponent } from './login-form/login-form.component';
import { EmployeeListComponent } from './employee-list/employee-list.component';
import { EmployeeFormComponent } from './employee-form/employee-form.component';
import { BankListComponent } from './bank-list/bank-list.component';
import { TaskTypeListComponent } from './task-type-list/task-type-list.component';
import { TaskLogListComponent } from './task-log-list/task-log-list.component';
import { EmployeeTrackerComponent } from './employee-tracker/employee-tracker.component';
import { TaskTypeFormComponent } from './task-type-form/task-type-form.component';
import { BankFormComponent } from './bank-form/bank-form.component';

const routes: Routes = [
  { path: 'taskLogList', component: TaskLogListComponent },
  { path: 'employees', component: EmployeeListComponent },
  { path: 'addemployee', component: EmployeeFormComponent },
  { path: 'login', component: LoginFormComponent },
  { path: 'banks', component: BankListComponent },
  { path: 'tasksType', component: TaskTypeListComponent },
  { path: 'employeeTracker', component: EmployeeTrackerComponent },
  { path: 'tasksType', component: TaskTypeListComponent },
  { path: 'addTaskType', component: TaskTypeFormComponent },
  { path: 'addbank', component: BankFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
