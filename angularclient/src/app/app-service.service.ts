import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse  } from '@angular/common/http';
import {Token} from "./token";
import {AuthData} from "./auth-data";
import {catchError, Observable} from "rxjs";

@Injectable()
export class AppService {

  authenticated = false;
  loginUrl: string;

  constructor(private http: HttpClient) {
    this.loginUrl = 'https://localhost:8080/api/auth/login';
  }

  authenticate(authData: AuthData): Observable<Token> {
    return this.http.post<Token>(this.loginUrl, authData)
      .pipe(catchError((error: HttpErrorResponse) => {
      if (error.error instanceof Error) {
        // A client-side or network error occurred. Handle it accordingly.
        console.error('An error occurred:', error.error.message);
      } else {
        // The backend returned an unsuccessful response code.
        // The response body may contain clues as to what went wrong,
        console.error(`Backend returned code ${error.status}, body was: ${error.error}`);
      }
      return new Observable<Token>()}));

  }
}
