import { Component, OnInit } from '@angular/core';
import { AppService } from '../app-service.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import {AuthData} from "../auth-data";
import {Token} from "../token";

@Component({
  templateUrl: './login-form.component.html',
    selector: 'app-login-form',
    styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent {

  authData: AuthData;
  userToken: Token;

  constructor(private app: AppService, private http: HttpClient, private router: Router) {
    this.authData = new AuthData();
  }

  login() {
    this.app.authenticate(this.authData).subscribe(data => {
      if (data.accessToken=="")
      {
        console.log("bad data");
      }
      else
      {
        this.userToken = data;
        console.log(data);
        this.router.navigate(["employeeTracker"]);
      }
    })
  }

}


