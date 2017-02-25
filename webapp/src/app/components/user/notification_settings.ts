import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';
import {NotificationObject} from './notification_settings_object';
import {NotificationObjectError} from './notification_settings_error';

@Component({
  selector: 'notification',
  templateUrl: './notification_settings.html',
  styleUrls: ['./notification_settings.css'],
  providers: [],
})

export class NotificationSettings {

	states = ["CA", "AL", "AK", "AZ", "AR", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"];
	notification: NotificationObject = new NotificationObject();
	error: NotificationObjectError = new NotificationObjectError();

	constructor( private router: Router, private _languageService: LanguageService) {}
	addLoc(){
		this.error.address = this.error.zipCode = "";

		document.getElementById("address").style["borderColor"] = "black";
		document.getElementById("zip").style["borderColor"] = "black";

		var strippedZipcode = this.stripNonNumeric(this.notification.zipCode);

		if(this.notification.address.length == 0){
			this.error.address = this._languageService.getTranslation("address_required");
			document.getElementById("address").style["borderColor"] = "#CD2026";
		}
		if(strippedZipcode.length > 0){
			if(strippedZipcode.length != 5){
				this.error.zipCode = this._languageService.getTranslation("zipcode_validation");
				document.getElementById("zip").style["borderColor"] = "#CD2026";
			}
		}else{
			this.error.zipCode = this._languageService.getTranslation("zipcode_required");
			document.getElementById("zip").style["borderColor"] = "#CD2026";
		}
	}

	stripNonNumeric(input: string){
		return input.replace(/\D/g, '');
	}

	

}
