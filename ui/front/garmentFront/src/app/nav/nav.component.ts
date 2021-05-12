import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  encapsulation: ViewEncapsulation.None,
  styles: ['./nav.component.scss',]
})
export class NavComponent implements OnInit {

  constructor(
    public route: Router,
    private modalService: NgbModal,
  ) { }

  ngOnInit(): void {
  }

  logout() {
    sessionStorage.clear();
    this.route.navigateByUrl('login');
  }

  closeResult: string;

  openModal(content) {
    this.modalService.open(content, { centered: true,  scrollable: true});
  }
  
}
