import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BankService } from '../bank-service.service';
import { Bank } from '../bank';

@Component({
  selector: 'app-bank-edit-form',
  templateUrl: './bank-edit-form.component.html',
  styleUrls: ['./bank-edit-form.component.css']
})
export class BankEditFormComponent {
  bankId: number;

  bank: Bank;

  constructor(
    private route: ActivatedRoute,
      private router: Router,
        private bankService: BankService) {
    this.bank = new Bank();
  }

  ngOnInit(){
  this.route.params.subscribe(params => {
    this.bankId = Number(params['id']);
    });
  }

  onSubmit() {
//     this.bankService.editBank(this.bank.id, this.bank.address, this.bank.registrationDate, this.bank.materialsDelivered, this.bank.lastCardIssuanceDays, this.bank.approvedApplicationsNum, this.bank.issuanceCardsNum).subscribe(result => this.gotoBankList());
    this.bankService.editBank(this.bank).subscribe(result => this.gotoBankList());
  }

  gotoBankList() {
    this.router.navigate(['/banks']);
  }
}
