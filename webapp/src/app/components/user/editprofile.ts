import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';
import {EditprofileObject} from './editprofile_object';
import {EditprofileObjectError} from './editprofile_error';
import {ApiRequest} from '../../services/http/api_request';
import {Address} from '../common/address';
import {UserState} from '../../services/user_state/user_state';
import {LoginService} from '../home/login_service';
import {Session} from '../common/session';

@Component({
  selector: 'editprofile',
  templateUrl: './editprofile.html',
  styleUrls: ['./editprofile.css'],
  providers: [LoginService, ApiRequest],
})

export class EditProfile {


	states = ["AL", "AK", "AZ", "AR","CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"];
	edit_profile: EditprofileObject = new EditprofileObject();
	error: EditprofileObjectError = new EditprofileObjectError();
	errorMessage = "";
	constructor(private router: Router, private _userState: UserState, private _languageService: LanguageService, private _loginService: LoginService, private _apiRequest: ApiRequest) {

	}
	ngOnInit(){
		this.edit_profile.addresses[0].state = "CA";
		var session = new Session();
		session.session = this._userState.getSession();
		this._apiRequest.doRequest('accountinformation',session).subscribe(res => this.loadUser(res));
	}

	loadUser(result: any){
		this.edit_profile = result;
	}

	save(){
		this.error.firstName = this.error.lastName = this.error.email = this.error.phoneNumber = this.error.addresses[0].addressOne = this.error.addresses[0].addressTwo = 
		this.error.addresses[0].city = this.error.addresses[0].zipCode = this.error.password = this.error.confPassword = "";

		document.getElementById("fname").style["borderColor"] = "black";
		document.getElementById("lname").style["borderColor"] = "black";
		document.getElementById("email").style["borderColor"] = "black";
		document.getElementById("phone").style["borderColor"] = "black";
		document.getElementById("address1").style["borderColor"] = "black";
		document.getElementById("city").style["borderColor"] = "black";
		document.getElementById("zipcode").style["borderColor"] = "black";
		document.getElementById("password").style["borderColor"] = "black";
		document.getElementById("confPassword").style["borderColor"] = "black";

		var num = /[0-9]/;
		var lower = /[a-z]/;
		var upper = /[A-Z]/;
		var valEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		var strippedZipcode = this.stripNonNumeric(this.edit_profile.addresses[0].zipCode);
		var strippedPhone = this.stripNonNumeric(this.edit_profile.phoneNumber);
		var hasError = false;

		if(this.edit_profile.firstName.length == 0){
			this.error.firstName = this._languageService.getTranslation("first_name_required");
			document.getElementById("fname").style["borderColor"] = "#CD2026";
			hasError = true;
		}
		if(this.edit_profile.lastName.length == 0){
			this.error.lastName = this._languageService.getTranslation("last_name_required");
			document.getElementById("lname").style["borderColor"] = "#CD2026";
			hasError = true;
		}

		if(this.edit_profile.email.length > 0){
			if(!valEmail.test(this.edit_profile.email)){
				this.error.email = this._languageService.getTranslation("email_validation");
				document.getElementById("email").style["borderColor"] = "#CD2026";
				hasError = true;
			}
		}else{
			this.error.email = this._languageService.getTranslation("email_required");
			document.getElementById("email").style["borderColor"] = "#CD2026";
			hasError = true;
		}

		if(strippedPhone.length > 0){
			if(strippedPhone.length != 10){
				this.error.phoneNumber = this._languageService.getTranslation("phone_validation");
				document.getElementById("phone").style["borderColor"] = "#CD2026";
				hasError = true;
			}
		}else{
			this.error.phoneNumber = this._languageService.getTranslation("phone_required");
			document.getElementById("phone").style["borderColor"] = "#CD2026";
			hasError = true;
		}

		if(this.edit_profile.addresses[0].addressOne.length == 0){
			this.error.addresses[0].addressOne = this._languageService.getTranslation("address1_required");
			document.getElementById("address1").style["borderColor"] = "#CD2026";
			hasError = true;
		}

		if(this.edit_profile.addresses[0].city.length == 0){
			this.error.addresses[0].city = this._languageService.getTranslation("city_required");
			document.getElementById("city").style["borderColor"] = "#CD2026";
			hasError = true;
		}

		if(strippedZipcode.length > 0){
			if(strippedZipcode.length != 5){
				this.error.addresses[0].zipCode = this._languageService.getTranslation("zipcode_validation");
				document.getElementById("zipcode").style["borderColor"] = "#CD2026";
				hasError = true;
			}
		}else{
			this.error.addresses[0].zipCode = this._languageService.getTranslation("zipcode_required");
			document.getElementById("zipcode").style["borderColor"] = "#CD2026";
			hasError = true;
		}


		if(this.edit_profile.password.length != 0){		
			if(this.edit_profile.password.length < 6){
				this.error.password = this._languageService.getTranslation("password_length_low");
				document.getElementById("password").style["borderColor"] = "#CD2026";
				hasError = true;
			}else if(!num.test(this.edit_profile.password)){
				this.error.password = this._languageService.getTranslation("password_number");
				document.getElementById("password").style["borderColor"] = "#CD2026";
				hasError = true;
			}else if(!lower.test(this.edit_profile.password)){
				this.error.password = this._languageService.getTranslation("password_lower");
				document.getElementById("password").style["borderColor"] = "#CD2026";
				hasError = true;
			}else if(!upper.test(this.edit_profile.password)){
				this.error.password = this._languageService.getTranslation("password_upper");
				document.getElementById("password").style["borderColor"] = "#CD2026";
				hasError = true;
			}else if(this.edit_profile.confPassword != this.edit_profile.password){
				document.getElementById("confPassword").style["borderColor"] = "#CD2026";
				this.error.confPassword = this._languageService.getTranslation("password_dont_match");
				hasError = true;
			}
		}

 		


		if(hasError){
			return;
		}

		var editprofileObj = JSON.parse(JSON.stringify(this.edit_profile));
		editprofileObj.password = this._loginService.prepareLogin("", this.edit_profile.password).password;
		if(this.edit_profile.password.length == 0){
			editprofileObj.password = "";
		}
		delete editprofileObj.confPassword;
		delete editprofileObj.oldPassword;
		console.log(JSON.stringify(editprofileObj));
		this._apiRequest.doRequest('updateaccount',editprofileObj).subscribe(res => this.processResponse(res));
	}

	processResponse(response: any){
		if(response.errorResponse.error == true){
			this.error.password = response.errorResponse.errorMessage;
			return;
			
		}else{
			this.router.navigate(['dashboard']);
		}
	}

	stripNonNumeric(input: string){
		return input.replace(/\D/g, '');
	}
	
	cancel(){
		this.router.navigate(['dashboard']);
	}
 
}
