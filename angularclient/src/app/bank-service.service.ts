import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Bank } from './bank';
import { Observable } from 'rxjs';

@Injectable()
export class BankService {

  private banksUrl: string;

  constructor(private http: HttpClient) {
    this.banksUrl = 'http://localhost:8080/getBanks';
  }

  public findAll(): Observable<Bank[]> {
    return this.http.get<Bank[]>(this.banksUrl);
  }

  public save(banks: Bank) {
    return this.http.post<Bank>(this.banksUrl, banks);
  }
}
