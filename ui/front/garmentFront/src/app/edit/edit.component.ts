import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.scss']
})
export class EditComponent implements OnInit {
  talie: number;
  sold: number;
  lungime: number;
  sold_talie: number;
  tip: string;
  model: string;
  selectedOption: string;
  disabled_bie: false;
  disabled_clos: false;
  disabled_dreapta: false;

  constructor() { }

  ngOnInit(): void {
    this.model = sessionStorage.getItem('identified_type');
    // if(this.model == 'error') {
      this.model = 'clos';
      this.talie = 80;
      // this.sold = 120;
      // this.sold_talie = 18;
      this.lungime = 70;
      // this.tip = 'custom';
      this.draw();
    // }
  }

  change_type(number: any) {
    if(number == 0) {
      this.model = 'dreapta';
    } else if (number == 1) {
      this.model = 'bie';
    } else if (number == 2) {
      this.model = 'clos';
    }
  }

  draw() {
    if(this.model == 'dreapta') {
      this.draw_dreapta();
    } else if (this.model == 'bie') {
      this.draw_bie();
    } else if (this.model == 'clos') {
      this.draw_clos();
    }
  }

  draw_bie() {
    const canvas = <HTMLCanvasElement> document.getElementById('DemoCanvas');
    if (canvas.getContext)
    {
      var start_horizontal = 20;
      var start_vertical = 20;
      var scale = 2;
      
      var angle_end;
      var center_x;
      var center_y;

      var length_dress = this.lungime * scale;
      var waist = this.talie * scale;
      var radius = waist / Math.PI;

      angle_end = 0.5;
      center_x = start_vertical;
      center_y = start_horizontal;

      var startAngle = 0;
      var endAngle = angle_end * Math.PI;
      var ctx = canvas.getContext('2d');
  
      // draw grid
      var w=600;
      var h=500;
      ctx.canvas.width  = w;
      ctx.canvas.height = h;
  
      ctx.strokeStyle = "#EEEDE7";
  
      for (let x=0;x<=w;x+=20) {
          for (let y=0;y<=h;y+=20) {
              ctx.moveTo(x, 0);
              ctx.lineTo(x, h);
              ctx.stroke();
              ctx.moveTo(0, y);
              ctx.lineTo(w, y);
              ctx.stroke();
          }
      }
  
      ctx.stroke();

      //linii ajutatoare
      ctx.strokeStyle = "#ea8c86";
      ctx.beginPath();
      ctx.moveTo(start_vertical, start_horizontal);
      ctx.lineTo(start_vertical, start_horizontal + length_dress + radius);
      ctx.lineTo(start_vertical  + length_dress + radius, start_horizontal + length_dress + radius);
      ctx.lineTo(start_vertical  + length_dress + radius, start_horizontal);
      ctx.lineTo(start_vertical, start_horizontal);
      // ctx.moveTo(start_vertical, start_horizontal + length_dress + radius);
      // ctx.lineTo(start_vertical + 2 * length_dress + 2 * radius, start_horizontal + length_dress + radius)
      ctx.stroke();

      //tiparul
      var counterClockwise = false;
        ctx.beginPath();
        ctx.strokeStyle = "#0A0708";
        ctx.arc(center_x, center_y, length_dress + radius, startAngle, endAngle, counterClockwise);
        ctx.lineWidth = 1;
        ctx.stroke();
      
        ctx.beginPath();
        ctx.strokeStyle = "#0A0708";
        ctx.arc(center_x, center_y, radius, startAngle, endAngle, counterClockwise);
        ctx.lineWidth = 1;
        ctx.stroke();

        // liniile de unesc materialul
        ctx.beginPath();
        ctx.moveTo(start_vertical + radius, start_horizontal);
        ctx.lineTo(start_vertical + radius + length_dress, start_horizontal);
        ctx.moveTo(start_vertical, start_horizontal  + radius);
        ctx.lineTo(start_vertical, start_horizontal + radius + length_dress);
        ctx.stroke();
       
        //fermoar
        var start_point_fermoar = start_vertical;
        var latime_fermoar = 5;
        var lungime_fermoar = 20 * scale;
        ctx.beginPath();
        ctx.moveTo(start_point_fermoar  + radius, start_horizontal);
        ctx.lineTo(start_point_fermoar + lungime_fermoar  + radius, start_horizontal);
        ctx.lineTo(start_point_fermoar + lungime_fermoar  + radius, start_horizontal - latime_fermoar);
        ctx.lineTo(start_point_fermoar  + radius, start_horizontal - latime_fermoar);
        ctx.lineTo(start_point_fermoar  + radius, start_horizontal);
        ctx.stroke();
    }
  }

  draw_clos() {
    const canvas = <HTMLCanvasElement> document.getElementById('DemoCanvas');
    if (canvas.getContext)
    {
      var start_horizontal = 20;
      var start_vertical = 20;
      var scale = 2;
      
      var angle_end;
      var center_x;
      var center_y;

      var length_dress = this.lungime * scale;
      var waist = this.talie * scale;
      var radius = waist / Math.PI;

      if (this.selectedOption == '1') {
        angle_end = 2;
        center_x = start_vertical + length_dress + radius;
        center_y = start_horizontal + length_dress + radius;
      } else if (this.selectedOption == '2') {
        angle_end = 1;
        center_x = start_vertical + length_dress + radius;
        center_y = start_horizontal;
      } else if (this.selectedOption == '3') {
        angle_end = 1.5;
        center_x = start_vertical + length_dress + radius;
        center_y = start_horizontal + length_dress + radius;
      }

      var startAngle = 0;
      var endAngle = angle_end * Math.PI;
      var ctx = canvas.getContext('2d');
  
      // draw grid
      var w=600;
      var h=500;
      ctx.canvas.width  = w;
      ctx.canvas.height = h;
  
      // ctx.strokeStyle = "#EEEDE7";
  
      // for (let x=0;x<=w;x+=20) {
      //     for (let y=0;y<=h;y+=20) {
      //         ctx.moveTo(x, 0);
      //         ctx.lineTo(x, h);
      //         ctx.stroke();
      //         ctx.moveTo(0, y);
      //         ctx.lineTo(w, y);
      //         ctx.stroke();
      //     }
      // }
  
      // ctx.stroke();

      //linii ajutatoare
      // ctx.strokeStyle = "#ea8c86";
      // ctx.beginPath();
      // ctx.moveTo(start_vertical, start_horizontal);
      // ctx.lineTo(start_vertical, start_horizontal + 2 * length_dress + 2 * radius);
      // ctx.lineTo(start_vertical  + 2 * length_dress + 2 * radius, start_horizontal + 2 * length_dress + 2 * radius);
      // ctx.lineTo(start_vertical  + 2 * length_dress + 2 * radius, start_horizontal);
      // ctx.lineTo(start_vertical, start_horizontal);
      // ctx.moveTo(start_vertical, start_horizontal + length_dress + radius);
      // ctx.lineTo(start_vertical + 2 * length_dress + 2 * radius, start_horizontal + length_dress + radius)
      // ctx.stroke();

      //cercurile si semicercurile
      var counterClockwise = false;
        ctx.beginPath();
        ctx.strokeStyle = "#0A0708";
        console.log(center_x, center_y, length_dress + radius, startAngle, endAngle);
        ctx.arc(center_x, center_y, length_dress + radius, startAngle, endAngle, counterClockwise);
        ctx.lineWidth = 3;
        ctx.stroke();
      
        ctx.beginPath();
        ctx.strokeStyle = "#0A0708";
        ctx.arc(center_x, center_y, radius, startAngle, endAngle, counterClockwise);
        ctx.lineWidth = 3;
        ctx.stroke();

        // liniile de unesc materialul
        if (this.selectedOption == '2') {
          ctx.beginPath();
          ctx.moveTo(start_vertical, start_horizontal);
          ctx.lineTo(start_vertical + length_dress, start_horizontal);
          ctx.moveTo(start_vertical + length_dress + 2 * radius, start_horizontal);
          ctx.lineTo(start_vertical + 2 * length_dress + 2 * radius, start_horizontal);
          ctx.stroke();
        } else if (this.selectedOption == '3') {
          ctx.beginPath();
          ctx.moveTo(start_vertical + length_dress + radius, start_horizontal);
          ctx.lineTo(start_vertical + length_dress + radius, start_horizontal + length_dress);
          ctx.moveTo(start_vertical + length_dress + 2 * radius, start_horizontal + length_dress + radius);
          ctx.lineTo(start_vertical + 2 * length_dress + 2 * radius, start_horizontal + length_dress + radius);
          ctx.stroke();
        }
        //fermoar
        var start_point_fermoar = start_vertical + length_dress + 2 * radius + 5;
        var latime_fermoar = 5;
        var lungime_fermoar = 20 * scale;
        if(this.selectedOption == '1' || this.selectedOption == '3') {
          ctx.beginPath();
          ctx.moveTo(start_point_fermoar, start_horizontal + length_dress + radius);
          ctx.lineTo(start_point_fermoar + lungime_fermoar, start_horizontal + length_dress + radius);
          ctx.lineTo(start_point_fermoar + lungime_fermoar, start_horizontal + length_dress + radius - latime_fermoar);
          ctx.lineTo(start_point_fermoar, start_horizontal + length_dress + radius - latime_fermoar);
          ctx.lineTo(start_point_fermoar, start_horizontal + length_dress + radius);
          ctx.stroke();
        } else if (this.selectedOption == '2') {
          ctx.beginPath();
          ctx.moveTo(start_point_fermoar, start_horizontal);
          ctx.lineTo(start_point_fermoar + lungime_fermoar, start_horizontal);
          ctx.lineTo(start_point_fermoar + lungime_fermoar, start_horizontal - latime_fermoar);
          ctx.lineTo(start_point_fermoar, start_horizontal - latime_fermoar);
          ctx.lineTo(start_point_fermoar, start_horizontal);
          ctx.stroke();
        }
      
    }
  }
  
  draw_dreapta() {

    const canvas = <HTMLCanvasElement> document.getElementById('DemoCanvas');

  if (canvas.getContext)
  {
    var start_horizontal = 20;
    var start_vertical = 0;

    var scale = 5;
    var hip = this.sold / 4 * scale + start_horizontal;
    var waist = this.talie / 4 * scale + start_horizontal;

    var dist_waist_to_hip = this.sold_talie * scale + start_horizontal;
    var lenght_dress = this.lungime * scale + start_horizontal;

    var a;
    var draft = 1.5;
    if(this.talie >= 60 && this.talie < 70) {
      a = 6;
      draft = 1;
    } else if(this.talie >= 70 && this.talie < 80) {
      a = 7;
    } else if (this.talie >= 80 && this.talie < 90) {
      a = 8;
    } else if (this.talie >= 90 && this.talie < 100) {
      a = 9;
    }
    var pana_la_pensa = a * scale + start_horizontal;
    var length_pense = 8 * scale + start_horizontal;
    draft = draft * scale + start_horizontal;
    var ctx = canvas.getContext('2d');

    // draw grid
    var w=600;
    var h=500;
    ctx.canvas.width  = w;
    ctx.canvas.height = h;

    // ctx.strokeStyle = "#EEEDE7";

    // for (let x=0;x<=w;x+=20) {
    //     for (let y=0;y<=h;y+=20) {
    //         ctx.moveTo(x, 0);
    //         ctx.lineTo(x, h);
    //         ctx.stroke();
    //         ctx.moveTo(0, y);
    //         ctx.lineTo(w, y);
    //         ctx.stroke();
    //     }
    // }

    ctx.strokeStyle = "#ea8c86";
  
    if(this.tip === 'custom'){
      ctx.beginPath();
      ctx.moveTo(start_vertical, start_horizontal);
      ctx.lineTo(start_vertical, lenght_dress);
      ctx.lineTo(hip, lenght_dress);
      ctx.lineTo(hip, start_horizontal);
      ctx.lineTo(start_vertical, start_horizontal);
      ctx.moveTo(start_vertical, dist_waist_to_hip);
      ctx.lineTo(hip, dist_waist_to_hip);
      ctx.fillRect(waist + draft,start_horizontal,2,2);
      ctx.fillRect(pana_la_pensa,start_horizontal,2,2);
      ctx.fillRect(pana_la_pensa + draft,start_horizontal,2,2);
      ctx.fillRect(draft / 2 + pana_la_pensa, start_horizontal + length_pense,2,2);
      ctx.fillRect(hip,dist_waist_to_hip,2,2);
      ctx.stroke();

      // partea din fata
      ctx.beginPath();
      ctx.strokeStyle = "#0A0708";
      ctx.lineWidth = 1;

      ctx.moveTo(pana_la_pensa, start_horizontal);
      ctx.lineTo(pana_la_pensa + draft / 2, length_pense + start_horizontal);
      ctx.lineTo(pana_la_pensa + draft, start_horizontal);
      ctx.moveTo(start_vertical, start_horizontal);
      ctx.lineTo(start_vertical, lenght_dress);
      ctx.moveTo(start_vertical, start_horizontal);
      ctx.lineTo(pana_la_pensa, start_horizontal);
      ctx.moveTo(start_vertical, lenght_dress);
      ctx.lineTo(hip / 2, lenght_dress);
 

      ctx.fillRect(waist + draft + start_horizontal / 10, start_horizontal / 2,2,2);
      ctx.fillRect(pana_la_pensa + draft + start_horizontal / 8, 8 * start_horizontal / 10, 2, 2);
      ctx.stroke();

      // ridicare pensa
      ctx.beginPath();
      ctx.strokeStyle = "#green";
      ctx.moveTo(pana_la_pensa + draft, start_horizontal);
      ctx.lineTo(pana_la_pensa + draft + start_horizontal / 8, 8 * start_horizontal / 10);
      ctx.quadraticCurveTo(waist + draft + 3 * (hip - waist - draft) / 4, 7 * start_horizontal / 10, waist + draft + start_horizontal / 12, start_horizontal / 2)
      ctx.quadraticCurveTo(waist + draft + 3 * (hip - waist - draft) / 4, dist_waist_to_hip / 2, hip, dist_waist_to_hip);

      // ridica cant
      ctx.fillRect(hip + 6 * start_horizontal / 10, lenght_dress - 6 * start_horizontal / 10, 2, 2);
      ctx.moveTo(hip + 6 * start_horizontal / 10, lenght_dress - 6 * start_horizontal / 10);
      ctx.quadraticCurveTo(waist + draft + 3 * (hip - waist - draft) / 4, lenght_dress, hip / 2, lenght_dress);
      ctx.moveTo(hip + 6 * start_horizontal / 10, lenght_dress - 6 * start_horizontal / 10);
      ctx.lineTo(hip, dist_waist_to_hip);
      ctx.stroke();


      // tipar spate
      var length_pense = 13* scale + start_horizontal;

      start_vertical = hip + 30;

      ctx.beginPath();
      ctx.strokeStyle = "#ea8c86";

      ctx.moveTo(start_vertical, start_horizontal);
      ctx.lineTo(start_vertical, lenght_dress);
      ctx.lineTo(start_vertical + hip, lenght_dress);
      ctx.lineTo(start_vertical + hip, start_horizontal);
      ctx.lineTo(start_vertical, start_horizontal);
      ctx.moveTo(start_vertical, dist_waist_to_hip);
      ctx.lineTo(start_vertical + hip, dist_waist_to_hip);
      ctx.fillRect(start_vertical + hip - waist - draft,start_horizontal,2,2);
      ctx.fillRect(start_vertical + hip - pana_la_pensa,start_horizontal,2,2);
      ctx.fillRect(start_vertical + hip - pana_la_pensa - draft,start_horizontal,2,2);
      ctx.fillRect(start_vertical + hip - draft / 2 - pana_la_pensa, start_horizontal + length_pense,2,2);
      ctx.fillRect(start_vertical + hip, dist_waist_to_hip,2,2);
      ctx.stroke();

      ctx.beginPath();
      ctx.strokeStyle = "#0A0708";
      ctx.lineWidth = 1;
      
      ctx.moveTo(start_vertical + hip, start_horizontal);
      ctx.lineTo(start_vertical + hip, lenght_dress);
      ctx.lineTo(start_vertical + hip / 2, lenght_dress);
      ctx.moveTo(start_vertical + hip, start_horizontal);
      ctx.lineTo(start_vertical + hip - pana_la_pensa, start_horizontal);
      ctx.lineTo(start_vertical + hip - pana_la_pensa - draft / 2, start_horizontal + length_pense);
      ctx.lineTo(start_vertical + hip - pana_la_pensa - draft, start_horizontal);

      //ridicare pensa
      ctx.moveTo(start_vertical + hip - pana_la_pensa - draft, start_horizontal);
      ctx.lineTo(start_vertical + hip - pana_la_pensa - draft - start_horizontal / 8, 8 * start_horizontal / 10);
      ctx.quadraticCurveTo(start_vertical + hip - waist - draft, 7 * start_horizontal / 10, start_vertical + hip - waist - draft + start_horizontal / 12, start_horizontal / 2)
      ctx.quadraticCurveTo(start_vertical + 3 * start_horizontal / 10, dist_waist_to_hip / 2, start_vertical, dist_waist_to_hip);


      //asta e pentru cea basic
      // ctx.lineTo(start_vertical + hip - waist - draft, start_horizontal);

      // ridicare cant rochie
      ctx.moveTo(hip + 6 * start_horizontal / 10, lenght_dress - 6 * start_horizontal / 10);
      ctx.quadraticCurveTo(start_vertical, lenght_dress, start_vertical + hip / 2, lenght_dress );
      ctx.moveTo(hip + 6 * start_horizontal / 10, lenght_dress - 6 * start_horizontal / 10);
      ctx.lineTo(start_vertical, dist_waist_to_hip );
 

      ctx.fillRect(waist + draft + start_horizontal / 10, start_horizontal / 2,2,2);
      ctx.fillRect(pana_la_pensa + draft + start_horizontal / 8, 8 * start_horizontal / 10, 2, 2);
      ctx.stroke();


      ctx.lineWidth = 2;
      ctx.stroke();
    } else if (this.tip === 'dreapta') {
      // ctx.beginPath();
      // ctx.moveTo(start_vertical, start_horizontal);
      // ctx.lineTo(start_vertical, lenght_dress);
      // ctx.lineTo(hip, lenght_dress);
      // ctx.lineTo(hip, start_horizontal);
      // ctx.lineTo(start_vertical, start_horizontal);
      // ctx.moveTo(start_vertical, dist_waist_to_hip);
      // ctx.lineTo(hip, dist_waist_to_hip);
      // ctx.fillRect(waist + draft,start_horizontal,2,2);
      // ctx.fillRect(pana_la_pensa,start_horizontal,2,2);
      // ctx.fillRect(pana_la_pensa + draft,start_horizontal,2,2);
      // ctx.fillRect(draft / 2 + pana_la_pensa, start_horizontal + length_pense,2,2);
      // ctx.fillRect(hip,dist_waist_to_hip,2,2);
      // ctx.stroke();
  
      // ctx.beginPath();
      ctx.beginPath();
      ctx.strokeStyle = "#0A0708";
      ctx.lineWidth = 3;
      // ctx.setLineDash([5, 3]);
  
      ctx.moveTo(pana_la_pensa, start_horizontal);
      ctx.lineTo(pana_la_pensa + draft / 2, length_pense + start_horizontal);
      ctx.lineTo(pana_la_pensa + draft, start_horizontal);
      ctx.moveTo(start_vertical, start_horizontal);
      ctx.lineTo(start_vertical, lenght_dress);
      ctx.lineTo(hip, lenght_dress);
      ctx.moveTo(start_vertical, start_horizontal);
      ctx.lineTo(pana_la_pensa, start_horizontal);
      ctx.moveTo(pana_la_pensa + draft, start_horizontal);
      ctx.lineTo(draft + waist, start_horizontal);
      
      ctx.quadraticCurveTo(waist + draft + 3 * (hip - waist - draft) / 4, dist_waist_to_hip / 2, hip, dist_waist_to_hip);
      ctx.moveTo(hip, dist_waist_to_hip);
      ctx.lineTo(hip, lenght_dress);      
      ctx.lineWidth = 3;
  
      ctx.stroke();

      // tipar spate
      var length_pense = 13* scale + start_horizontal;

      start_vertical = hip + 30;

      // ctx.beginPath();
      // ctx.strokeStyle = "#ea8c86";

      // ctx.moveTo(start_vertical, start_horizontal);
      // ctx.lineTo(start_vertical, lenght_dress);
      // ctx.lineTo(start_vertical + hip, lenght_dress);
      // ctx.lineTo(start_vertical + hip, start_horizontal);
      // ctx.lineTo(start_vertical, start_horizontal);
      // ctx.moveTo(start_vertical, dist_waist_to_hip);
      // ctx.lineTo(start_vertical + hip, dist_waist_to_hip);
      // ctx.fillRect(start_vertical + hip - waist - draft,start_horizontal,2,2);
      // ctx.fillRect(start_vertical + hip - pana_la_pensa,start_horizontal,2,2);
      // ctx.fillRect(start_vertical + hip - pana_la_pensa - draft,start_horizontal,2,2);
      // ctx.fillRect(start_vertical + hip - draft / 2 - pana_la_pensa, start_horizontal + length_pense,2,2);
      // ctx.fillRect(start_vertical + hip, dist_waist_to_hip,2,2);
      // ctx.stroke();

      ctx.beginPath();
      ctx.strokeStyle = "#0A0708";
      ctx.lineWidth = 3;
      
      ctx.moveTo(start_vertical + hip, start_horizontal);
      ctx.lineTo(start_vertical + hip, lenght_dress);
      ctx.lineTo(start_vertical, lenght_dress);
      ctx.moveTo(start_vertical + hip, start_horizontal);
      ctx.lineTo(start_vertical + hip - pana_la_pensa, start_horizontal);
      ctx.lineTo(start_vertical + hip - pana_la_pensa - draft / 2, start_horizontal + length_pense);
      ctx.lineTo(start_vertical + hip - pana_la_pensa - draft, start_horizontal);
      ctx.moveTo(start_vertical, dist_waist_to_hip);
      ctx.lineTo(start_vertical, lenght_dress);

      ctx.moveTo(start_vertical + hip - pana_la_pensa - draft, start_horizontal);
      ctx.lineTo(start_vertical + hip - waist - draft, start_horizontal);
      ctx.quadraticCurveTo(start_vertical + 3 * start_horizontal / 10, dist_waist_to_hip / 2, start_vertical, dist_waist_to_hip);
 
      ctx.lineWidth = 3;
      ctx.stroke();
    } else if (this.tip === 'conica') {
      ctx.beginPath();
      ctx.moveTo(start_vertical, start_horizontal);
      ctx.lineTo(start_vertical, lenght_dress);
      ctx.lineTo(hip, lenght_dress);
      ctx.lineTo(hip, start_horizontal);
      ctx.lineTo(start_vertical, start_horizontal);
      ctx.moveTo(start_vertical, dist_waist_to_hip);
      ctx.lineTo(hip, dist_waist_to_hip);
      ctx.fillRect(waist + draft,start_horizontal,2,2);
      ctx.fillRect(pana_la_pensa,start_horizontal,2,2);
      ctx.fillRect(pana_la_pensa + draft,start_horizontal,2,2);
      ctx.fillRect(draft / 2 + pana_la_pensa, start_horizontal + length_pense,2,2);
      ctx.fillRect(hip,dist_waist_to_hip,2,2);
      ctx.stroke();
  
      // ctx.beginPath();
      ctx.beginPath();
      ctx.strokeStyle = "#0A0708";
      ctx.lineWidth = 1;
  
      ctx.moveTo(pana_la_pensa, start_horizontal);
      ctx.lineTo(pana_la_pensa + draft / 2, length_pense + start_horizontal);
      ctx.lineTo(pana_la_pensa + draft, start_horizontal);
      ctx.moveTo(start_vertical, start_horizontal);
      ctx.lineTo(start_vertical, lenght_dress);
    
      ctx.lineTo(hip - draft / 2, lenght_dress);
      ctx.moveTo(start_vertical, start_horizontal);
      ctx.lineTo(pana_la_pensa, start_horizontal);
      ctx.moveTo(pana_la_pensa + draft, start_horizontal);
      ctx.lineTo(draft + waist, start_horizontal);
      
      ctx.quadraticCurveTo(waist + draft + 3 * (hip - waist - draft) / 4, dist_waist_to_hip / 2, hip, dist_waist_to_hip);
      ctx.moveTo(hip, dist_waist_to_hip);
      ctx.quadraticCurveTo(hip, lenght_dress - draft / 2, hip - draft / 2, lenght_dress);      
      ctx.lineWidth = 1;
  
      ctx.stroke();

      // tipar spate
      var length_pense = 13* scale + start_horizontal;

      start_vertical = hip + 30;

      ctx.beginPath();
      ctx.strokeStyle = "#ea8c86";

      ctx.moveTo(start_vertical, start_horizontal);
      ctx.lineTo(start_vertical, lenght_dress);
      ctx.lineTo(start_vertical + hip, lenght_dress);
      ctx.lineTo(start_vertical + hip, start_horizontal);
      ctx.lineTo(start_vertical, start_horizontal);
      ctx.moveTo(start_vertical, dist_waist_to_hip);
      ctx.lineTo(start_vertical + hip, dist_waist_to_hip);
      ctx.fillRect(start_vertical + hip - waist - draft,start_horizontal,2,2);
      ctx.fillRect(start_vertical + hip - pana_la_pensa,start_horizontal,2,2);
      ctx.fillRect(start_vertical + hip - pana_la_pensa - draft,start_horizontal,2,2);
      ctx.fillRect(start_vertical + hip - draft / 2 - pana_la_pensa, start_horizontal + length_pense,2,2);
      ctx.fillRect(start_vertical + hip, dist_waist_to_hip,2,2);
      ctx.stroke();

      ctx.beginPath();
      ctx.strokeStyle = "#0A0708";
      ctx.lineWidth = 1;
      
      ctx.moveTo(start_vertical + hip, start_horizontal);
      ctx.lineTo(start_vertical + hip, lenght_dress);
      ctx.lineTo(start_vertical + draft / 2, lenght_dress);
      ctx.moveTo(start_vertical + hip, start_horizontal);
      ctx.lineTo(start_vertical + hip - pana_la_pensa, start_horizontal);
      ctx.lineTo(start_vertical + hip - pana_la_pensa - draft / 2, start_horizontal + length_pense);
      ctx.lineTo(start_vertical + hip - pana_la_pensa - draft, start_horizontal);
      ctx.moveTo(start_vertical, dist_waist_to_hip);
      ctx.quadraticCurveTo(start_vertical, lenght_dress - draft / 2, start_vertical + draft / 2, lenght_dress);

      ctx.moveTo(start_vertical + hip - pana_la_pensa - draft, start_horizontal);
      ctx.lineTo(start_vertical + hip - waist - draft, start_horizontal);
      ctx.quadraticCurveTo(start_vertical + 3 * start_horizontal / 10, dist_waist_to_hip / 2, start_vertical, dist_waist_to_hip);
 
      ctx.lineWidth = 1;
      ctx.stroke();
    } else if (this.tip === 'evazata') {
      ctx.beginPath();
      ctx.moveTo(start_vertical, start_horizontal);
      ctx.lineTo(start_vertical, lenght_dress);
      ctx.lineTo(hip, lenght_dress);
      ctx.lineTo(hip, start_horizontal);
      ctx.lineTo(start_vertical, start_horizontal);
      ctx.moveTo(start_vertical, dist_waist_to_hip);
      ctx.lineTo(hip, dist_waist_to_hip);
      ctx.fillRect(waist + draft,start_horizontal,2,2);
      ctx.fillRect(pana_la_pensa,start_horizontal,2,2);
      ctx.fillRect(pana_la_pensa + draft,start_horizontal,2,2);
      ctx.fillRect(draft / 2 + pana_la_pensa, start_horizontal + length_pense,2,2);
      ctx.fillRect(hip,dist_waist_to_hip,2,2);
      ctx.stroke();
  
      ctx.beginPath();
      ctx.strokeStyle = "#0A0708";
      ctx.lineWidth = 1;
  
      ctx.moveTo(pana_la_pensa, start_horizontal);
      ctx.lineTo(pana_la_pensa + draft / 2, length_pense + start_horizontal);
      ctx.lineTo(pana_la_pensa + draft, start_horizontal);
      ctx.moveTo(start_vertical, start_horizontal);
      ctx.lineTo(start_vertical, lenght_dress);
    
      ctx.lineTo(hip + draft, lenght_dress);
      ctx.moveTo(start_vertical, start_horizontal);
      ctx.lineTo(pana_la_pensa, start_horizontal);
      ctx.moveTo(pana_la_pensa + draft, start_horizontal);
      ctx.lineTo(draft + waist, start_horizontal);
      
      ctx.quadraticCurveTo(waist + draft + 3 * (hip - waist - draft) / 4, dist_waist_to_hip / 2, hip, dist_waist_to_hip);
      ctx.moveTo(hip, dist_waist_to_hip);
      ctx.quadraticCurveTo(hip, lenght_dress - draft, hip + draft, lenght_dress);      
      ctx.lineWidth = 1;
  
      ctx.stroke();

      // tipar spate
      var length_pense = 13* scale + start_horizontal;

      start_vertical = hip + 30 + draft;

      ctx.beginPath();
      ctx.strokeStyle = "#ea8c86";

      ctx.moveTo(start_vertical, start_horizontal);
      ctx.lineTo(start_vertical, lenght_dress);
      ctx.lineTo(start_vertical + hip, lenght_dress);
      ctx.lineTo(start_vertical + hip, start_horizontal);
      ctx.lineTo(start_vertical, start_horizontal);
      ctx.moveTo(start_vertical, dist_waist_to_hip);
      ctx.lineTo(start_vertical + hip, dist_waist_to_hip);
      ctx.fillRect(start_vertical + hip - waist - draft,start_horizontal,2,2);
      ctx.fillRect(start_vertical + hip - pana_la_pensa,start_horizontal,2,2);
      ctx.fillRect(start_vertical + hip - pana_la_pensa - draft,start_horizontal,2,2);
      ctx.fillRect(start_vertical + hip - draft / 2 - pana_la_pensa, start_horizontal + length_pense,2,2);
      ctx.fillRect(start_vertical + hip, dist_waist_to_hip,2,2);
      ctx.stroke();

      ctx.beginPath();
      ctx.strokeStyle = "#0A0708";
      ctx.lineWidth = 1;
      
      ctx.moveTo(start_vertical + hip, start_horizontal);
      ctx.lineTo(start_vertical + hip, lenght_dress);
      ctx.lineTo(start_vertical - draft, lenght_dress);
      ctx.moveTo(start_vertical + hip, start_horizontal);
      ctx.lineTo(start_vertical + hip - pana_la_pensa, start_horizontal);
      ctx.lineTo(start_vertical + hip - pana_la_pensa - draft / 2, start_horizontal + length_pense);
      ctx.lineTo(start_vertical + hip - pana_la_pensa - draft, start_horizontal);
      ctx.moveTo(start_vertical, dist_waist_to_hip);
      ctx.quadraticCurveTo(start_vertical, lenght_dress - draft, start_vertical - draft, lenght_dress);

      ctx.moveTo(start_vertical + hip - pana_la_pensa - draft, start_horizontal);
      ctx.lineTo(start_vertical + hip - waist - draft, start_horizontal);
      ctx.quadraticCurveTo(start_vertical + 3 * start_horizontal / 10, dist_waist_to_hip / 2, start_vertical, dist_waist_to_hip);
 
      ctx.lineWidth = 1;
      ctx.stroke();
    } 

    //fermoar
    var start_point_fermoar = 2 * hip + 30;
    if(this.tip === 'evazata'){
      start_point_fermoar += draft;
    }
    var latime_fermoar = 5;
    ctx.beginPath();
    ctx.moveTo(start_point_fermoar, start_horizontal);
    ctx.lineTo(start_point_fermoar, start_horizontal / 2 + dist_waist_to_hip);
    ctx.lineTo(start_point_fermoar + latime_fermoar, start_horizontal / 2 + dist_waist_to_hip);
    ctx.lineTo(start_point_fermoar + latime_fermoar, start_horizontal);
    ctx.lineTo(start_point_fermoar, start_horizontal);
    ctx.stroke();
  } else {
    alert('Not supported in this browser.');
  }
  }

}
