import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';
import {ApiRequest} from '../../services/http/api_request';
import {Session} from '../common/session';
import {UserState} from '../../services/user_state/user_state';
import {Notification} from '../common/notification';

declare var google: any;

@Component({
  selector: 'reports',
  templateUrl: './reports.html',
  styleUrls: ['./reports.css'],
  providers: [],
})

export class Reports {

  groupings = {};
  gaugeData = 0;
  sourcesData: any;
  notificationData: string[] = [];
  sourceData: string[] = [];
  userData: any[] = [];

	constructor(private router: Router, private _languageService: LanguageService, private _apiRequest: ApiRequest, private _userState: UserState ) {}

	ngOnInit(){
    google.charts.load('current', {'packages':['bar', 'corechart', 'gauge']});
    var session = new Session();
    session.session = this._userState.getSession();
    this._apiRequest.doRequest('adminmessagereport',session).subscribe(res => this.processDataNotificationReports(res));
    this._apiRequest.doRequest('accountreport',session).subscribe(res => this.processAccountReports(res));
	}

  processAccountReports(reportData: any){
    var highestYear = 0;
    var highestMonth = 0;
    var highestYearMinusOne = 0;
    var highestMonthMinusOne = 0;

    for(var key in reportData.reportData){
      if(parseInt(key) > highestYear){
        highestYear = parseInt(key);
      }
    }

    for(var key in reportData.reportData[highestYear]){
        if(parseInt(key) > highestMonth){
          highestMonth = parseInt(key);
        }
    }

    if(highestMonth == 1){
      highestYearMinusOne = highestYear - 1;
      highestMonthMinusOne = 12
    }else{
      highestYearMinusOne = highestYear;
      highestMonthMinusOne = highestMonth - 1;
    }

    this.userData.push(['', 'This Month', 'Last Month']);
    this.userData.push(['New', reportData.reportData[highestYear][highestMonth][0].count, reportData.reportData[highestYearMinusOne][highestMonthMinusOne][0].count]);
    this.userData.push(['Active', reportData.reportData[highestYear][highestMonth][1].count, reportData.reportData[highestYearMinusOne][highestMonthMinusOne][1].count]);
    this.userData.push(['Inactive', reportData.reportData[highestYear][highestMonth][2].count, reportData.reportData[highestYearMinusOne][highestMonthMinusOne][2].count]);
    console.log(this.userData);
    this.drawBar();
  }

  processDataNotificationReports(reportData: any){
    var highestYear = 0;
    var highestMonth = 0;
    var years = [];

    for (var i = 0; i < reportData.length; ++i) {
      var messageDate = new Date(reportData[i].sendTime);
      if (!(messageDate.getFullYear() in this.groupings)){
        this.groupings[messageDate.getFullYear()] = {};
        years.push(messageDate.getFullYear());
        if(messageDate.getFullYear() > highestYear){
          highestYear = messageDate.getFullYear();
        }
      }
      if (!(messageDate.getMonth()+1 in this.groupings[messageDate.getFullYear()])){
        this.groupings[messageDate.getFullYear()][messageDate.getMonth()+1] = [];
      }

      this.groupings[messageDate.getFullYear()][messageDate.getMonth()+1].push(reportData[i]);
    }
    for (var key in this.groupings[highestYear]) {
      if(highestMonth < parseInt(key)){
        highestMonth = parseInt(key);
      }
    }
    this.gaugeData = this.groupings[highestYear][highestMonth].length;
    var catagories = {};
    for (var i = 0; i < this.groupings[highestYear][highestMonth].length; ++i) {
      var notification = this.groupings[highestYear][highestMonth][i];
      if(!(notification.title in catagories)){
        catagories[notification.title] = 0;
        this.sourceData.push(notification.title);
      }
      catagories[notification.title] = catagories[notification.title] + 1;
    }

    this.sourcesData = [];
    this.sourcesData.push(['Task', 'Notifications by type']);
    for(var key in catagories){
      this.sourcesData.push([key, catagories[key]]);
    }

    years = years.sort().reverse();

    for (var i = 0; i < years.length; ++i) {
      for (var j = 13 - 1; j >= 1; j--) {
        if(j in this.groupings[years[i]]){
          if(String(j).length == 1){
            this.notificationData.push("0" + j + "/" + years[i]);
          }else{
            this.notificationData.push(j + "/" + years[i]);
          }
        }
      }
    }

    this.drawGuage();
    this.drawPie();
  }

  drawBar(){
    google.charts.setOnLoadCallback(this.drawBarChart.bind(this));
  }

  drawBarChart(){
    var data = google.visualization.arrayToDataTable(this.userData);

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
    google.charts.setOnLoadCallback(this.drawPieChart.bind(this));
  }

  drawPieChart(){
    var data = google.visualization.arrayToDataTable(this.sourcesData);

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
    google.charts.setOnLoadCallback(this.drawGaugeChart.bind(this));
  }

  drawGaugeChart() {
    var gaugeOptions = {min: 0, max: 2000,  
      width: document.getElementById("notifications-gauge").offsetWidth, 
      height: document.getElementById("notifications-gauge").offsetWidth,
      minorTicks: 5};
    var gaugeData = new google.visualization.DataTable();
    gaugeData.addColumn('number', '');
    gaugeData.addRows(1);
    gaugeData.setCell(0, 0, 0);

    var gauge = new google.visualization.Gauge(document.getElementById('notifications-gauge'));
    gauge.draw(gaugeData, gaugeOptions);
    gaugeData.setValue(0, 0, this.gaugeData);
    gauge.draw(gaugeData, gaugeOptions);
  }

  exportNotifications(date:string){
    console.log(date);
  }

}
