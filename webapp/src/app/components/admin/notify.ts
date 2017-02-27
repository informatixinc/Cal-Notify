import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';
import {NotifyObject} from './notify_object';
import {NotifyObjectError} from './notify_error';
import {ApiRequest} from '../../services/http/api_request';
import {AdminMessage} from '../common/admin_message';
import {Session} from '../common/session';
import {UserState} from '../../services/user_state/user_state';

@Component({
  selector: 'notify',
  templateUrl: './notify.html',
  styleUrls: ['./notify.css'],
  providers: [LanguageService],
})

export class Notify {

	notify: NotifyObject = new NotifyObject();
	error: NotifyObjectError = new NotifyObjectError();
	adminMessages: AdminMessage[] = [];
	successMessage = "";

	constructor(private router: Router, private _languageService: LanguageService, private _apiRequest: ApiRequest, private _userState: UserState) {}

	ngOnInit(){
		var session = new Session();
		session.session = this._userState.getSession();
		this._apiRequest.doRequest('adminmessagehistory',session).subscribe(res => this.processHistory(res));
	}

	editAndResend(adminMessage: AdminMessage){
		this.notify.notifyTitle = adminMessage.title;
		this.notify.notifyDetails = adminMessage.message;
	}

	processHistory(res: any){
		this.adminMessages = res;
	}

	cancel(){
		this.router.navigate(['reports']);
	}

	send(){

		this.error.notifyTitle = this.error.expDate = this.error.notifyDetails = "";

		document.getElementById("title").style["borderColor"] = "black";
		document.getElementById("expdate").style["borderColor"] = "black";
		document.getElementById("message").style["borderColor"] = "black";

		var hasError = false;
		
		if(this.notify.notifyTitle.length == 0){
			this.error.notifyTitle = this._languageService.getTranslation("title_required");
			document.getElementById("title").style["borderColor"] = "#CD2026";
			hasError = true;
		}
		if(this.notify.expDate.length == 0){
			this.error.expDate = this._languageService.getTranslation("date_required");
			document.getElementById("expdate").style["borderColor"] = "#CD2026";
			hasError = true;
		}
		if(this.notify.notifyDetails.length == 0){
			this.error.notifyDetails = this._languageService.getTranslation("message_required");
			document.getElementById("message").style["borderColor"] = "#CD2026";
			hasError = true;
		}

		if(hasError){
			return;
		}

		var adminMessage = new AdminMessage();
		adminMessage.title = this.notify.notifyTitle;
		adminMessage.message = this.notify.notifyDetails;
		adminMessage.expirationDate = new Date(this.notify.expDate).getTime();


		this._apiRequest.doRequest('adminmessage',adminMessage).subscribe(res => this.updateView());
	}

	updateView(){
		var session = new Session();
		session.session = this._userState.getSession();
		this.notify.notifyTitle = "";
		this.notify.expDate = "";
		this.notify.notifyDetails = "";
		this._apiRequest.doRequest('adminmessagehistory',session).subscribe(res => this.processHistory(res));
		this.successMessage = "Message sent successfully";
	}

	prevent(event: any){
		if(this.notify.notifyTitle.length > 79){
			this.notify.notifyTitle = this.notify.notifyTitle.substring(0,80);
		}
	}

	
 
}
