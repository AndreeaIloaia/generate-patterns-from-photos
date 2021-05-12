import { Component, OnInit } from '@angular/core';
import { LoginService } from '../services/login/login.service';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { HttpResponse } from '@angular/common/http';
import { UserLogin } from '../model/userLogin';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  usernameOrEmail: string;
  password: string;

  messageRegister : string = '';
  bodyRegister : string = '';

  constructor(
    private loginService: LoginService,
    private route: Router,
  ) { }

  ngOnInit(): void {
  }

  login(): void {
    let user: UserLogin = {
      usernameOrEmail: this.usernameOrEmail,
      password: this.password
    }
    this.loginService.login({
      usernameOrEmail: this.usernameOrEmail,
      password: this.password
    }).subscribe(
      (res) => {
        sessionStorage.clear();
        sessionStorage.setItem('tokenType', res.tokenType);
        sessionStorage.setItem('token', res.accessToken);
        sessionStorage.setItem('role', res.role);

        this.route.navigateByUrl('/home');
      }, 
      (status) => {
        this.messageRegister = status.error;
        console.log(this.messageRegister);
      }
    )
  }

}
