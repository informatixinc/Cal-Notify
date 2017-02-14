import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';
import {GoogleChart} from '../../../../node_modules/chart.js/node_modules/angular2-google-chart/directives/angular2-google-chart.directive';

declare var google: any;

@Component({
  selector: 'reports',
  templateUrl: './reports.html',
  styleUrls: ['./reports.css'],
  providers: [],
})

export class Reports {

	constructor(private router: Router, private _languageService: LanguageService ) {}

	ngOnInit(){
		google.charts.load('current', {packages: ['corechart', 'bar']});
		google.charts.setOnLoadCallback(this.drawBasic);
	}

	drawBasic() {

      var data = google.visualization.arrayToDataTable([
        ['', 'Messages',],
        [new Date("2017-02-14"), 15],
        [new Date("2017-02-13"), 5],
        [new Date("2017-02-12"), 23],
        [new Date("2017-02-11"), 2],
        [new Date("2017-02-10"), 37]
      ]);

      var options = {
        title: 'Messages sent per day',
        chartArea: {width: '50%'},
        hAxis: {
          title: 'Messages Sent',
          minValue: 0
        },
        vAxis: {
          title: 'Date'
        }
      };

      var chart = new google.visualization.BarChart(document.getElementById('chart_div'));

      chart.draw(data, options);
    }
}
