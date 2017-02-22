import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';

declare var google: any;
declare var $:any;

@Component({
  selector: 'reports',
  templateUrl: './reports.html',
  styleUrls: ['./reports.css'],
  providers: [],
})

export class Reports {

	constructor(private router: Router, private _languageService: LanguageService ) {}

	ngOnInit(){
    google.charts.load('current', {'packages':['bar', 'corechart', 'gauge']});
    this.drawGuage();
    this.drawPie();
    this.drawBar();
	}

  drawBar(){
    google.charts.setOnLoadCallback(this.drawBarChart);
  }

  drawBarChart(){
    var data = google.visualization.arrayToDataTable([
          ['Catagory', 'User Activity'],
          ['NEW', 450],
          ['ACTIVE', 1170],
          ['DELETED', 10],
          ['INACTIVE', 1030]
        ]);

        var options = {
          chart: {
            title: '',
          },
          chartArea:{left:"5%",top:"5%",width:"90%",height:"90%", backgroundColor: 'transparent'},
          height: document.getElementById("notifications-gauge").offsetHeight,
          backgroundColor: 'transparent',
          legend: {position: 'none'}
        };

        var chart = new google.charts.Bar(document.getElementById('notifications-bar'));

        chart.draw(data, options);
  }

  drawPie(){
    google.charts.setOnLoadCallback(this.drawPieChart);
  }

  drawPieChart(){
    var data = google.visualization.arrayToDataTable([
          ['Task', 'Notifications by type'],
          ['Flood Watch',11],
          ['Gale Watch',2],
          ['Winter Storm Watch',  2],
          ['Brisk Wind Advisory', 2],
          ['Storm Watch',7]
        ]);

        var options = {
          title: '',
          width: document.getElementById("notifications-gauge").offsetWidth, 
          height: document.getElementById("notifications-gauge").offsetWidth,
          chartArea:{left:"5%",top:"5%",width:"90%",height:"90%"},
          backgroundColor: 'transparent',
          legend: {position: 'none'}
        };

        var chart = new google.visualization.PieChart(document.getElementById('notifications-pie'));

        chart.draw(data, options);
  }

  drawGuage(){
    google.charts.setOnLoadCallback(this.drawGaugeChart);
  }

  drawGaugeChart() {
    var gaugeOptions = {min: 0, max: 500, yellowFrom: 500, yellowTo: 500, redFrom: 500, redTo: 500,  
      width: document.getElementById("notifications-gauge").offsetWidth, 
      height: document.getElementById("notifications-gauge").offsetWidth,
      minorTicks: 5};
    var gaugeData = new google.visualization.DataTable();
    gaugeData.addColumn('number', '');
    gaugeData.addRows(1);
    gaugeData.setCell(0, 0, 0);

    var gauge = new google.visualization.Gauge(document.getElementById('notifications-gauge'));
    gauge.draw(gaugeData, gaugeOptions);
    gaugeData.setValue(0, 0, 280);
    gauge.draw(gaugeData, gaugeOptions);
  }

}
