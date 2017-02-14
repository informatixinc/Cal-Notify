import {Component} from '@angular/core';
import {Router} from '@angular/router';

import {UserState} from '../../services/user_state/user_state';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.css'],
  providers: []
})

export class NavBar {

	constructor( private router: Router, private _userState: UserState) {}

	isLoggedIn(){
		return this._userState.getLoggedIn();
	}

	logOut(){
		this._userState.removeSession();
		this.router.navigate(['/home']);
	}
}
