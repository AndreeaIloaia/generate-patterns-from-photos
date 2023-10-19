import { Component, ElementRef, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import 'ol/ol.css';
import { PatternService } from '../services/pattern/pattern.service';
import { Router } from '@angular/router';
import { DomSanitizer } from '@angular/platform-browser';
import html2canvas from 'html2canvas';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Point } from '../model/point';
import { MyFile } from '../model/file';


@Component({
  selector: 'app-draw',
  templateUrl: './draw.component.html',
  encapsulation: ViewEncapsulation.None,
  styleUrls: ['./draw.component.scss']
})
export class DrawComponent implements OnInit {
  // @ViewChild("fileDropRef", { static: false }) fileDropEl: ElementRef;
  list = Array<Point>();
  fileToUpload: MyFile = new MyFile;
  isUploaded: boolean = false;
  url: any =  '../../assets/6.jpg';
  imagePath: FileList;
  completeForm: boolean = false;
  typeGarment: string;

  

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
    this.modalService.open(content, { centered: true,  scrollable: true});
  }

  handleFileInput(e: any) {
    if(e.target.files) {
        var reader = new FileReader();
        reader.readAsDataURL(e.target.files[0]);
        reader.onload = (event: any) => {
          this.url=event.target.result;
        }
    }
    this.fileToUpload.file = e.target.files.item(0);
    this.isUploaded = true;
    console.log(this.url);
    console.log(this.fileToUpload.file);
    console.log(this.isUploaded);
  }

  formDetails() {
    this.completeForm = true;
  }

  uploadFile() {
    // this.completeForm=!this.completeForm;
    // console.log(this.typeGarment);
    this.patternService.postFile(this.fileToUpload.file, this.typeGarment).subscribe(
      (res) => {
        
      // this.src = res.url;
      // do something, if upload success
      }, 
      (error) => {
        console.log(error);
      });
  }

  delete() {
    this.isUploaded = false;
    this.url="";
    this.fileToUpload.file = null;
    console.log(this.url);
    console.log(this.fileToUpload.file);
    console.log(this.isUploaded);
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

  draw(): void {
    console.log(this.fileToUpload.file.name);
    this.patternService.draw(this.fileToUpload.file.name).subscribe(
      (res) => {
        console.log(res);
        
        for (let index = 0; index < res.length; index++) {
          for (let i = 0; i < res[index].length; i++) {
            const element = res[index][i];
            this.list.push(element);
          }
          }
        console.log(this.list);
        const canvas = <HTMLCanvasElement> document.getElementById('canvas');
        const ctx = canvas.getContext('2d');
        ctx.moveTo(this.list[0].x, this.list[0].y);
        for (let index = 1; index < this.list.length; index++) {
          const element = this.list[index];
          ctx.lineTo(element.x, element.y);    
        }
        ctx.lineTo(this.list[0].x, this.list[0].y);
        ctx.lineWidth = 5;
        ctx.stroke();
      }
    );
  }
}
