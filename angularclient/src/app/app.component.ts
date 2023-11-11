import { Component, OnInit } from '@angular/core';
import { AppService } from './app-service.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { finalize } from "rxjs/operators";
import { InputService } from './input-service.service';
//import * as $ from "jquery";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  title: string;
  is_login: number; // not login, 1 - manager, 2 - employee
  worker_id : number;

  constructor(private app: AppService, private http: HttpClient, private router: Router, private input : InputService) {
//         this.app.authenticate(undefined, undefined);
//         this.title = 'Spring Boot - Angular Application';
//         this.is_login = 0;
//         this.worker_id = -1;
//       }
//       logout() {
//         this.http.post('logout', {}).pipe(
//             finalize(() => {
//             this.app.authenticated = false;
//             this.is_login = 0;
//             this.router.navigateByUrl('/login');
//         })).subscribe();
      if (this.is_login != 1 && this.is_login != 2) {
          router.navigate(['input']);

      }
  }
  authenticated(){
    return this.app.authenticated;
  }
  ngOnInit() {
    this.input.data$.subscribe(data => {
      this.is_login = data;
    });
    this.input.worker_data$.subscribe(worker_data => {
          this.worker_id = worker_data;
        });
  }

  MenuButton(){
    $("#wrapper").toggleClass("toggled");
  }
}
