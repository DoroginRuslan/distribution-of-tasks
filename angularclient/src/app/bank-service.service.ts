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

    public removeBank(id) {
      console.log("remove bank");
      return ;
    }
    public editBank(id, address, registration, card_materials, last_card_issued, approved_application_num, card_issued_num) {
    let bank = new Bank();
    bank.id = id;
    bank.address = address;
    bank.registration = registration;
    bank.card_materials = card_materials;
    bank.last_card_issued = last_card_issued;
    bank.approved_application_num = approved_application_num;
    bank.card_issued_num = card_issued_num;
    return this.http.put<Bank>(this.banksUrl+'/${bank.id}', bank);
    }
}
