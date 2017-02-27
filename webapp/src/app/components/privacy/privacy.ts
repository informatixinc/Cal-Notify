import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';

@Component({
  selector: 'privacy',
  templateUrl: './privacy.html',
  styleUrls: ['./privacy.css'],
  providers: [],
})

export class Privacy {

	constructor( private router: Router, private _languageService: LanguageService) {}

	ngOnInit(){
		setTimeout(function() {
			window.scrollTo(0,0);
		}, 100);
	}

 
}
