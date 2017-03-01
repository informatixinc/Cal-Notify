import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';
import {PasswordResetObject} from './password_reset_object';
import {PasswordResetObjectError} from './password_reset_error';

@Component({
  selector: 'password-reset',
  templateUrl: './password_reset.html',
  styleUrls: ['./password_reset.css'],
  providers: [LanguageService],
})

export class PasswordReset {

	password_reset: PasswordResetObject = new PasswordResetObject();
	error: PasswordResetObjectError = new PasswordResetObjectError();
	constructor(private router: Router, private _languageService: LanguageService) {}

	submit(){

		this.error.email =  "";

		document.getElementById("email").style["borderColor"] = "black";

		var valEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		var hasError = false;
		if(this.password_reset.email.length > 0){
			if(!valEmail.test(this.password_reset.email)){
				this.error.email = this._languageService.getTranslation("email_validation");
				document.getElementById("email").style["borderColor"] = "#CD2026";
				hasError = true;
			}
		}else{
			this.error.email = this._languageService.getTranslation("email_required");
			document.getElementById("email").style["borderColor"] = "#CD2026";
			hasError = true;
		}
		if(hasError){
			return;
		}else{
			this.router.navigate(['home']);
		}

	}
	cancel(){
		this.router.navigate(['home']);
	}
}
