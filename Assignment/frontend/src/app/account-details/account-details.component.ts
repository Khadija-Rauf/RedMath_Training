import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountService } from '../account.service';
import { Account } from '../account';
import { Balance } from '../balance';
import { HttpClient } from '@angular/common/http';
@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.css']
})
export class AccountDetailsComponent implements OnInit {
  id!: number;
  account!: Account;
  balance!: Balance;
  myAccount!: Account;
  isAdmin = false;
  isSameAccount = false;

  constructor(
    private accountService: AccountService,
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.account = new Account();
    this.balance = new Balance();
    this.accountService.getAccountById(this.id).subscribe(
      (data) => {
        this.account = data;
        this.ValidateUser();
        this.getBalance();
      },
      (error: any) => {
        console.error('An error occurred:', error);
        alert('You are not logged in');
      }
    );
  }

  ValidateUser() {
    this.myAccount = new Account();
    this.accountService.getMyDetails().subscribe(
      (data) => {
        this.myAccount = data;
        this.isAdmin = this.checkIfUserIsAdmin();
        this.isSameAccount = this.myAccount.id === this.account.id;
        console.log("Validation",this.account.id)
        this.redirectUser();
      },
      (error: any) => {
        console.error('An error occurred:', error);
        alert('You are not logged in');
      }
    );
  }

  checkIfUserIsAdmin(): boolean {
    return this.myAccount.role === 'ADMIN';
  }

  redirectUser() {
    if (this.isAdmin || this.isSameAccount) {
      console.log(this.account.id)
      this.router.navigate(['account-details', this.account.id]);
    }
     else {
      alert('You do not have permission to access this page!');
    }
  }
    private getBalance(){
        this.accountService.getBalanceList(this.account.id).subscribe(data =>{
          this.balance = data;
        },(error: any) => {
                console.error('An error occurred:', error);
                alert('No, Balance');
              });
        }

    getTransactionList(id: number) {
      if (this.isAdmin || this.isSameAccount) {
        this.router.navigate(['view-transactions', this.account.id]);
      } else {
        alert('You do not have permission to access this page!');
      }
    }
    createTransaction(id: number) {
          if (this.isAdmin || this.isSameAccount) {
            this.router.navigate(['create-transaction', this.account.id]);
          } else {
            alert('You do not have permission to access this page!');
          }
        }
}
