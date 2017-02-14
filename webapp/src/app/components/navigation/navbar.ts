import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.css'],
  providers: []
})

export class NavBar {

	constructor( private router: Router, private _languageService: LanguageService) {}

	setLanguage(language: string){
		this._languageService.setSelectedLanguage(language);
		location.reload();
	}

}
