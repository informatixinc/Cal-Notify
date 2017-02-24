import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';
import {UserState} from '../../services/user_state/user_state';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.css'],
  providers: []
})

export class NavBar {

	constructor( private router: Router, private _languageService: LanguageService, private _userState: UserState) {}


	setLanguage(language: string){
		this._languageService.setSelectedLanguage(language);
		// location.reload();
	}

	isLoggedIn(){
		return this._userState.getLoggedIn();
	}
	
	logOut(){
		this._userState.removeSession();
		localStorage.removeItem("accountType");
		this.router.navigate(['/home']);
	}
}
