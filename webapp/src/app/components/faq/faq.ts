import {Component} from '@angular/core';
import {Router} from '@angular/router';


@Component({
  selector: 'faq',
  templateUrl: './faq.html',
  styleUrls: ['./faq.css'],
  providers: [],
})

export class Faq {

	elements: boolean[] = [false];

	constructor( private router: Router) {}

 	toggleHidden(element: number){
		this.elements[element] = !this.elements[element];
	}
}
