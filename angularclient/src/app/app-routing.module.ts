import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginFormComponent } from './login-form/login-form.component';
import { EmployeeListComponent } from './employee-list/employee-list.component';
import { EmployeeFormComponent } from './employee-form/employee-form.component';
import { BankListComponent } from './bank-list/bank-list.component';
import { TaskTypeListComponent } from './task-type-list/task-type-list.component';

const routes: Routes = [
  { path: 'employees', component: EmployeeListComponent },
  { path: 'addemployee', component: EmployeeFormComponent },
  { path: 'login', component: LoginFormComponent }
  { path: 'banks', component: BankListComponent },
  { path: 'tasksType', component: TaskTypeListComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
