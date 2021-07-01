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
  loading = true;

  constructor(
    private profileService: ProfileService,
    private route: Router
    ) { }

  ngOnInit(): void {
    this.loading = true;
    this.profileService.getGarments().subscribe(
        (res) => {
          console.log(res);
          this.images = res.files;
          this.garmentIds = res.ids;
          this.loading = false;
        }, 
        (error) => {
          // this.errorMessage = error.error.message;
          console.log(error.error.message);
        });
  }

  chooseGarment(i: number) {
    // i++;
    sessionStorage.setItem('garment_id', this.garmentIds[i] + "");
    sessionStorage.setItem('front_img', this.images[i].toString());
    this.route.navigateByUrl('/patterns');
  }

}
