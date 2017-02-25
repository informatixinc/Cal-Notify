import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';
import {ApiRequest} from '../../services/http/api_request';
import {UserState} from '../../services/user_state/user_state';
import {LoginService} from './login_service';
import {LoginObject} from './login_object';
import {LoginObjectError} from './login_error';
import {Notification} from '../common/notification';

@Component({
  selector: 'home',
  templateUrl: './home.html',
  styleUrls: ['./home.css'],
  providers: [LanguageService, LoginService, ApiRequest],
})

export class Home {

	email = "";
	password = "";
	errorMessage = "";
	notifications: Notification[] = [];
	
	error: LoginObjectError = new LoginObjectError();
	constructor( private router: Router, private _apiRequest: ApiRequest, private _userState: UserState, private _languageService: LanguageService, private _loginService: LoginService) {}

	ngOnInit(){
		setTimeout(() => {
			document.getElementsByClassName("notification-body")[0]["style"].height = document.getElementsByClassName("main-body")[0].clientHeight + "px";
		},1);
		this.error.showPassword = this._languageService.getTranslation("show_password");
		this._apiRequest.doRequest('getactivenotifications',this._userState.getGeoLocation()).subscribe(res => this.loadNotifications(res));
	}

	loadNotifications(input: any){
		this.notifications = input;
	}

	signup(){
		this.router.navigate(['signup']);
	}

	login(){

		var hasError = false;
		this.error.email = this.error.password = "";
		document.getElementById("email").style["borderColor"] = "black";
		document.getElementById("password").style["borderColor"] = "black";

		if(this.email.length == 0 && this.password.length == 0){
			this.error.email = this._languageService.getTranslation("email_required");
			document.getElementById("email").style["borderColor"] = "#CD2026";
			this.error.password = this._languageService.getTranslation("password_required");
			document.getElementById("password").style["borderColor"] = "#CD2026";
			hasError = true;
		}else if(this.email.length == 0){
			this.error.email = this._languageService.getTranslation("email_required");
			document.getElementById("email").style["borderColor"] = "#CD2026";
			hasError = true;
		}else if(this.password.length == 0){
			this.error.password = this._languageService.getTranslation("password_required");
			document.getElementById("password").style["borderColor"] = "#CD2026";
			hasError = true;
		}
		
		if(hasError){
			return;
		}

		var login = this._loginService.prepareLogin(this.email, this.password);
		this._apiRequest.doRequest('login', login).subscribe(res => this.processResponse(res));
	}

	processResponse(response: any){
    	if(!response.errorResponse.error){
	      	this._userState.setSession(response.session);
			// if(this._userState.isAdmin()){
			// 	this.router.navigate(['notify']);	
			// }else{
				this.router.navigate(['dashboard']);
			// }
	    }else{
	      	// this.loginError = response.errorMessage;
	      	// this.showLoginPopup = true;
	    }
  	}

	goToNotification(notificationId: string){
		this.router.navigate(["notification",notificationId]);
	}
 
	
 	showPassword(input: any): any {
   		if(input.type === 'password'){
   			input.type = 'text';
   			this.error.showPassword = this._languageService.getTranslation("hide_password");

   		}else{
   			input.type = 'password';
   			this.error.showPassword = this._languageService.getTranslation("show_password");
   		}
  	}
}
