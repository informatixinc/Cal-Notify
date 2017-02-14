import {Component} from '@angular/core';
import {Router} from '@angular/router';


@Component({
  selector: 'notificationview',
  templateUrl: './notification_view.html',
  styleUrls: ['./notification_view.css'],
  providers: [],
})

export class NotificationView {

	constructor( private router: Router) {}

 	goToMessage(messageId: string){
 		this.router.navigate(['message',messageId]);
 	}
}
