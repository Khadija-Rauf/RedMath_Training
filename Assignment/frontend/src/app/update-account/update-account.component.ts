import { Component, OnInit } from '@angular/core';
import {Account} from '../account';
import {AccountService} from '../account.service';
import {ActivatedRoute, Router} from '@angular/router';
@Component({
  selector: 'app-update-account',
  templateUrl: './update-account.component.html',
  styleUrls: ['./update-account.component.css']
})
export class UpdateAccountComponent  implements OnInit{
 id!: number;
 account: Account = new Account();
 constructor(private accountService: AccountService,
              private route: ActivatedRoute,
              private router: Router){}
 ngOnInit(): void{
  this.id= this.route.snapshot.params['id'];
  this.accountService.getAccountById(this.id).subscribe(data =>{
    this.account = data;
  },
  (error: any) => {
         console.error('An error occurred:', error);
         alert('You are not Logged in');
       });
 }
 updateAccount(){
   this.accountService.updateAccount(this.id, this.account).subscribe(data =>{
    console.log(data);
    this.account = new Account();
//     this.gotoList();
   },
   error => console.log(error));
 }
 goToAccountList(){
  this.router.navigate(['/accounts']);
 }
  onSubmit(){
    this.accountService.updateAccount(this.id, this.account).subscribe( data =>{
      this.goToAccountList();
    },
    error => console.log(error));
  }
}

