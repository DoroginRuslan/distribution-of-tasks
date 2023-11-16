import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Bank } from './bank';
import { Observable } from 'rxjs';

@Injectable()
export class BankService {

  private banksUrl: string;

  constructor(private http: HttpClient) {
    this.banksUrl = 'https://localhost:8080/api/banks';
  }

  public findAll(): Observable<Bank[]> {
    return this.http.get<Bank[]>(this.banksUrl);
  }

  public find(id): Observable<Bank> {
    return this.http.get<Bank>(this.banksUrl+'/'+id);
  }

  public addBank(bank: Bank) {
    console.log(124);
    return this.http.post<Bank>(this.banksUrl, bank);
  }

  public removeBank(id){
    return this.http.delete(this.banksUrl+'/'+id);
  }

  public editBank(bank: Bank) {
    return this.http.put<Bank>(this.banksUrl+'/'+ bank.id, bank);
    }
}
