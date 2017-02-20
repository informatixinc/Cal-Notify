import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';
import {SignupObject} from './signup_object';
import {SignupObjectError} from './signup_error';


@Component({
  selector: 'signup',
  templateUrl: './signup.html',
  styleUrls: ['./signup.css'],
  providers: [],
})

export class Signup {

	states = ["CA", "AL", "AK", "AZ", "AR", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"];
	sign_up: SignupObject = new SignupObject();
	error: SignupObjectError = new SignupObjectError();
	constructor(private router: Router, private _languageService: LanguageService) {}
	// phoneFilter(event: any){
	// 	console.log(event);
	// }

	signup(){

		this.error.firstName = this.error.lastName = this.error.email = this.error.phoneNumber = this.error.address1 = this.error.address2 = 
		this.error.city = this.error.zipCode = this.error.password = this.error.confPassword = "";

		document.getElementById("fname").style["borderColor"] = "black";
		document.getElementById("lname").style["borderColor"] = "black";
		document.getElementById("email").style["borderColor"] = "black";
		document.getElementById("phone").style["borderColor"] = "black";
		document.getElementById("address1").style["borderColor"] = "black";
		//document.getElementById("address2").style["borderColor"] = "black";
		document.getElementById("city").style["borderColor"] = "black";
		document.getElementById("zipcode").style["borderColor"] = "black";
		document.getElementById("password").style["borderColor"] = "black";
		document.getElementById("confPassword").style["borderColor"] = "black";
		

		var num = /[0-9]/;
		var lower = /[a-z]/;
		var upper = /[A-Z]/;
		var valEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		var strippedZipcode = this.stripNonNumeric(this.sign_up.zipCode);
		var strippedPhone = this.stripNonNumeric(this.sign_up.phoneNumber);

		if(this.sign_up.firstName.length == 0){
			this.error.firstName = this._languageService.getTranslation("first_name_required");
			document.getElementById("fname").style["borderColor"] = "#CD2026";
		}
		if(this.sign_up.lastName.length == 0){
			this.error.lastName = this._languageService.getTranslation("last_name_required");
			document.getElementById("lname").style["borderColor"] = "#CD2026";
		}

		if(this.sign_up.email.length > 0){
			if(!valEmail.test(this.sign_up.email)){
				this.error.email = this._languageService.getTranslation("email_validation");
				document.getElementById("email").style["borderColor"] = "#CD2026";
			}
		}else{
			this.error.email = this._languageService.getTranslation("email_required");
			document.getElementById("email").style["borderColor"] = "#CD2026";
		}

		if(strippedPhone.length > 0){
			if(strippedPhone.length != 10){
				this.error.phoneNumber = this._languageService.getTranslation("phone_validation");
				document.getElementById("phone").style["borderColor"] = "#CD2026";
			}
		}else{
			this.error.phoneNumber = this._languageService.getTranslation("phone_required");
			document.getElementById("phone").style["borderColor"] = "#CD2026";
		}

		if(this.sign_up.address1.length == 0){
			this.error.address1 = this._languageService.getTranslation("address1_required");
			document.getElementById("address1").style["borderColor"] = "#CD2026";
		}
		// if(this.sign_up.address2.length == 0){
		// 	this.error.address2 = this._languageService.getTranslation("address2_required");
		// 	document.getElementById("address2").style["borderColor"] = "#CD2026";
		// }
		if(this.sign_up.city.length == 0){
			this.error.city = this._languageService.getTranslation("city_required");
			document.getElementById("city").style["borderColor"] = "#CD2026";
		}

		if(strippedZipcode.length > 0){
			if(strippedZipcode.length != 5){
				this.error.zipCode = this._languageService.getTranslation("zipcode_validation");
				document.getElementById("zipcode").style["borderColor"] = "#CD2026";
			}
		}else{
			this.error.zipCode = this._languageService.getTranslation("zipcode_required");
			document.getElementById("zipcode").style["borderColor"] = "#CD2026";
		}

		if(this.sign_up.password.length > 0){		
			if(this.sign_up.password.length < 6){
				this.error.password = this._languageService.getTranslation("password_length_low");
				document.getElementById("password").style["borderColor"] = "#CD2026";
			}else if(!num.test(this.sign_up.password)){
				this.error.password = this._languageService.getTranslation("password_number");
				document.getElementById("password").style["borderColor"] = "#CD2026";
			}else if(!lower.test(this.sign_up.password)){
				this.error.password = this._languageService.getTranslation("password_lower");
				document.getElementById("password").style["borderColor"] = "#CD2026";
			}else if(!upper.test(this.sign_up.password)){
				this.error.password = this._languageService.getTranslation("password_upper");
				document.getElementById("password").style["borderColor"] = "#CD2026";
			}	
		}else {
			this.error.password = this._languageService.getTranslation("password_required");
			document.getElementById("password").style["borderColor"] = "#CD2026";
		}


		if(this.sign_up.confPassword.length > 0 ){
			if(this.sign_up.confPassword != this.sign_up.password){
				document.getElementById("confPassword").style["borderColor"] = "#CD2026";
				this.error.confPassword = this._languageService.getTranslation("password_dont_match");
			}
		}else{
			this.error.confPassword = this._languageService.getTranslation("confpassword_required");
			document.getElementById("confPassword").style["borderColor"] = "#CD2026";
		}

		//this.router.navigate(['home']);
	}

	stripNonNumeric(input: string){
		return input.replace(/\D/g, '');
	}
 	
}
