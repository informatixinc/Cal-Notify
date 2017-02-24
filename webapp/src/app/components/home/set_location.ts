import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';
import {UserState} from '../../services/user_state/user_state';
import {Address} from '../common/address';
import {ApiRequest} from '../../services/http/api_request';


@Component({
  selector: 'set_location',
  templateUrl: './set_location.html',
  styleUrls: ['./set_location.css'],
  providers: [],
})

export class SetLocation {

	constructor( private router: Router,private _userState: UserState, private _languageService: LanguageService, private _apiRequest: ApiRequest,) {}

	states = ["CA", "AL", "AK", "AZ", "AR", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"];

	address = new Address();
	errorMessage = "";

	ngOnInit(){
		this.address.state = "CA";
	}

	setLocation(){
		this._apiRequest.doRequest('geofromaddress', this.address).subscribe(res => this.setCoordinates(res));
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
	
 
}
