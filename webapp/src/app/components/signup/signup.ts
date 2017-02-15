import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';
import {SignupObject} from './signup_object';

@Component({
  selector: 'signup',
  templateUrl: './signup.html',
  styleUrls: ['./signup.css'],
  providers: [],
})

export class Signup {

	states = ["CA", "AL", "AK", "AZ", "AR", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"];
	sign_up: SignupObject = new SignupObject();
	constructor(private router: Router, private _languageService: LanguageService) {}
	signup(){

		if(this.sign_up.firstName.length == 0){
			console.log("first name is required");
		}
		if(this.sign_up.lastName.length == 0){
			console.log("first name is required");
		}
		if(this.sign_up.email.length == 0){
			console.log("first name is required");
		}
		if(this.sign_up.phoneNumber.length == 0){
			console.log("first name is required");
		}
		if(this.sign_up.address1.length == 0){
			console.log("first name is required");
		}
		if(this.sign_up.address2.length == 0){
			console.log("first name is required");
		}
		this.router.navigate(['home']);
	}
 	
}
