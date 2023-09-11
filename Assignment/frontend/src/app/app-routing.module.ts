import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountListComponent } from './account-list/account-list.component';
import { CreateAccountComponent } from './create-account/create-account.component';
import { UpdateAccountComponent } from './update-account/update-account.component';
import { AccountDetailsComponent } from './account-details/account-details.component';
import { ViewTransactionsComponent } from './view-transactions/view-transactions.component';
import { ViewBalanceComponent } from './view-balance/view-balance.component';
import { CreateTransactionComponent } from './create-transaction/create-transaction.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { LogoutComponent } from './logout/logout.component';
const routes: Routes = [
{path:'accounts', component:AccountListComponent},
{path:'create-account', component:CreateAccountComponent},
{path:'create-transaction/:id', component:CreateTransactionComponent},
{path:'update-account/:id', component:UpdateAccountComponent},
{path:'account-details/:id', component:AccountDetailsComponent},
{ path: 'view-transactions/:id', component: ViewTransactionsComponent },
{ path: 'view-balance/:id', component: ViewBalanceComponent },
{path:'',redirectTo:'login',pathMatch:'full'},
{ path: 'login', component: LoginComponent },
{ path: 'logout', component: LogoutComponent },
{ path: 'home', component: HomeComponent },
// { path: '', component: HomeComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
