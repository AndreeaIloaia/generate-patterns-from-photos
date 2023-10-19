import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  isLoggedIn: boolean = true;

  constructor(
    private route: Router
    ) { }

  ngOnInit(): void {
    const token = sessionStorage.getItem('token');
    console.log(token);
    if (!token) {
        this.isLoggedIn = false;
        this.route.navigateByUrl("login");
    }
  }

  onScroll(event: any) {
      console.log('da');
  }

}
