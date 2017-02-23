import {Injectable} from '@angular/core';
import {LoginObject} from './login_object';
import {Router, RouterModule} from '@angular/router';
declare var jsSHA: any;

@Injectable()
export class LoginService {
	constructor(private router: Router ) {}
	
	prepareLogin(email: string, password: string){
		var shaObj = new jsSHA("SHA-512", "TEXT");
		shaObj.update(password);
		var login: LoginObject = new LoginObject();
		login.email = email;
		login.password = shaObj.getHash("HEX");
		return login;
	}


}
