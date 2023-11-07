import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { EmployeeListComponent } from './employee-list/employee-list.component';
import { EmployeeFormComponent } from './employee-form/employee-form.component';
import { EmployeeService } from './employee-service.service';
import { LoginFormComponent } from './login-form/login-form.component';
import { AppService } from './app-service.service';
import { BankListComponent } from './bank-list/bank-list.component';
import { BankService } from './bank-service.service';
import { TaskTypeListComponent } from './task-type-list/task-type-list.component';
import { TaskTypeService } from './task-type-service.service';
import { BankFormComponent } from './bank-form/bank-form.component';

@NgModule({
  declarations: [
    AppComponent,
    EmployeeListComponent,
    EmployeeFormComponent,
    LoginFormComponent,
    BankListComponent,
    TaskTypeListComponent,
    BankFormComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    EmployeeService,
    AppService,
    BankService,
    TaskTypeService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
