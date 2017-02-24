import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';
import {UserState} from '../../services/user_state/user_state';
import {NotificationSetting} from '../common/notification_setting';
import {ApiRequest} from '../../services/http/api_request';
import {Session} from '../common/session';
import {User} from '../common/user';

@Component({
  selector: 'dashboard',
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css'],
  providers: [],
})

export class DashBoard {

	notifications: string[] = [];
	notificationSettings: NotificationSetting[] = [];
	user = new User();

	constructor( private router: Router, private _languageService: LanguageService, private _userState: UserState, private _apiRequest: ApiRequest) {}

	ngOnInit(){
		for (var i = 0; i < 10; ++i) {
			this.notifications.push("Alert Notification");
		}

		var session = new Session();
		session.session = this._userState.getSession();
		this._apiRequest.doRequest('accountinformation',session).subscribe(res => this.loadUser(res));
		this._apiRequest.doRequest('getnotificationsettings',session).subscribe(res => this.loadSettings(res));
	}

	loadUser(user: any){
		this.user = user;
	}

	loadSettings(settings: any){
		this.notificationSettings = settings;
	}

}
