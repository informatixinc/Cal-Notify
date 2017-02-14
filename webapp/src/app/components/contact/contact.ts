import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';

@Component({
  selector: 'contact',
  templateUrl: './contact.html',
  styleUrls: ['./contact.css'],
  providers: [LanguageService],
})

export class Contact {

	constructor(private router: Router, private _languageService: LanguageService) {}

}
