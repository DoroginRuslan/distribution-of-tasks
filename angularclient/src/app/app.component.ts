import { Component } from '@angular/core';
import { AppService } from './app-service.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { finalize } from "rxjs/operators";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title: string;
  is_login: number; // not login, 1 - manager, 2 - employee

  constructor(private app: AppService, private http: HttpClient, private router: Router) {
        this.app.authenticate(undefined, undefined);
        this.title = 'Spring Boot - Angular Application';
        this.is_login = 2;
      }
      logout() {
        this.http.post('logout', {}).pipe(
            finalize(() => {
            this.app.authenticated = false;
            this.is_login = 0;
            this.router.navigateByUrl('/login');
        })).subscribe();
      }
  authenticated(){
    return this.app.authenticated;
  }
}
