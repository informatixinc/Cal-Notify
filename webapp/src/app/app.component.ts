import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app',
  providers: [],
  template: `<router-outlet></router-outlet>`
})

export class AppComponent { 
	constructor(private router: Router) { }

	    ngOnInit() {
	        this.router.events.subscribe((event) => {
	            if (!(event instanceof NavigationEnd)) {
	                return;
	            }
	            window.scrollTo(0, 0);
	        });
	    }
}
