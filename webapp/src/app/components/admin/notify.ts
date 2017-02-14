import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';

@Component({
  selector: 'notify',
  templateUrl: './notify.html',
  styleUrls: ['./notify.css'],
  providers: [],
})

export class Notify {

	constructor(private router: Router, private _languageService: LanguageService ) {}

 
}
