import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Bank } from './bank';
import { Observable } from 'rxjs';

@Injectable()
export class BankService {

  private banksUrl: string;

  constructor(private http: HttpClient) {
    this.banksUrl = 'http://localhost:8080/api/banks';
  }

  public findAll(): Observable<Bank[]> {
    return this.http.get<Bank[]>(this.banksUrl);
  }

  public addBank(bank: Bank) {
    return this.http.post<Bank>(this.banksUrl, bank);
  }

    public removeBank(id) {
      console.log("remove bank");
      return ;
    }
    public editBank(id) {
      console.log("edit bank");
      return ;
    }
}
