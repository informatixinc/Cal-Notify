import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';
import {ContactObject} from './contact_object';
import {ContactObjectError} from './contact_error';
import {UserState} from '../../services/user_state/user_state';

@Component({
  selector: 'contact',
  templateUrl: './contact.html',
  styleUrls: ['./contact.css'],
  providers: [LanguageService],
})

export class Contact {

	contact: ContactObject = new ContactObject();
	error: ContactObjectError = new ContactObjectError();
	constructor(private router: Router, private _languageService: LanguageService, private _userState: UserState) {}

	submit(){
		this.error.name = this.error.email = this.error.message = "";

		document.getElementById("name").style["borderColor"] = "black";
		document.getElementById("email").style["borderColor"] = "black";
		document.getElementById("message").style["borderColor"] = "black";

		var valEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		var hasError = false;
		if(this.contact.name.length == 0){
			this.error.name = this._languageService.getTranslation("name_required");
			document.getElementById("name").style["borderColor"] = "#CD2026";
			hasError = true;
		}
		if(this.contact.email.length > 0){
			if(!valEmail.test(this.contact.email)){
				this.error.email = this._languageService.getTranslation("email_validation");
				document.getElementById("email").style["borderColor"] = "#CD2026";
				hasError = true;
			}
		}else{
			this.error.email = this._languageService.getTranslation("email_required");
			document.getElementById("email").style["borderColor"] = "#CD2026";
			hasError = true;
		}
		if(this.contact.message.length == 0){
			this.error.message = this._languageService.getTranslation("message_required");
			document.getElementById("message").style["borderColor"] = "#CD2026";
			hasError = true;
		}
		if(hasError){
			return;
		}else{
			if(this._userState.getLoggedIn()==true){
				this.router.navigate(['dashboard']);
			}else{
				this.router.navigate(['home']);
			}
			
		}
	}
	cancel(){
		if(this._userState.getLoggedIn()==true){
			this.router.navigate(['dashboard']);
		}else{
			this.router.navigate(['home']);
		}
	}
}
