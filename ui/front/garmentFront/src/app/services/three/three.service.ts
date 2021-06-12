import * as THREE from '../../../../node_modules/three';
import { Router } from '@angular/router';
import { OrbitControls } from "../../../../node_modules/three/examples/jsm/controls/OrbitControls"
import { ElementRef, Injectable, NgZone, OnDestroy, ViewChild } from '@angular/core';
import { GLTFLoader } from '../../../../node_modules/three/examples/jsm/loaders/GLTFLoader.js';
import { TransformControls } from "../../../../node_modules/three/examples/jsm/controls/TransformControls.js";
import { DragControls } from "../../../../node_modules/three/examples/jsm/controls/DragControls.js";
import { Seam } from 'src/app/model/seam';
import { Graph } from 'src/app/model/graph';
import { PatternService } from '../pattern/pattern.service';

@Injectable({
  providedIn: 'root'
})
export class ThreeService implements OnDestroy {
  container: HTMLCanvasElement;
  private renderer: THREE.WebGLRenderer;
  private camera: THREE.PerspectiveCamera;
  private scene: THREE.Scene;

  point: THREE.Vector3;
  pointer: THREE.Vector2;
  onUpPosition: THREE.Vector2;
	onDownPosition: THREE.Vector2;

  geometry: THREE.BoxGeometry;

  transformControl: TransformControls;
  controls: OrbitControls;
  raycaster: THREE.Raycaster;
 
  ARC_SEGMENTS = 200;

  seams = [];
  endPoint = [];
  loadedGraph: boolean = false;
  addedNewSeam: boolean = false;
  no_seams = 0;
  graph: Graph;


	splines = {
    uniform: true,
    centripetal: true,
    chordal: true
  };

	params = {
		uniform: true,
		tension: 0.6,
		centripetal: true,
		chordal: true,
		// addPoint: addPoint,
		// removePoint: removePoint,
		// exportSpline: exportSpline
			};
  
  private frameId: number = null;
  constructor(private ngZone: NgZone, 
    private patternService: PatternService,
    private route: Router,
    ) { }
 
  public ngOnDestroy(): void {
    if (this.frameId != null) {
      cancelAnimationFrame(this.frameId);
    }
  }

  public getEndPoint() {
    return this.endPoint;
  }

  public createScene(canvas: ElementRef<HTMLCanvasElement>): void {
    // for (let index = 0; index < this.no_seams; index++) {
    //   this.seams.push(new Seam());
    // }

    this.point = new THREE.Vector3();
    this.raycaster = new THREE.Raycaster();
    this.pointer = new THREE.Vector2();
    this.onUpPosition = new THREE.Vector2();
    this.onDownPosition = new THREE.Vector2();
    this.geometry = new THREE.BoxGeometry( 10, 10, 10 );

    // container = document.getElementById( 'container' );
    this.container = canvas.nativeElement;
		this.scene = new THREE.Scene();
    this.scene.background = new THREE.Color( 0xf0f0f0 );

    this.camera = new THREE.PerspectiveCamera( 70, window.innerWidth / window.innerHeight, 1, 10000 );
    this.camera.position.set( 0, 250, 1000 );
    this.scene.add( this.camera );

    this.scene.add( new THREE.AmbientLight( 0xf0f0f0 ) );
    const light = new THREE.SpotLight( 0xffffff, 1.5 );
    light.position.set( 0, 1500, 200 );
    light.angle = Math.PI * 0.2;
    light.castShadow = true;
    light.shadow.camera.near = 200;
    light.shadow.camera.far = 2000;
    light.shadow.bias = - 0.000222;
    light.shadow.mapSize.width = 1024;
    light.shadow.mapSize.height = 1024;
    this.scene.add( light );

    const planeGeometry = new THREE.PlaneGeometry( 2000, 2000 );
    planeGeometry.rotateX( - Math.PI / 2 );
    const planeMaterial = new THREE.ShadowMaterial( { opacity: 0.2 } );

    const plane = new THREE.Mesh( planeGeometry, planeMaterial );
    plane.position.y = - 200;
    plane.receiveShadow = true;
    this.scene.add( plane );

    const helper = new THREE.GridHelper( 2000, 100 );
    helper.position.y = - 199;
    helper.material.opacity = 0.25;
    helper.material.transparent = true;
    this.scene.add( helper );

    this.renderer = new THREE.WebGLRenderer({
      canvas: this.container,
      alpha: true,    // transparent background
      antialias: true // smooth edges
    });
    this.renderer.setPixelRatio( window.devicePixelRatio );
    this.renderer.setSize( window.innerWidth, window.innerHeight );
    this.renderer.shadowMap.enabled = true;
      
    this.graph = new Graph();
    // var garment_id = sessionStorage.getItem('garment_id');
    //   this.patternService.load3DGraph(garment_id).subscribe(
    //     (res) => {
    //       this.graph = res;
    //       this.no_seams = this.graph.seams.length;
    //     }, 
    //     (error) => {
    //       // this.errorMessage = error.error.message;
    //       console.log(error.error.message);
    //     });
    this.loadModel();
    // this.myEarcut();
  } 

  controlObjects() {
      const controls = new OrbitControls( this.camera, this.renderer.domElement );
      controls.damping = 0.2;

      const transformControl = new TransformControls( this.camera, this.renderer.domElement );
      this.scene.add( transformControl );
      var hoverPoint = [];
      for (let index = 0; index < this.no_seams; index++) {
        var dragcontrols = new DragControls(this.seams[index].splineHelperObjects, this.camera, this.renderer.domElement); //
        dragcontrols.enabled = false;
        dragcontrols.addEventListener('hoveron', function(event) {
          transformControl.attach(event.object);
          console.log(event.object);
          hoverPoint.push(event.object.position.x);
          hoverPoint.push(event.object.position.y);
          hoverPoint.push(event.object.position.z);
          controls.enabled = false;
        });
        dragcontrols.addEventListener('dblclick', function(event) {
          console.log(event.object);
        });
        this.container.addEventListener('click', function() {
          if(transformControl !== undefined) {
            transformControl.detach();
          }
          controls.enabled = true;
        });
      }
      this.transformControl = transformControl;
      this.controls = controls;
      this.endPoint = hoverPoint;
  }

  public load3DGraph(graph: Graph) {
    // this.deleteExistingGraph();
    this.graph = graph;
    console.log(this.graph);
    this.no_seams = this.graph.seams.length;
    for (let index = 0; index < this.no_seams; index++) {
      this.seams.push(new Seam());
      this.loadCurves(index);
    }
    this.loadedGraph = true;
    this.controlObjects();

  }

  deleteExistingGraph() {
    //delete splines
    for (let index = 0; index < this.no_seams; index++) {
      for ( const k in this.seams[index].splines ) {
        const spline = this.seams[index].splines[ k ];
        this.scene.remove( spline.mesh );
      }
    }

    //delete helpers
    for (let index = 0; index < this.no_seams; index++) {
      for ( let i = 0; i < this.seams[index].splinePointsLength; i ++ ) {
        const p = this.seams[index].splineHelperObjects[ i ];
        console.log(this.transformControl);
        console.log(this.transformControl.object);
        if ( this.transformControl.object == p ) {
          this.transformControl.detach();
        }
        this.scene.remove(p);
      }
    }
  }
 
  public loadModel() {
    const loader = new THREE.TextureLoader();
      
    //planurile ce intersecteaza obiectul 3D
    var planeGeom1 = new THREE.PlaneBufferGeometry(10, 10, 10, 10);
    planeGeom1.translate(0, 0, 10 * -0.5);

    var planeGeom2 = new THREE.PlaneBufferGeometry(10, 10, 10, 10);
    planeGeom2.translate(10 * 0.5, 0, 10 * 0);
    planeGeom2.rotateY(Math.PI * 0.5);

    // using the same geometry you can have plane meshes
    var front_url = sessionStorage.getItem('front_img');
    var side_url = sessionStorage.getItem('side_img');
    var mesh1 = new THREE.Mesh(planeGeom1, new THREE.MeshBasicMaterial({map: loader.load(front_url)}));
    var mesh2 = new THREE.Mesh(planeGeom2, new THREE.MeshBasicMaterial({map: loader.load(side_url)}));
    // this.scene.add(mesh1);
    // this.scene.add(mesh2);

    
    // modelul 3D in functie de predictiile facute
    const gltfLoader = new GLTFLoader();
    const url_body = '../../assets/body2.gltf';
    const url_torso = '../../assets/torso.gltf';
    const url_legs = '../../assets/legs.gltf';
    const predictions = [sessionStorage.getItem('types')];
    let url;
    let poz_x, poz_y, poz_z;
    if (predictions.indexOf('dress') > -1 ||
        (predictions.indexOf('pants') > -1 && predictions.indexOf('shirt') > -1)
    ) {
      url = url_body;
      poz_x = 10 * 0;
      poz_y = 10 * -1;
      poz_z = 10 * -0.45;
    }
    if (predictions.indexOf('pants') > -1 && predictions.indexOf('shirt') == -1) {
      url = url_legs;
      poz_x = 10 * 0;
      poz_y = 10 * -1;
      poz_z = 10 * -0.45;
    }
    if (predictions.indexOf('shirt') > -1 && predictions.indexOf('pants') == -1) {
      url = url_torso;
      poz_x = 10 * 0;
      poz_y = 10 * -1;
      poz_z = 10 * -0.45;
    }

    url = url_body;
    gltfLoader.load(url, (gltf) => {
      const root2 = gltf.scene.children[0];
      root2.position.set(0, 10 * -20, 0);
      root2.scale.set(40,40,40); //x,z,y
      const root = gltf.scene;
      this.scene.add(root);
    });
  }

  public generateCoords(index) {
    var splinePointsLength = 0;
    for(let i = 0; i < this.graph.seams[index].length; i++) {
      splinePointsLength++;
      this.seams[index].splinePointsLength = splinePointsLength;
      var verticeNumber = this.graph.seams[index][i];
      var point = this.graph.vertices[verticeNumber]
      var pos = new THREE.Vector3( point[0], point[1], point[2] );
      this.addSplineObject( pos, index );
    }
  }

  public loadCurves(index: any) {
    this.generateCoords(index);

    this.seams[index].positions.length = 0;

    for ( let i = 0; i < this.seams[index].splinePointsLength; i ++ ) {
      this.seams[index].positions.push( this.seams[index].splineHelperObjects[ i ].position );
    }

    const geometry = new THREE.BufferGeometry();
    geometry.setAttribute( 'position', new THREE.BufferAttribute( new Float32Array( this.ARC_SEGMENTS * 3 ), 3 ) );

    // for (let index = 0; index < this.no_seams; index++) {
      this.generateCurves(index);

      for ( const k in this.seams[index].splines ) {
        const spline = this.seams[index].splines[ k ];
        this.scene.add( spline.mesh );
      }
    // }
  }

  public generateCurves(index: any) {
    const geometry = new THREE.BufferGeometry();
    geometry.setAttribute( 'position', new THREE.BufferAttribute( new Float32Array( this.ARC_SEGMENTS * 3 ), 3 ) );

    let curve = new THREE.CatmullRomCurve3( this.seams[index].positions );
    curve.curveType = 'catmullrom';
    curve.mesh = new THREE.Line( geometry.clone(), new THREE.LineBasicMaterial( {
      color: 0xff0000,
      opacity: 0.35
    } ) );
    curve.mesh.castShadow = true;
    this.seams[index].splines.uniform = curve;

    curve = new THREE.CatmullRomCurve3( this.seams[index].positions );
    curve.curveType = 'centripetal';
    curve.mesh = new THREE.Line( geometry.clone(), new THREE.LineBasicMaterial( {
      color: 0x00ff00,
      opacity: 0.35
    } ) );
    curve.mesh.castShadow = true;
    this.seams[index].splines.centripetal = curve;

    curve = new THREE.CatmullRomCurve3( this.seams[index].positions );
    curve.curveType = 'chordal';
    curve.mesh = new THREE.Line( geometry.clone(), new THREE.LineBasicMaterial( {
      color: 0x0000ff,
      opacity: 0.35
    } ) );
    curve.mesh.castShadow = true;
    this.seams[index].splines.chordal = curve;
  }
  
  public clickme() {
    for (let index = 0; index < this.no_seams; index++) {
      this.updateSplineOutline(index);
    }
    this.render();
    // this.addPoint();
  }

  public addSplineObject( position, index ) {
    const material = new THREE.MeshLambertMaterial( { color: Math.random() * 0xffffff } );
    const object = new THREE.Mesh( this.geometry, material );

    if ( position ) {
      object.position.copy( position );
    } else {
      object.position.x = Math.random() * 1000 - 500;
      object.position.y = Math.random() * 600;
      object.position.z = Math.random() * 800 - 400;
    }

    object.castShadow = true;
    object.receiveShadow = true;
    this.scene.add( object );

    this.seams[index].splineHelperObjects.push( object );
    return object;
  }

  public addNewSeam(startPoint: any[], lastPoint: any[], nr_points: number) {
    console.log(nr_points);
    this.seams.push(new Seam());
    var index = this.no_seams;
    this.no_seams++;

    this.seams[index].positions.length = 0;
    this.seams[index].splinePointsLength = Number(nr_points) + 2;

    var pos = new THREE.Vector3( startPoint[0], startPoint[1], startPoint[2] );
    this.addSplineObject( pos, index );
    console.log(pos);
    for(let i = 0; i < Number(nr_points); i++) {
      if(startPoint[0] < lastPoint[0]) {
        pos = new THREE.Vector3( startPoint[0] + i * 20, startPoint[1], startPoint[2] + 200 );
      } else {
        pos = new THREE.Vector3( startPoint[0] - i * 20, startPoint[1], startPoint[2] + 200 );
      }
      console.log(pos);
      this.addSplineObject( pos, index );
    }
    pos = new THREE.Vector3( lastPoint[0], lastPoint[1], lastPoint[2] );
    console.log(pos);
    this.addSplineObject( pos, index );

   
    for ( let i = 0; i < this.seams[index].splinePointsLength; i ++ ) {
      this.seams[index].positions.push( this.seams[index].splineHelperObjects[ i ].position );
    }

    const geometry = new THREE.BufferGeometry();
    geometry.setAttribute( 'position', new THREE.BufferAttribute( new Float32Array( this.ARC_SEGMENTS * 3 ), 3 ) );

    this.generateCurves(index);

    for ( const k in this.seams[index].splines ) {
      const spline = this.seams[index].splines[ k ];
      this.scene.add( spline.mesh );
    }
    // this.addedNewSeam = true;
    // this.controls();
  }

  public addPoint(index: any) {
    this.seams[index].splinePointsLength ++;
    this.seams[index].positions.push( this.addSplineObject(null, index).position );

    this.updateSplineOutline(index);
  }

/////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////
  public removePoint(index_seam, coords) {
    if ( this.seams[index_seam].splinePointsLength <= 2 ) {
       return;
    }

    var vertices_aux = [];
    var index_point = -1;
    for (let index = 0; index < this.graph.vertices.length; index++) {
      const element = this.graph.vertices[index];
      if(element[0] === coords[0] && element[1] === coords[1] && element[2] === coords[2]) {
        index_point = index;
      } else {
        vertices_aux.push(element);
      }
    }
    const element = this.graph.seams[index_seam];
    var seams_aux = [];
    for (let i = 0; i < element.length; i++) {
      const seam = element[i];
      if(seam !== index_point ) {
        seams_aux.push(seam);
      }
    }
    console.log(this.graph);
    this.graph.seams[index_seam] = seams_aux;
    this.graph.vertices = vertices_aux;
    console.log(this.graph);

    var deleted_one;
    var positions_aux = [];
    var splineHelperObjects_aux = [];
    for ( let i = 0; i < this.seams[index_seam].splinePointsLength; i ++ ) {
      const el = this.seams[index_seam].splineHelperObjects[ i ].position;
      const p = this.seams[index_seam].splineHelperObjects[ i ];
      if (el.x === coords[0] && el.y === coords[1] && el.z === coords[2]) {
        deleted_one = p;
      } else {
        var pos = new THREE.Vector3( el.x, el.y, el.z );
        splineHelperObjects_aux.push(this.addSplineObject(pos, index_seam));
      }
    }
    this.seams[index_seam].splineHelperObjects = splineHelperObjects_aux;
    this.seams[index_seam].splinePointsLength--;


    
    for (let i = 0; i < this.seams[index_seam].splinePointsLength; i++) {
      positions_aux.push(this.seams[index_seam].splineHelperObjects[ i ].position);
    }

    this.seams[index_seam].positions.length--;
    this.seams[index_seam].positions = positions_aux;

    // const point = this.seam.splineHelperObjects.pop();
    // this.seam.splinePointsLength --;
    // this.seam.positions.pop();

    if ( this.transformControl.object === deleted_one ) this.transformControl.detach();
    this.scene.remove( deleted_one );
    this.updateSplineOutline(index_seam);
  }

  removeSeam(index_seam) {
    console.log(this.graph);
    
    var to_delete = [];
    for ( let i = 0; i < this.seams[index_seam].splinePointsLength; i ++ ) {
      const p = this.seams[index_seam].splineHelperObjects[ i ];
      to_delete.push(p.position);
      this.scene.remove( p );
    }
    this.seams[index_seam].splineHelperObjects = [];
    this.updateSplineOutline(index_seam);
    this.seams[index_seam].splinePointsLength = 0;
    this.seams[index_seam].positions.length = 0;
    this.seams[index_seam].positions = [];

    var vertices_aux = [];
    var pos = this.graph.seams[index_seam];
    for (let index = 0; index < this.graph.vertices.length; index++) {
      if(pos.indexOf(index) > -1){
        continue;
      }
      vertices_aux.push(this.graph.vertices[index]);
    }
    this.graph.vertices = vertices_aux;

    var seams_aux = [];
    for (let index = 0; index < this.no_seams; index++) {
      if (index == index_seam) {
        continue;
      }
      seams_aux.push(this.seams[index]);
    }
    this.seams = seams_aux;
    this.transformControl.detach();
    this.graph.seams = this.seams;
    this.no_seams--;
  }

  public updateSplineOutline(index: any) {
    for ( const k in this.seams[index].splines ) {
      const spline = this.seams[index].splines[ k ];

      const splineMesh = spline.mesh;
      const position = splineMesh.geometry.attributes.position;

      for ( let i = 0; i < this.ARC_SEGMENTS; i ++ ) {
        const t = i / ( this.ARC_SEGMENTS - 1 );
        spline.getPoint( t, this.point );
        position.setXYZ( i, this.point.x, this.point.y, this.point.z );
      }
      position.needsUpdate = true;
    }
  }


  public load( new_positions, index ) {
    while ( new_positions.length > this.seams[index].positions.length ) {
      this.addPoint(index);
    }
    // while ( new_positions.length < this.seam.positions.length ) {
    //   this.removePoint(index);
    // }
    for ( let i = 0; i < this.seams[index].positions.length; i ++ ) {
      this.seams[index].positions[ i ].copy( new_positions[ i ] );
    }
    this.updateSplineOutline(index);
  }

  public animate(): void {
    // We have to run this outside angular zones,
    // because it could trigger heavy changeDetection cycles.
    
    this.ngZone.runOutsideAngular(() => {
      if (document.readyState !== 'loading') {
        this.render();
      } else {
        window.addEventListener('DOMContentLoaded', () => {
          this.render();
        });
      }

      // window.addEventListener('resize', () => {
      //   this.resize();
      // });
    });
  }

  public getIndexSeam(points: any) {
    var index_point = -1;
    for (let index = 0; index < this.graph.vertices.length; index++) {
      const element = this.graph.vertices[index];
      if(element[0] === points[0] && element[1] === points[1] && element[2] === points[2]) {
        index_point = index;
        break;
      }
    }
    var seams_point = [];
    for (let index = 0; index < this.graph.seams.length; index++) {
      const element = this.graph.seams[index];
      for (let i = 0; i < element.length; i++) {
        const seam = element[i];
        if(seam === index_point ) {
          seams_point.push(index);
          break;
        }
      }
      
    }
    return seams_point;
  }

  public render(): void {
    this.frameId = requestAnimationFrame(() => {
      this.render();
    });
    this.renderer.render(this.scene, this.camera);
    this.updateSplineOutline;
  }


  //daca number e 1, atunci il trimite pe serverul din Java pentru stocare
  //daca number e 2, atunci il trimite pe serverul din Py pentru clusterizare
  public exportSpline(number: number) {
    console.log(this.seams);
    var graph = new Graph();
    const strplace = [];
    var noSaving = false;
    var points = [];
    var nodes = 0;
    var seams = [];

    //vertices
    for (let index = 0; index < this.no_seams; index++) {
      for ( let i = 0; i < this.seams[index].splinePointsLength; i ++ ) {
        const p = this.seams[index].splineHelperObjects[ i ].position;
        strplace.push( `pentru cusatura ${index + 1}, coordonatele x:${p.x}, y:${p.y}, z:${p.z})` );
        //verificam daca punctul nu exista deja in graf
        for (const point of points) { 
          if(point[0] === p.x && point[1] === p.y && point[2] === p.z) {
            noSaving = true;
            break;
          } else {
            noSaving = false;
          }
        }
        if(!noSaving) {
          points.push([p.x, p.y, p.z]);
          graph.vertices[nodes] = [p.x, p.y, p.z];
          nodes++;
        }
      }
    }

    //edges
    nodes = 0;
    for (const point of points) {
      var edges = [];
      for (let index = 0; index < this.no_seams; index++) {
        for ( let i = 0; i < this.seams[index].splinePointsLength; i ++ ) {
          const p = this.seams[index].splineHelperObjects[ i ].position;
          if (point[0] === p.x && point[1] === p.y && point[2] === p.z) {
            var neighbour;
              if (i === 0) {
                neighbour = this.seams[index].splineHelperObjects[ i+1 ].position;
                edges.push(this.findIndex(points, neighbour));
                break;
              } else if (i > 0 && i < this.seams[index].splinePointsLength - 1) {//e la mijloc si vrem vecinii stanga dreapta
                neighbour = this.seams[index].splineHelperObjects[ i-1 ].position;
                edges.push(this.findIndex(points, neighbour));
                neighbour = this.seams[index].splineHelperObjects[ i+1 ].position;
                edges.push(this.findIndex(points, neighbour));
                break;
              } else if (i === this.seams[index].splinePointsLength - 1) {
                neighbour = this.seams[index].splineHelperObjects[ i-1 ].position;
                edges.push(this.findIndex(points, neighbour));
                break;
              }
          }
        }
      }
      graph.edges[nodes] = edges;
      nodes++;
    }
    var garment_id = sessionStorage.getItem('garment_id');
    graph.id = garment_id;

    seams = this.getVerticesFromSeam(graph.vertices);
    graph.seams = seams;
    // console.log( strplace.join( ',\n' ) );
    console.log(graph);
    console.log(seams);
    const code = '[' + ( strplace.join( ',\n\t' ) ) + ']';
    prompt( 'copy and paste code', code );

    this.patternService.send3DGraph(graph, number).subscribe(
      (res) => {
        if(number == 2) {
          sessionStorage.setItem('identified_type', res.type);
          this.route.navigateByUrl('/edit');

        }
        console.log(res);
      }, 
      (error) => {
        // this.errorMessage = error.error.message;
        console.log(error.error.message);
      });
    
  }

  findIndex(array, point) {
    for (let index = 0; index < array.length; index++) {
      const element = array[index];
      if (element[0] === point.x && element[1] === point.y && element[2] === point.z) {
        return index;
      }
    }
    return -1;
  }

  getVerticesFromSeam(points): any {
    var seams = [];
    var noSaving = false;
    for (let index = 0; index < this.no_seams; index++) {
      var vertices = [];
      for ( let i = 0; i < this.seams[index].splinePointsLength; i ++ ) {
        const p = this.seams[index].splineHelperObjects[ i ].position;
        for (const point of points) { 
          if(point[0] === p.x && point[1] === p.y && point[2] === p.z) {
            noSaving = true;
            vertices.push(points.indexOf(point))
            break;
          } else {
            noSaving = false;
          }
        }
        
      }
      if(noSaving) {
        seams.push(vertices);
      }
    }
    return seams;
  }
}

