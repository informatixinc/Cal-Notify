import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';
import {NotifyObject} from './notify_object';
import {NotifyObjectError} from './notify_error';

@Component({
  selector: 'notify',
  templateUrl: './notify.html',
  styleUrls: ['./notify.css'],
  providers: [],
})

export class Notify {

	notify: NotifyObject = new NotifyObject();
	error: NotifyObjectError = new NotifyObjectError();
	constructor(private router: Router, private _languageService: LanguageService ) {}

	send(){

		this.error.notifyTitle = this.error.expDate = this.error.notifyDetails = "";

		document.getElementById("title").style["borderColor"] = "black";
		document.getElementById("expdate").style["borderColor"] = "black";
		document.getElementById("message").style["borderColor"] = "black";
		
		if(this.notify.notifyTitle.length == 0){
			this.error.notifyTitle = this._languageService.getTranslation("title_required");
			document.getElementById("title").style["borderColor"] = "#CD2026";
		}
		if(this.notify.expDate.length == 0){
			this.error.expDate = this._languageService.getTranslation("date_required");
			document.getElementById("expdate").style["borderColor"] = "#CD2026";
		}
		if(this.notify.notifyDetails.length == 0){
			this.error.notifyDetails = this._languageService.getTranslation("message_required");
			document.getElementById("message").style["borderColor"] = "#CD2026";
		}
	}
 
}
