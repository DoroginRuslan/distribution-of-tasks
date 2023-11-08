import { Component } from '@angular/core';
import { Bank } from '../bank';
import { BankService } from '../bank-service.service';

@Component({
  selector: 'app-bank-list',
  templateUrl: './bank-list.component.html',
  styleUrls: ['./bank-list.component.css']
})

export class BankListComponent{

  banks: Bank[];

  constructor(private bankService: BankService) {
  }

  ngOnInit() {
    this.bankService.findAll().subscribe(data => {
      this.banks = data;
    });
  }
  removeBank(id) {
    this.bankService.removeBank(id);
  };

}
