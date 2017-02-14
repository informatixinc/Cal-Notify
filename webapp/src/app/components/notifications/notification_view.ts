import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';

@Component({
  selector: 'notificationview',
  templateUrl: './notification_view.html',
  styleUrls: ['./notification_view.css'],
  providers: [],
})

export class NotificationView {

	constructor( private router: Router, private _languageService: LanguageService) {}

 
}
