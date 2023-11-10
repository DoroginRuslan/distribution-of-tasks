import { Component, OnInit } from '@angular/core';
import { AppService } from '../app-service.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  templateUrl: './login-form.component.html',
    selector: 'app-login-form',
    styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent {

  credentials = {username: '', password: ''};

  constructor(private app: AppService, private http: HttpClient, private router: Router) {
  }

  login() {
    this.app.authenticate(this.credentials, () => {
        this.router.navigateByUrl('/');
    });
    return false;
  }

}


