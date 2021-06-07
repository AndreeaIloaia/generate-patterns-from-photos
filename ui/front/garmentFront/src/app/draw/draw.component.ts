import { Component, ElementRef, OnInit, TemplateRef, ViewChild, ViewEncapsulation } from '@angular/core';
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
    if(this.filesToUpload.length === 2) {
      this.isUploaded = true;
      this.errorMessage = "";
    }
    console.log(this.url);
    console.log(this.filesToUpload);
    console.log(this.isUploaded);
  }

  formDetails() {
    this.completeForm = true;
  }

  uploadFile() {
    this.patternService.getType(this.filesToUpload).subscribe(
      (res) => {
        console.log(res)
        for (let index = 0; index < res.types.length; index++) {
          const element = res.types[index];
          this.typesGarment.push(element);  
        }
        sessionStorage.setItem('types', this.typesGarment[0].toString());
        sessionStorage.setItem('front_img', this.urls[0].toString());
        sessionStorage.setItem('side_img', this.urls[1].toString());
        sessionStorage.setItem('garment_id', res.id);
        console.log(this.typesGarment);
        // this.openModal('Imaginile au fost salvate. S-a identificat in imagine: ' + this.typesGarment[0]);
        // this.openModal(this.shortContentRef);
      }, 
      (error) => {
        this.errorMessage = error.error.message;
        console.log(error.error.message);
        
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

  // draw(): void {
  //   console.log(this.fileToUpload.file.name);
  //   this.patternService.draw(this.fileToUpload.file.name).subscribe(
  //     (res) => {
  //      for (let index = 0; index < res.length; index++) {
  //         for (let i = 0; i < res[index].length; i++) {
  //           const element = res[index][i];
  //           this.list.push(element);
  //         }
  //         }
  //         sessionStorage.setItem("generatedCoords", JSON.stringify(this.list));
  //         this.route.navigateByUrl('/patterns');
          
  //         // console.log(this.list);
  //       // const canvas = <HTMLCanvasElement> document.getElementById('canvas');
  //       // const ctx = canvas.getContext('2d');
  //       // ctx.moveTo(this.list[0].x, this.list[0].y);
  //       // for (let index = 1; index < this.list.length; index++) {
  //       //   const element = this.list[index];
  //       //   ctx.lineTo(element.x, element.y);    
  //       // }
  //       // ctx.lineTo(this.list[0].x, this.list[0].y);
  //       // ctx.lineWidth = 5;
  //       // ctx.stroke();
  //     }
  //   );
  // }
}
