import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';
import {UserState} from '../../services/user_state/user_state';
import {ApiRequest} from '../../services/http/api_request';
import {Notification} from '../common/notification';

@Component({
  selector: 'notificationview',
  templateUrl: './notification_view.html',
  styleUrls: ['./notification_view.css'],
  providers: [],
})

export class NotificationView {

	notifications: Notification[] = [];

	constructor( private router: Router, private _languageService: LanguageService, private _apiRequest: ApiRequest, private _userState: UserState) {}

	ngOnInit(){
		this._apiRequest.doRequest('getactivenotifications',this._userState.getGeoLocation()).subscribe(res => this.loadNotifications(res));
	}

 	goToNotification(notificationId: string){
		this.router.navigate(["notification",notificationId]);
	}

 	loadNotifications(input: any){
		this.notifications = input;
	}
	
	clearLocation(){
		this._userState.resetLocation();
		this._apiRequest.doRequest('getactivenotifications',this._userState.getGeoLocation()).subscribe(res => this.loadNotifications(res));
	}
}