import { Component, OnInit } from '@angular/core';
import { ProfileService } from '../services/profile/profile.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  garmentIds = [];
  images = [];

  constructor(
    private profileService: ProfileService,
    private route: Router
    ) { }

  ngOnInit(): void {
    this.profileService.getGarments().subscribe(
        (res) => {
          console.log(res);
          this.images = res.files;
          this.garmentIds = res.ids;
        }, 
        (error) => {
          // this.errorMessage = error.error.message;
          console.log(error.error.message);
        });
  }

  chooseGarment(i: number) {
    i++;
    sessionStorage.setItem('garment_id', i + "");
    sessionStorage.setItem('front_img', this.images[i - 1].toString());
    this.route.navigateByUrl('/patterns');
  }

}
