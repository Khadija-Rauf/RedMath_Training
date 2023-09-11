import { Component, OnInit } from '@angular/core';
import {Account} from '../account';
import {AccountService} from '../account.service';
import {Router} from '@angular/router'
import { NgForm } from '@angular/forms';
@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.css']
})
export class CreateAccountComponent implements OnInit{
 account: Account = new Account();
 errorMessage: string = '';
 constructor(private accountService: AccountService,
              private router: Router){}
 ngOnInit(): void{
 }
 saveAccount(){
   this.accountService.createAccount(this.account).subscribe(data =>{
    console.log(data);
    this.gotoAccountList();
     },
     (error) => {
      if (error.status === 403) {
              this.errorMessage = 'Please press Create again.🧓';
            }
       if (error.status === 500) {
         this.errorMessage = 'Please enter correct data.';
       }
        else {
        this.accountService.createAccount(this.account).subscribe(data =>{
          console.log(data);
          this.gotoAccountList();
        });
         this.errorMessage = 'You are not Logged in as Admin!';
         alert("You are not Logged in!");
         return;
       }
       console.error(error);
     }
   );
 }
 gotoAccountList(){
  this.router.navigate(['/accounts']);
 }
onSubmit(myForm: NgForm) {
    if (myForm.invalid) {
      console.log('Validation errors:', myForm.errors);
      return;
    }
    console.log(this.account);
    this.saveAccount();
  }
}
