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
import { TaskLogListComponent } from './task-log-list/task-log-list.component';
import { TaskLogService } from './task-log-service.service';
import { EmployeeTrackerComponent } from './employee-tracker/employee-tracker.component';
import { TaskTypeFormComponent } from './task-type-form/task-type-form.component';
import { GradeService } from './grade-service.service';
import { AngularYandexMapsModule, YaConfig  } from 'angular8-yandex-maps';

const mapConfig: YaConfig = {
  apikey: '61f5780b-ee71-47a9-93a5-6ac5fd5262e2',
  lang: 'ru_RU',
};
import { EmployeeEditFormComponent } from './employee-edit-form/employee-edit-form.component';
import { TaskTypeEditFormComponent } from './task-type-edit-form/task-type-edit-form.component';
import { BankEditFormComponent } from './bank-edit-form/bank-edit-form.component';

@NgModule({
  declarations: [
    AppComponent,
    EmployeeListComponent,
    EmployeeFormComponent,
    LoginFormComponent,
    BankListComponent,
    TaskTypeListComponent,
    BankFormComponent,
    TaskLogListComponent,
    EmployeeTrackerComponent,
    TaskTypeFormComponent,
    BankFormComponent,
    EmployeeEditFormComponent,
    TaskTypeEditFormComponent,
    BankEditFormComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    AngularYandexMapsModule.forRoot(mapConfig)
  ],
  providers: [
    EmployeeService,
    AppService,
    BankService,
    TaskTypeService,
    TaskLogService,
    GradeService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
