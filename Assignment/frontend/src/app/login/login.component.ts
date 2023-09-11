import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { AccountService } from '../account.service';
import { Account } from '../account';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user = {
    username: '',
    password: '',
  };
  account!: Account;

  constructor(private authService: AuthService, private accountService: AccountService, private router: Router) {}

  ngOnInit(): void {
    this.account = new Account();
  }
  async loginUser() {
    try {
      const result = await this.authService.login(this.user).toPromise();
      this.getAccountDetails(this.user.username);
      console.log(this.account.role)
    } catch (error) {
      const result = await this.authService.login(this.user).toPromise();
      this.getAccountDetails(this.user.username);
      console.error('An error occurred:', error);
      alert('Error: Unable to complete the request. Please try again later.');
    }
  }
  getAccountDetails(name: string){
      this.accountService.getDetails(name).subscribe(
        (data) => {
          this.account = data;
          console.log(this.account)
          if (this.account && this.account.role === 'ADMIN') {
                this.router.navigate(['accounts']);
              } else if (this.account) {
                const userId = this.account.id;
                this.router.navigate(['account-details', userId]);
              } else {
                alert('Account data not available.');
          }
        },
        (error: any) => {
          console.error('An error occurred:', error);
          alert('You are not Logged in');
        }
      );
  }
}
