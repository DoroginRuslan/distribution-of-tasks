import { Injectable , OnInit} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Token} from "./token";
import {AuthData} from "./auth-data";
import {catchError, Observable} from "rxjs";

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root',
})
export class AppService implements OnInit{

  authenticated = false;
  loginUrl: string;
  authData: AuthData;
  userToken: Token;


  private roles: string[] = [];
  userIsLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  username?: string;

  constructor(private http: HttpClient) {
    this.loginUrl = 'https://localhost:8080/api/auth/login';
  }
  ngOnInit(): void {
    this.userIsLoggedIn = this.isLoggedIn();

    if (this.userIsLoggedIn) {
      const user = this.getUser();
      this.roles = user.roles;

      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR');

      this.username = user.username;

    }
  }

  requestOptions()
  {
    console.log(this.getUser().accessToken);
    return {headers: new HttpHeaders({ 'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.getUser().accessToken}`})};
  }

    login(authData: AuthData): Observable<any> {
      return this.http.post<Token>(
        this.loginUrl,
        authData,
        httpOptions
      );
    }
  logout(): void {
    this.logoutS().subscribe({
      next: res => {
        console.log(res);
        this.clean();

        window.location.reload();
      },
      error: err => {
        console.log(err);
      }
    });
  }
  logoutS(): Observable<any> {
    return this.http.post(this.loginUrl, { }, httpOptions);
  }

  clean(): void {
    window.sessionStorage.clear();
  }

  public saveUser(user: any): void {
    window.sessionStorage.removeItem(USER_KEY);
    console.log(user);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser(): any {
    const user = window.sessionStorage.getItem(USER_KEY);
    if (user) {
      return JSON.parse(user);
    }

    return {};
  }

  public isLoggedIn(): boolean {
    const user = window.sessionStorage.getItem(USER_KEY);
    if (user) {
      return true;
    }

    return false;
  }
}
