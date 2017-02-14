import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LanguageService} from '../../services/language/language_service';

@Component({
  selector: 'editprofile',
  templateUrl: './editprofile.html',
  styleUrls: ['./editprofile.css'],
  providers: [],
})

export class EditProfile {

	constructor( private router: Router, private _languageService: LanguageService) {}

 
}
