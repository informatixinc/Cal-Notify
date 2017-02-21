import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';

import {NotificationSetting} from '../common/notification_setting';

@Component({
  selector: 'dashboard',
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css'],
  providers: [],
})

export class DashBoard {

	notifications: string[] = [];
	notificationSettings: NotificationSetting[] = [];

	constructor( private router: Router, private _languageService: LanguageService) {}

	ngOnInit(){
		for (var i = 0; i < 10; ++i) {
			this.notifications.push("Alert Notification");
		}

		this.notificationSettings.push(new NotificationSetting("Geo Location", true, true, true));
		this.notificationSettings.push(new NotificationSetting("Primary Address", false, true, false));
		this.notificationSettings.push(new NotificationSetting("Additional Location", false, true, false));
		this.notificationSettings.push(new NotificationSetting("Additional Location", false, false, false));
		this.notificationSettings.push(new NotificationSetting("Additional Location", true, true, false));
		this.notificationSettings.push(new NotificationSetting("Additional Location", false, true, true));
		this.notificationSettings.push(new NotificationSetting("Additional Location", true, false, true));
		this.notificationSettings.push(new NotificationSetting("Additional Location", false, false, true));
		this.notificationSettings.push(new NotificationSetting("Additional Location", true, true, true));
	}

}
