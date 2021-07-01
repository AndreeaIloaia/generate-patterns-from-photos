import { Component, ContentChild, ElementRef, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { Point } from '../model/point';
import { ThreeService } from '../services/three/three.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Graph } from 'src/app/model/graph';
// import { PatternService } from '../pattern/pattern.service';
import { PatternService } from '../services/pattern/pattern.service';
import Cropper from 'cropperjs';
@Component({
  selector: 'app-pattern',
  templateUrl: './pattern.component.html',
  encapsulation: ViewEncapsulation.None,
  styleUrls: ['./pattern.component.scss']
})
export class PatternComponent implements OnInit {
  list = Array<Point>();
  @ViewChild('rendererCanvas', {static: true})
  public rendererCanvas: ElementRef<HTMLCanvasElement>;

  constructor(
    private threeService: ThreeService,
    private modalService: NgbModal,
    private patternService: PatternService
    ) { }
  index: number;
  point = [];
  nr_points: number;
  startPoint = [];
  lastPoint = [];
  index_seam = [];
  delete_from_seam: number;
  current_point= [];
  graph: Graph;
  url_photo: any;
  isLoaded= false;
  loading = false;

  msg: string = "";

  ngOnInit(): void {
    this.loading = false;
    this.threeService.createScene(this.rendererCanvas);
    this.threeService.animate();
  }

  mouseover(event: any) {
    // this.threeService.move(event);
  }

  clickme() {
    this.threeService.clickme();
  }

  addPoints() {
    this.threeService.addPoint(this.index);
  }

  openModal(content, number) {
    this.modalService.open(content, { centered: true,  scrollable: true});
    this.url_photo = sessionStorage.getItem('front_img');
  }

  exportSpline() {
    this.msg = this.threeService.exportSpline(1);
    // console.log(this.msg);
    this.msg = "Modelul a fost salvat!";
  }

  load3DGraph() {
    this.threeService.load3DGraph(this.graph);
  }

  sendGraphToCluster() {
    this.msg = this.threeService.exportSpline(2);
    this.loading = true;
    // this.msg = "Se încarcă!";
  }

  onValueChangePoint() {
    var tempList = this.threeService.getEndPoint();
    this.point = [];
    if(tempList.length > 2) {
      this.point.push(tempList.pop());
      this.point.push(tempList.pop());
      this.point.push(tempList.pop());
    }
    this.current_point = this.point.reverse();
    console.log(this.current_point);
    this.point = this.current_point;
    tempList = [];
  }

  setStartPoint() {
    console.log(this.current_point);
    this.startPoint = this.current_point;
    // this.startPoint = this.point.reverse();
    console.log(this.startPoint);
  }

  setLastPoint() {
    console.log(this.current_point);
    this.lastPoint = this.current_point;
    // this.lastPoint = this.point.reverse();
    console.log(this.lastPoint);
  }

  getIndexSeam() {
    console.log(this.current_point);
    this.index_seam = this.threeService.getIndexSeam(this.current_point);
  }

  deleteFromSeam() {
    console.log(this.delete_from_seam);
    console.log(this.current_point);
    this.threeService.removePoint(this.delete_from_seam, this.current_point);
  }

  deleteSeam() {
    this.threeService.removeSeam(this.delete_from_seam);
  }

  addNewSeam() {
    this.threeService.addNewSeam(this.startPoint, this.lastPoint, this.nr_points);
  }

  sendID(number: any) {
    this.graph = new Graph();
    var garment_id = sessionStorage.getItem('garment_id');
      this.patternService.load3DGraph(garment_id, number).subscribe(
        (res) => {
          this.graph = res;
          this.load3DGraph();
        }, 
        (error) => {
          // this.errorMessage = error.error.message;
          console.log(error.error.message);
        });
  }

  // change() {
  //   this.threeService.render();
  // }
  

  // dragging_changed(event: any) {
  //   this.threeService.dragging_changed(event);
  // }

  objectChange() {
    // this.threeService.objectChange();
  }

  draw() {
    setTimeout('', 5000);
    this.list = JSON.parse(sessionStorage.getItem("generatedCoords"));
    console.log(this.list);
    // for (let index = 0; index < res.length; index++) {
    //   for (let i = 0; i < res[index].length; i++) {
    //     const element = res[index][i];
    //     console.log(element);
    //     // this.list.push(element);
    //   }
    // }
  //     }
  // //   console.log(this.list);
  //   const canvas = <HTMLCanvasElement> document.getElementById('canvas');
  //   const ctx = canvas.getContext('2d');
  //   ctx.moveTo(this.list[0].x, this.list[0].y);
  //   for (let index = 1; index < this.list.length; index++) {
  //     const element = this.list[index];
  //     ctx.lineTo(element.x, element.y);    
  //   }
  //   ctx.lineTo(this.list[0].x, this.list[0].y);
  //   ctx.lineWidth = 5;
  //   ctx.stroke();
  }
}
