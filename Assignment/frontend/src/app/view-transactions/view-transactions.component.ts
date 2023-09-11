import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Transaction } from '../transaction';
import { AccountService } from '../account.service';

@Component({
  selector: 'app-view-transactions',
  templateUrl: './view-transactions.component.html',
  styleUrls: ['./view-transactions.component.css']
})
export class ViewTransactionsComponent implements OnInit {
  id!: number;
  transactions!: Transaction[];
  balance: number = 0;

  constructor(
    private accountService: AccountService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.getValidTransactions();
  }

  private getValidTransactions() {
    this.accountService.getTransactionList(this.id).subscribe(data => {
      this.transactions = data;
    },(error: any) => {
             console.error('An error occurred:', error);
             alert('No, Transactions')
           });
  }

  private getBalance() {
    this.accountService.getBalanceList(this.id).subscribe(balanceData => {
      this.balance = balanceData.balance.amount;
      console.log(this.balance);
    });
  }
}
