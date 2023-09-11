import { Component, OnInit } from '@angular/core';
import { Transaction } from '../transaction';
import { AccountService } from '../account.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-create-transaction',
  templateUrl: './create-transaction.component.html',
  styleUrls: ['./create-transaction.component.css']
})
export class CreateTransactionComponent implements OnInit {
  id!: number;
  transaction: Transaction = new Transaction();
  errorMessage: string = '';

  constructor(private accountService: AccountService, private router: Router,private route: ActivatedRoute) {}

  ngOnInit(): void {
  this.id = this.route.snapshot.params['id'];
  }

  saveTransaction() {
    this.accountService.createTransaction(this.transaction, this.id).subscribe(
      (data) => {
        console.log(data);
        this.gotoTransactionList();
      },
      (error) => {
        if (error.status === 409 || error.status === 302 || error.status === 'null') {
          this.errorMessage = 'Insufficient balance. Transaction cannot be created.';
          return;
        }
        else if (error.status === 404) {
           console.error('An error occurred:', error);
                     alert('You are not Logged in');
        }else {
          this.accountService.createTransaction(this.transaction, this.id).subscribe(
            (data) => {
              console.log(data);
              this.gotoTransactionList();
          });
          this.errorMessage = 'Transaction cannot be created!';
          console.log(error);
        }
      }
    );
  }
  gotoTransactionList() {
    this.router.navigate([`/account-details/${this.id}`]);
  }
  onSubmit(myForm: NgForm) {
    if (myForm.invalid) {
        console.log('Validation errors:', myForm.errors);
        return;
      }
    console.log(this.transaction);
    this.saveTransaction();
  }
}
