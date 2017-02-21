import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';

@Component({
  selector: 'contactsettings',
  templateUrl: './notification_settings.html',
  styleUrls: ['./notification_settings.css'],
  providers: [],
})

export class NotificationSettings {

	constructor( private router: Router, private _languageService: LanguageService) {}

}
