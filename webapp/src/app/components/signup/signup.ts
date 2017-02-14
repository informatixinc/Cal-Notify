import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';

@Component({
  selector: 'signup',
  templateUrl: './signup.html',
  styleUrls: ['./signup.css'],
  providers: [],
})

export class Signup {

	constructor(private router: Router, private _languageService: LanguageService) {}

 
}
