import { Component, ElementRef, OnInit, TemplateRef, ViewChild, ViewEncapsulation } from '@angular/core';
import 'ol/ol.css';
import { PatternService } from '../services/pattern/pattern.service';
import { Router } from '@angular/router';
import { DomSanitizer } from '@angular/platform-browser';
import html2canvas from 'html2canvas';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Point } from '../model/point';


@Component({
  selector: 'app-draw',
  templateUrl: './draw.component.html',
  encapsulation: ViewEncapsulation.None,
  styleUrls: ['./draw.component.scss']
})
export class DrawComponent implements OnInit {
  // @ViewChild("fileDropRef", { static: false }) fileDropEl: ElementRef;
  @ViewChild('shortContent') shortContentRef: TemplateRef<any>;
  list = Array<Point>();
  filesToUpload = Array<File>();
  isUploaded: boolean = false;
  url: any =  '../../assets/6.jpg';
  urls = Array<any>();
  imagePath: FileList;
  completeForm: boolean = false;
  typesGarment = Array<string>();
  errorMessage: string = "";

  msg : string = "";

  constructor(
    private patternService: PatternService,
    private route: Router,
    private domSanitizer: DomSanitizer,
    private modalService: NgbModal,
  ) { }

  ngOnInit(): void {
    this.url = this.domSanitizer.bypassSecurityTrustUrl(this.url);
  }

  openModal(content) {
    if (this.msg !== "") {
      content = this.msg;
    }
    this.modalService.open(content, { centered: true,  scrollable: true});
  }

  goToPatterns() {
    this.route.navigateByUrl('patterns');
  }

  handleFileInput(e: any) {
    if(this.filesToUpload.length === 2) {
      this.filesToUpload = [];
    }
    if(e.target.files) {
      for (let index = 0; index < e.target.files.length; index++) {
        const element = e.target.files[index];
        var reader = new FileReader();
        reader.readAsDataURL(e.target.files[index]);
        reader.onload = (event: any) => {
          this.urls.push(event.target.result);
          console.log(this.urls);
          // this.url=event.target.result;
        }
        this.filesToUpload.push(e.target.files.item(index));
      }
    }
    if(this.filesToUpload.length >= 1) {
      this.isUploaded = true;
      this.errorMessage = "";
    }
  }

  formDetails() {
    this.completeForm = true;
  }

  uploadFile() {
    this.patternService.getType(this.filesToUpload).subscribe(
      (res) => {
        console.log(res);
        // console.log(this.urls[0].toString());
        sessionStorage.setItem('front_img', this.urls[0].toString());
        sessionStorage.setItem('garment_id', res);

        this.msg = "Imaginea a fost salvată cu succes!";
      }, 
      (error) => {
        this.errorMessage = error.error.message;
        this.msg = "Imaginea există deja, încercați altă imagine!";
      });
  }

  delete(file:any) {
    this.isUploaded = false;
    this.errorMessage="";
    this.filesToUpload.forEach((obj, index) =>{
      if(file === obj) {
        this.filesToUpload.splice(index, 1);
        this.urls.splice(index, 1);
      }
    });
  }

  download() {
    var container = document.getElementById("canvas");
    html2canvas(container,{allowTaint : true}).then(function(canvas) {
		
			var link = document.createElement("a");
			document.body.appendChild(link);
			link.download = "html_image.png";
			link.href = canvas.toDataURL("image/png");
			link.target = '_blank';
			link.click();
		});
  }
}
