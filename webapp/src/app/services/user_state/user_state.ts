import {Injectable} from '@angular/core';

@Injectable()
export class UserState {
	constructor() { }

	isAdmin = false;

	getLoggedIn(){
		if(localStorage.getItem("session") != null){
			return true;
		}else{
			return false;
		}
	}

	setSesstion(session: string){
		localStorage.setItem("session", session);
	}

	getSession(){
		return localStorage.getItem("session");
	}

	removeSession(){
		localStorage.removeItem("session");
	}
}
