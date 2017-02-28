import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';
import {NotificationObject} from './notification_settings_object';
import {NotificationObjectError} from './notification_settings_error';
import {ApiRequest} from '../../services/http/api_request';
import {Address} from '../common/address';
import {UserState} from '../../services/user_state/user_state';
import {NotificationSetting} from '../common/notification_setting';
import {Session} from '../common/session';

@Component({
  selector: 'notification',
  templateUrl: './notification_settings.html',
  styleUrls: ['./notification_settings.css'],
  providers: [LanguageService, ApiRequest],
})

export class NotificationSettings {

	states = ["AL", "AK", "AZ", "AR","CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"];
	additional_loc: NotificationObject = new NotificationObject();
	error: NotificationObjectError = new NotificationObjectError();
	notifications: NotificationSetting[] = [];
	errorMessage = "";
	ngOnInit(){
		var session = new Session();
		session.session = this._userState.getSession();
		this.additional_loc.addresses[0].state = "CA";
		this._apiRequest.doRequest('getnotificationsettings',session).subscribe(res => this.addNotifications(res));
	}
	constructor( private router: Router, private _languageService: LanguageService, private _apiRequest: ApiRequest, private _userState: UserState) {}
	addNotifications(notifications: any){
		this.notifications = notifications;
	}

	addLoc(){
		this.error.addresses[0].addressOne = this.error.addresses[0].nickName = this.error.addresses[0].zipCode = "";

		document.getElementById("address").style["borderColor"] = "black";
		document.getElementById("zip").style["borderColor"] = "black";
		document.getElementById("nickname").style["borderColor"] = "black";
		var hasError = false;
		var strippedZipcode = this.stripNonNumeric(this.additional_loc.addresses[0].zipCode);

		if(this.additional_loc.addresses[0].nickName.length == 0){
			this.error.addresses[0].nickName = this._languageService.getTranslation("nickname_required");
			document.getElementById("nickname").style["borderColor"] = "#CD2026";
			hasError = true;
		}
		if(this.additional_loc.addresses[0].addressOne.length == 0){
			this.error.addresses[0].addressOne = this._languageService.getTranslation("address_required");
			document.getElementById("address").style["borderColor"] = "#CD2026";
			hasError = true;
		}
		if(strippedZipcode.length > 0){
			if(strippedZipcode.length != 5){
				this.error.addresses[0].zipCode = this._languageService.getTranslation("zipcode_validation");
				document.getElementById("zip").style["borderColor"] = "#CD2026";
				hasError = true;
			}
		}else{
			this.error.addresses[0].zipCode = this._languageService.getTranslation("zipcode_required");
			document.getElementById("zip").style["borderColor"] = "#CD2026";
			hasError = true;
		}
		if(hasError){
			return;
		}

		var addLocObj = JSON.parse(JSON.stringify(this.additional_loc));
		console.log(JSON.stringify(addLocObj));
		this._apiRequest.doRequest('addlocations',addLocObj).subscribe(res => this.updateTable());

	}

	updateTable(){
		var session = new Session();
		session.session = this._userState.getSession();
		this.additional_loc.addresses[0].nickName = "";
		this.additional_loc.addresses[0].addressOne = "";
		this.additional_loc.addresses[0].city = "";
		this.additional_loc.addresses[0].state = "";
		this.additional_loc.addresses[0].zipCode = "";
		this._apiRequest.doRequest('getnotificationsettings',session).subscribe(res => this.addNotifications(res));
	}

	saveNotifications(){
		var wrapper = {};
		wrapper["notificationSettings"] = this.notifications;
		this._apiRequest.doRequest('managenotifications', wrapper).subscribe(res => this.processResponse(res));
	}
	processResponse(response: any){
		if(response.errorResponse.error == true){
			this.error.notifications = response.errorResponse.errorMessage;
		}else{
			this.router.navigate(['dashboard']);
		}
	}

	cancel(){
		this.router.navigate(['dashboard']);
	}

	stripNonNumeric(input: string){
		return input.replace(/\D/g, '');
	}
}
