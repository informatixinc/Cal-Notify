import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';

@Component({
  selector: 'faq',
  templateUrl: './faq.html',
  styleUrls: ['./faq.css'],
  providers: [],
})

export class Faq {

	elements: boolean[] = [false];

	constructor(private router: Router, private _languageService: LanguageService) {}

 	toggleHidden(element: number){
		this.elements[element] = !this.elements[element];
	}

	// ngOnInit(){
	// 	setTimeout(function() {
	// 		window.scrollTo(0,0);
	// 	}, 100);
	// }
}
