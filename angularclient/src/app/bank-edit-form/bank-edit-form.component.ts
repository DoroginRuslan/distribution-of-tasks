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
    this.bankService.editBank(this.bank.id, this.bank.address, this.bank.registration, this.bank.card_materials, this.bank.last_card_issued, this.bank.approved_application_num, this.bank.card_issued_num).subscribe(result => this.gotoBankList());
  }

  gotoBankList() {
    this.router.navigate(['/banks']);
  }
}
