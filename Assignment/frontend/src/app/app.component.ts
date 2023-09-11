import { Component } from '@angular/core';
import { Account } from './account';
import { AccountService } from './account.service';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Bank App';
  myAccount!: Account;
  constructor(private accountService: AccountService) {}

    ngOnInit() {
      this.myAccount = new Account();
      this.accountService.getMyDetails().subscribe(
        (data) => {
          this.myAccount = data;
          console.log('My Account Details:', this.myAccount);
        },
        (error) => {
          console.error('An error occurred:', error);
        }
      );
    }
}
