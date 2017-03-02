import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';

@Component({
  selector: 'footer',
  templateUrl: './footer.html',
  styleUrls: ['./footer.css'],
  providers: [],
})

export class Footer {

	constructor( private router: Router, private _languageService: LanguageService) {}
	
	setLanguage(language: string){
		this._languageService.setSelectedLanguage(language);
		location.reload();
	}

	 
}