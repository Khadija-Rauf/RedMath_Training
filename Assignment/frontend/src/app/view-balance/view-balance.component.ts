import { Component,OnInit } from '@angular/core';
import { ActivatedRoute,Router } from '@angular/router';
import {Balance} from '../balance';
import {AccountService} from '../account.service'

@Component({
  selector: 'app-view-balance',
  templateUrl: './view-balance.component.html',
  styleUrls: ['./view-balance.component.css']
})
export class ViewBalanceComponent implements OnInit{
  id!: number
  balance = new Balance();
  constructor(private accountService: AccountService,
                private route: ActivatedRoute,
                private router: Router){ }
  ngOnInit():void{
    this.id = this.route.snapshot.params['id'];
    this.getBalance();
  }
  private getBalance(){
    this.accountService.getBalanceList(this.id).subscribe(data =>{
      this.balance = data;
    },(error: any) => {
            console.error('An error occurred:', error);
            alert('No, Balance');
          });
    }
  }
