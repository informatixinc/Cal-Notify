import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';
import {UserState} from '../../services/user_state/user_state';
import {NotificationSetting} from '../common/notification_setting';
import {Notification} from '../common/notification';
import {ApiRequest} from '../../services/http/api_request';
import {Session} from '../common/session';
import {User} from '../common/user';
import {Point} from '../common/point';

@Component({
  selector: 'dashboard',
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css'],
  providers: [],
})

export class DashBoard {

	emergencyNotifications: Notification[] = [];
	nonEmergencyNotifications: Notification[] = [];
	notificationSettings: NotificationSetting[] = [];
	user = new User();

	constructor( private router: Router, private _languageService: LanguageService, private _userState: UserState, private _apiRequest: ApiRequest) {}

	ngOnInit(){
		var session = new Session();
		session.session = this._userState.getSession();
		this._apiRequest.doRequest('accountinformation',session).subscribe(res => this.loadUser(res));
		this._apiRequest.doRequest('getnotificationsettings',session).subscribe(res => this.loadSettings(res));
		this._apiRequest.doRequest('getusernotifications',session).subscribe(res => this.loadNotifications(res));
		navigator.geolocation.getCurrentPosition(this.geoSuccess.bind(this), this.geoFail);

		
	}

	geoSuccess(position: any){
		var geoLocation = new Point();
		geoLocation.longitude = position.coords.longitude;
		geoLocation.latitude =  position.coords.latitude;
		this._apiRequest.doRequest('updateuserposition',geoLocation).subscribe(res => this.ignore(res));
	}

	ignore(res:any){
	}

	geoFail(){
	}

	goToNotification(notificationId: string){
		this.router.navigate(["notification",notificationId]);
	}

	loadNotifications(notificaitons: any){
		let notifications: Notification[] = notificaitons;
		for (var i = 0; i < notifications.length; ++i) {
			if(notificaitons[i].title.toLowerCase().startsWith("admin")){
				this.nonEmergencyNotifications.push(notificaitons[i]);
			}else{
				this.emergencyNotifications.push(notificaitons[i]);
			}
		}
		this.emergencyNotifications = notificaitons;
	}

	loadUser(user: any){
		this.user = user;
	}

	loadSettings(settings: any){
		this.notificationSettings = settings;
	}

}
