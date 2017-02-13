import {Injectable} from '@angular/core';

@Injectable()
export class UserState {
	constructor() { }

	loggedin = false;
	isAdmin = false;

}
