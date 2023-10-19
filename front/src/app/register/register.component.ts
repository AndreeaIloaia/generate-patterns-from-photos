import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { RegisterService } from '../services/register/register.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  username: string;
  password: string;

  messageRegister : string = '';

  constructor(
    private registerService: RegisterService,
    private route: Router
  ) { }

  ngOnInit(): void {
  }

  register(): void {
    let user: User = {
      username: this.username,
      email: "da",
      password: this.password
    }
    this.registerService.register(user).subscribe(
      (res) => {
        this.messageRegister = res.text;
        this.route.navigateByUrl('/login');
      }, 
      (status) => {
        this.messageRegister = status.error;
      }
    )
  }

}
