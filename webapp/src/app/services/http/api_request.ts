import {Injectable} from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import {Observable} from 'rxjs/Rx';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class ApiRequest {
	constructor(private http: Http) { }

	// private apiUrl = "http://localhost:8081/api/";
    private apiUrl = "/api/";

	public doRequest(urlParam: string, request: any) {
      	sessionStorage.setItem("lastActive", new Date().getTime().toString());

	 	let bodyString = JSON.stringify(request); 
        let headers = new Headers({ 'Content-Type': 'application/json', 'Authorization':localStorage.getItem("session")});
        let options = new RequestOptions({ headers: headers }); 

        return this.http.post(this.apiUrl+urlParam, JSON.stringify(request), options) 
                         .map((res:Response) => res.json()) 
                         .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
	}

}
