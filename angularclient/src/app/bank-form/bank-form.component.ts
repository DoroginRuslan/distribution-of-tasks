import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BankService } from '../bank-service.service';
import { Bank } from '../bank';

@Component({
  selector: 'app-bank-form',
  templateUrl: './bank-form.component.html',
  styleUrls: ['./bank-form.component.css']
})
export class BankFormComponent {

  bank: Bank;

  constructor(
    private route: ActivatedRoute,
      private router: Router,
        private bankService: BankService) {
    this.bank = new Bank();
  }

  onSubmit() {
    this.bankService.save(this.bank).subscribe(result => this.gotoBankList());
  }

  gotoBankList() {
    this.router.navigate(['/banks']);
  }
}
