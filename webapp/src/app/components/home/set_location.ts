import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';
import {UserState} from '../../services/user_state/user_state';
import {Address} from '../common/address';
import {ApiRequest} from '../../services/http/api_request';
import {SetLocationError} from './set_location_error';


@Component({
  selector: 'set_location',
  templateUrl: './set_location.html',
  styleUrls: ['./set_location.css'],
  providers: [LanguageService],
})

export class SetLocation {

	constructor( private router: Router,private _userState: UserState, private _languageService: LanguageService, private _apiRequest: ApiRequest) {}

	states = ["AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"];

	address = new Address();
	error: SetLocationError = new SetLocationError();
	errorMessage = "";

	ngOnInit(){
		this.address.state = "CA";
	}

	setLocation(){
		this.error.addressOne = this.error.zipCode = "";
		document.getElementById("address1").style["borderColor"] = "black";
		document.getElementById("zipcode").style["borderColor"] = "black";

		var strippedZipcode = this.stripNonNumeric(this.address.zipCode);
		var hasError = false;
		if(this.address.addressOne.length == 0){
			this.error.addressOne = this._languageService.getTranslation("address1_required");
			document.getElementById("address1").style["borderColor"] = "#CD2026";
			hasError = true;
		}
		if(strippedZipcode.length > 0){
			if(strippedZipcode.length != 5){
				this.error.zipCode = this._languageService.getTranslation("zipcode_validation");
				document.getElementById("zipcode").style["borderColor"] = "#CD2026";
				hasError = true;
			}
		}else{
			this.error.zipCode = this._languageService.getTranslation("zipcode_required");
			document.getElementById("zipcode").style["borderColor"] = "#CD2026";
			hasError = true;
		}
		
		if(hasError){
			return;
		}

		this._apiRequest.doRequest('geofromaddress', this.address).subscribe(res => this.setCoordinates(res));
	}

	stripNonNumeric(input: string){
		return input.replace(/\D/g, '');
	}
	setCoordinates(input: any){
		if(input.errorResponse.error){
			this.errorMessage = input.errorResponse.errorMessage;
		}else{
			this._userState.setAddress(this.address);
			delete input.errorResponse;
			this._userState.setGeoLocation(input);
			this.router.navigate(['home']);

		}
	}
	cancel(){
		this.router.navigate(['home']);
	}
 
}
