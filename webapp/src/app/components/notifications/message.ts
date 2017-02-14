import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';

@Component({
  selector: 'message',
  templateUrl: './message.html',
  styleUrls: ['./message.css'],
  providers: [],
})

export class Message {

	constructor(private router: Router, private _languageService: LanguageService ) {}

 
}
