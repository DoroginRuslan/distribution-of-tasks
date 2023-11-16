import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Bank } from './bank';
import { Observable } from 'rxjs';
import {AppService} from "./app-service.service";

@Injectable()
export class BankService {

  private banksUrl: string;

  constructor(private http: HttpClient,  private app: AppService) {
    this.banksUrl = 'https://localhost:8080/api/banks';
  }

  public findAll(): Observable<Bank[]> {
    return this.http.get<Bank[]>(this.banksUrl, this.app.requestOptions());
  }

  public find(id): Observable<Bank> {
    return this.http.get<Bank>(this.banksUrl+'/'+id, this.app.requestOptions());
  }

  public addBank(bank: Bank) {
    return this.http.post<Bank>(this.banksUrl, bank, this.app.requestOptions());
  }

  public removeBank(id){
    return this.http.delete(this.banksUrl+'/'+id, this.app.requestOptions());
  }

  public editBank(bank: Bank) {
    return this.http.put<Bank>(this.banksUrl+'/'+ bank.id, bank, this.app.requestOptions());
    }
}
