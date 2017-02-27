import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable()
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate() {
  if (sessionStorage.getItem("session")) {
          return true;
      }
      this.router.navigate(['home']);
      return false;
  }
}