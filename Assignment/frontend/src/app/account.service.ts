import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Account } from './account';
import { Transaction } from './transaction';
import { Balance } from './balance';
import {Observable } from 'rxjs'
@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private baseUrl = "/bank/accounts"
  constructor(private httpClient: HttpClient) {
  }
  getAccountList(): Observable<Account[]>{
    return this.httpClient.get<Account[]>(`${this.baseUrl}`)
  }
  createAccount(account: Account): Observable<Object>{
    return this.httpClient.post(`${this.baseUrl}`, account);
  }
  getAccountById(id: number): Observable<Account>{
    return this.httpClient.get<Account>(`${this.baseUrl}/${id}`)
  }
  updateAccount(id: number, account: Account): Observable<any>{
      return this.httpClient.put(`${this.baseUrl}/${id}`, account);
    }
  deleteAccount(id: number): Observable<any>{
        return this.httpClient.delete(`${this.baseUrl}/${id}`);
      }
  getTransactionList(id: number): Observable<any>{
          return this.httpClient.get(`${this.baseUrl}/transactions/${id}`);
        }
  createTransaction(transaction: Transaction, id: number): Observable<any>{
     return this.httpClient.post(`${this.baseUrl}/transactions/${id}`, transaction);
    }
  getBalanceList(id: number): Observable<any>{
            return this.httpClient.get(`${this.baseUrl}/balances/${id}`);
     }
  getMyDetails():  Observable<Account>{
       return this.httpClient.get<Account>(`${this.baseUrl}/mine`)
  }
  getDetails(username: string):  Observable<Account>{
         return this.httpClient.get<Account>(`${this.baseUrl}/findByName/${username}`)
    }
  logout(): Observable<any> {
      return this.httpClient.post('/logout', {});
    }
}
