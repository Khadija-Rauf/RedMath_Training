import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http'
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AccountListComponent } from './account-list/account-list.component';
import { CreateAccountComponent } from './create-account/create-account.component';
import {FormsModule} from '@angular/forms';
import { UpdateAccountComponent } from './update-account/update-account.component';
import { AccountDetailsComponent } from './account-details/account-details.component';
import { LoginComponent } from './login/login.component';
import { ViewTransactionsComponent } from './view-transactions/view-transactions.component';
import { CreateTransactionComponent } from './create-transaction/create-transaction.component';
import { ViewBalanceComponent } from './view-balance/view-balance.component';
import { HomeComponent } from './home/home.component';
import { LogoutComponent } from './logout/logout.component';
@NgModule({
  declarations: [
    AppComponent,
    AccountListComponent,
    CreateAccountComponent,
    UpdateAccountComponent,
    AccountDetailsComponent,
    LoginComponent,
    ViewTransactionsComponent,
    CreateTransactionComponent,
    ViewBalanceComponent,
    HomeComponent,
    LogoutComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
