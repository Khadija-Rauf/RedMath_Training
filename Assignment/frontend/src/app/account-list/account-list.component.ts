import { Component,OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {Account} from '../account';
import {AccountService} from '../account.service'
@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})
export class AccountListComponent implements OnInit{
accounts!: Account[];
constructor(private accountService: AccountService,
private router: Router){ }
ngOnInit():void{
  this.getAccounts();
}
private getAccounts() {
  this.accountService.getAccountList().subscribe(
    (data) => {
      this.accounts = data;
      if (!data || data.length === 0) {
        alert('No accounts found.');
      }
    },
    (error: any) => {
      console.error('An error occurred:', error);
      alert('You are not Logged in');
    }
  );
}
  updateAccount(id: number){
    this.router.navigate(['update-account', id]);
  }
  accountDetails(id: number){
      this.router.navigate(['account-details', id]);
    }
  deleteAccount(id: number){
    this.accountService.deleteAccount(id).subscribe(data => {
      console.log(data);
      this.getAccounts();
    });
  }
   getPasswordMask(password: string): string {
      return password ? password.replace(/./g, '*') : '';
    }
}
