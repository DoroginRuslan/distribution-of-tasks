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
export class LoginFormComponent implements OnInit{


  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];

  authData: AuthData;

  constructor(private app: AppService, private http: HttpClient, private router: Router) {
     this.authData = new AuthData();
  }

  ngOnInit(): void {
    if (this.app.isLoggedIn()) {
      this.isLoggedIn = true;
      this.roles = this.app.getUser().roles;
      this.router.navigate(['employeeTracker']);
    }
  }

  login() {
    this.app.login(this.authData).subscribe({
      next: data => {
        this.app.saveUser(data);
        console.log(data);
        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.app.getUser().roles;
        this.router.navigate(['employeeTracker'])
      },
      error: err => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    });
  }
  reloadPage(): void {
    window.location.reload();
  }

}
