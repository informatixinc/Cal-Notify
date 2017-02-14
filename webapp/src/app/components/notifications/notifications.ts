import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';

@Component({
  selector: 'notifications',
  templateUrl: './notifications.html',
  styleUrls: ['./notifications.css'],
  providers: [],
})

export class Notifications {

	constructor( private router: Router, private _languageService: LanguageService) {}

 	goToMessage(messageId: string){
 		this.router.navigate(['message',messageId]);
 	}
}
