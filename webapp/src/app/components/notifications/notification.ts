import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';

@Component({
  selector: 'notification',
  templateUrl: './notification.html',
  styleUrls: ['./notification.css'],
  providers: [],
})

export class Notification {

	constructor(private router: Router, private _languageService: LanguageService ) {}

 
}