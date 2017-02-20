import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';
import {EditprofileObject} from './editprofile_object';
import {EditprofileObjectError} from './editprofile_error';

@Component({
  selector: 'editprofile',
  templateUrl: './editprofile.html',
  styleUrls: ['./editprofile.css'],
  providers: [],
})

export class EditProfile {


	states = ["CA", "AL", "AK", "AZ", "AR", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"];
	edit_profile: EditprofileObject = new EditprofileObject();
	error: EditprofileObjectError = new EditprofileObjectError();
	constructor( private router: Router, private _languageService: LanguageService) {}
	save(){
		this.error.firstName = this.error.lastName = this.error.email = this.error.phoneNumber = this.error.address1 = this.error.address2 = 
		this.error.city = this.error.zipCode = this.error.newPassword = this.error.oldPassword = this.error.confPassword = "";

		document.getElementById("fname").style["borderColor"] = "black";
		document.getElementById("lname").style["borderColor"] = "black";
		document.getElementById("email").style["borderColor"] = "black";
		document.getElementById("phone").style["borderColor"] = "black";
		document.getElementById("address1").style["borderColor"] = "black";
		//document.getElementById("address2").style["borderColor"] = "black";
		document.getElementById("city").style["borderColor"] = "black";
		document.getElementById("zipcode").style["borderColor"] = "black";
		document.getElementById("newPassword").style["borderColor"] = "black";
		document.getElementById("oldPassword").style["borderColor"] = "black";
		document.getElementById("confPassword").style["borderColor"] = "black";

		var num = /[0-9]/;
		var lower = /[a-z]/;
		var upper = /[A-Z]/;
		var valEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		var strippedZipcode = this.stripNonNumeric(this.edit_profile.zipCode);
		var strippedPhone = this.stripNonNumeric(this.edit_profile.phoneNumber);

		if(this.edit_profile.firstName.length == 0){
			this.error.firstName = this._languageService.getTranslation("first_name_required");
			document.getElementById("fname").style["borderColor"] = "#CD2026";
		}
		if(this.edit_profile.lastName.length == 0){
			this.error.lastName = this._languageService.getTranslation("last_name_required");
			document.getElementById("lname").style["borderColor"] = "#CD2026";
		}

		if(this.edit_profile.email.length > 0){
			if(!valEmail.test(this.edit_profile.email)){
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

		if(this.edit_profile.address1.length == 0){
			this.error.address1 = this._languageService.getTranslation("address1_required");
			document.getElementById("address1").style["borderColor"] = "#CD2026";
		}
		// if(this.edit_profile.address2.length == 0){
		// 	this.error.address2 = this._languageService.getTranslation("address2_required");
		// 	document.getElementById("address2").style["borderColor"] = "#CD2026";
		// }
		if(this.edit_profile.city.length == 0){
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

		if(this.edit_profile.oldPassword.length == 0 ){
			this.error.oldPassword = this._languageService.getTranslation("old_password_required");
			document.getElementById("oldPassword").style["borderColor"] = "#CD2026";
		}

		if(this.edit_profile.newPassword.length > 0){		
			if(this.edit_profile.newPassword.length < 6){
				this.error.newPassword = this._languageService.getTranslation("password_length_low");
				document.getElementById("newPassword").style["borderColor"] = "#CD2026";
			}else if(!num.test(this.edit_profile.newPassword)){
				this.error.newPassword = this._languageService.getTranslation("password_number");
				document.getElementById("newPassword").style["borderColor"] = "#CD2026";
			}else if(!lower.test(this.edit_profile.newPassword)){
				this.error.newPassword = this._languageService.getTranslation("password_lower");
				document.getElementById("newPassword").style["borderColor"] = "#CD2026";
			}else if(!upper.test(this.edit_profile.newPassword)){
				this.error.newPassword = this._languageService.getTranslation("password_upper");
				document.getElementById("newPassword").style["borderColor"] = "#CD2026";
			}	
		}else {
			this.error.newPassword = this._languageService.getTranslation("new_password_required");
			document.getElementById("newPassword").style["borderColor"] = "#CD2026";
		}


		if(this.edit_profile.confPassword.length > 0 ){
			if(this.edit_profile.confPassword != this.edit_profile.newPassword){
				document.getElementById("confPassword").style["borderColor"] = "#CD2026";
				this.error.confPassword = this._languageService.getTranslation("password_dont_match");
			}
		}else{
			this.error.confPassword = this._languageService.getTranslation("confpassword_required");
			document.getElementById("confPassword").style["borderColor"] = "#CD2026";
		}

		
	}

	stripNonNumeric(input: string){
		return input.replace(/\D/g, '');
	}
 
}
