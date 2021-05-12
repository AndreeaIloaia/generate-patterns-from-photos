import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate{


  constructor(
    public auth: AuthService,
    public router: Router
  ) { }

  canActivate(route: ActivatedRouteSnapshot): boolean {
    // const expectedRole = route.data.expectedRole;
    // const role = sessionStorage.getItem('role');

    // if (
    //   this.auth.isAuthenticated() &&
    //   role === expectedRole
    // ) {
    //   return true;
    // }

    // if(expectedRole === "ADMIN")
    // {
    //   this.router.navigate(['/admin']);
    // }
    // else {
    //   this.router.navigate(['']);
    // }

    if (this.auth.isAuthenticated()) {
      return true;
    }

    this.router.navigateByUrl('login');
    return false;
  }
}
