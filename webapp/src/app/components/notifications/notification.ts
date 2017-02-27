import {Component} from '@angular/core';
import {Router, ActivatedRoute, Params} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';
import {ApiRequest} from '../../services/http/api_request';
import {Notification} from '../common/notification';
import { Http, Response } from '@angular/http';

@Component({
  selector: 'notification',
  templateUrl: './notification.html',
  styleUrls: ['./notification.css'],
  providers: [],
})

export class ViewNotification {

	constructor(private router: Router, private activatedRoute: ActivatedRoute, 
		private _languageService: LanguageService, private _apiRequest: ApiRequest, private http: Http ) {}

	notification = new Notification();
	title = "";
	body = "";
	instructions = "";

	ngOnInit(){
		this.activatedRoute.params.subscribe((params: Params) => {
	        this.notification.id = params['notification_id'];
	        this._apiRequest.doRequest('getnotification',this.notification).subscribe(res => this.lodNotification(res));
      	});
	}


	 lodNotification(input: any){
	 	if(input.classificationId == 42){
	 		this.title = input.title;
	 		this.body = input.adminMessageBody;
	 	}else{
	 		this.http.get(input.infoUrl)
				.map((res:Response) => res.text())
				.subscribe(
				    data => {
		    		data = data.replace(/&amp;/g, '');
		        	var xmlString = data;
					var parser = new DOMParser();
					var doc: any = parser.parseFromString(xmlString, "text/html");
					this.title = doc.getElementsByTagName("headline")[0].innerHTML;
					this.body = doc.getElementsByTagName("description")[0].innerHTML;
					this.instructions = doc.getElementsByTagName("instruction")[0].innerHTML;
		     });
	 	}
	 }

	
}