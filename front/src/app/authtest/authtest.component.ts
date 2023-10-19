import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { LoginService } from '../services/login/login.service';
import { RegisterService } from '../services/register/register.service';

@Component({
  selector: 'app-authtest',
  templateUrl: './authtest.component.html',
  styleUrls: ['./authtest.component.scss']
})
export class AuthtestComponent implements OnInit {
  usernameOrEmail: string;
  password: string;

  username: string;
  email: string;
  passwordRegister: string;

  messageRegister : string = '';
  bodyRegister : string = '';
  isLoggedIn: boolean = false;

  constructor(
    private loginService: LoginService,
    private registerService: RegisterService,
    private route: Router
    ) { }

  ngOnInit(): void {
    // const token = sessionStorage.getItem('token');
    // console.log(token);
    // if (token) {
    //     this.isLoggedIn = true;
    //     this.route.navigateByUrl("home");
    // }
  }

  removeClass(): void {
    const container = document.querySelector(".container");
    container.classList.remove("right-panel-active");
  }
  

  addClass(): void {
    const container = document.querySelector(".container");
    container.classList.add("right-panel-active");
  }

  login(): void {
    console.log("OK - login");
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
  
  register(): void {
    this.registerService.register({
      username: this.username,
      email: this.email,
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
