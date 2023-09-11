import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AccountService } from './account.service';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = '/login';

  constructor(private httpClient: HttpClient, private accountService: AccountService) {}

  login(user: { username: string; password: string }): Observable<any> {
    const formData = new FormData();
    formData.append('username', user.username);
    formData.append('password', user.password);

    console.log(formData);

    return this.httpClient.post<any>(this.baseUrl, formData);
  }
  logout() {
    this.accountService.logout().subscribe(
      () => {
        alert('You are logged out!!');
//           this.router.navigate(['/login']);
      },
      (error) => {
        console.error('Logout failed:', error);
      }
    );
  }
}
