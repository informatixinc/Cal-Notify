import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';
import {ApiRequest} from '../../services/http/api_request';
import {UserState} from '../../services/user_state/user_state';


@Component({
  selector: 'home',
  templateUrl: './home.html',
  styleUrls: ['./home.css'],
  providers: [],
})

export class Home {

	constructor( private router: Router, private _apiRequest: ApiRequest, private _userState: UserState, private _languageService: LanguageService) {}

	ngOnInit(){
		setTimeout(() => {
			document.getElementsByClassName("notification-body")[0]["style"].height = document.getElementsByClassName("main-body")[0].clientHeight + "px";
		},1);
	}

	signup(){
		this.router.navigate(['signup']);
	}

	login(){
		this._userState.setSession("12345");
		if(this._userState.isAdmin){
			this.router.navigate(['notify']);	
		}else{
			this.router.navigate(['notification']);	

		}
	}
 
}
