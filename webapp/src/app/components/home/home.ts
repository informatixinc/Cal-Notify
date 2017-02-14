import {Component} from '@angular/core';
import {Router} from '@angular/router';

import {ApiRequest} from '../../services/http/api_request';
import {UserState} from '../../services/user_state/user_state';


@Component({
  selector: 'home',
  templateUrl: './home.html',
  styleUrls: ['./home.css'],
  providers: [],
})

export class Home {

	constructor( private router: Router, private _apiRequest: ApiRequest, private _userState: UserState) {}

	signup(){
		this.router.navigate(['signup']);
	}

	login(){
		this._userState.setSesstion("12345");
		if(this._userState.isAdmin){
			this.router.navigate(['notify']);	
		}else{
			this.router.navigate(['notification']);	
		}
	}
 
}
