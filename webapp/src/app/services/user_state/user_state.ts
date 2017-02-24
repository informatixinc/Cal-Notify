import {Injectable} from '@angular/core';
import {Address} from '../../components/common/address';
import {Point} from '../../components/common/point';

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

	setSession(session: string){
		localStorage.setItem("session", session);
	}

	getSession(){
		return localStorage.getItem("session");
	}

	removeSession(){
		localStorage.removeItem("session");
	}

	resetLocation(){
		localStorage.removeItem("address");
	}

	setAddress(address: Address){
		localStorage.setItem("address", JSON.stringify(address));
	}

	getAddress():Address{
		if(localStorage.getItem("address") == null){
			return new Address();
		}
		return (JSON.parse(localStorage.getItem("address")));	
	}

	setGeoLocation(point: Point){
		localStorage.setItem("geoLocation", JSON.stringify(point));
	}

	getGeoLocation():Point{
		if(localStorage.getItem("geoLocation") == null){
			return new Point();
		}
		return (JSON.parse(localStorage.getItem("geoLocation")));	
	}
}
