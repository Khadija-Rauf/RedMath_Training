import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { HttpErrorResponse } from '@angular/common/http'; // Import HttpErrorResponse

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent {
constructor(private authService: AuthService, private router: Router) {}
ngOnInit(): void {
  }
 async logout() {
    try {
      this.authService.logout();
      this.authService.logout();
      this.router.navigate(['login']);
    } catch (error) {
      console.error('An error occurred:', error);
      if (error instanceof HttpErrorResponse && error.status === 403) {
         this.authService.logout();
      } else {
        alert('Error: Unable to complete the request. Please try again later.');
      }
    }
  }
}
