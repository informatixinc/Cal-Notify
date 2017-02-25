// import {Component} from '@angular/core';
// import {LanguageService} from '../../services/language/language_service';

// @Component({
//   selector: 'custompopover',
//   templateUrl:  `
//   	<div>
//     	<div class="popup-title"></div>
//     	<div class="popup-body"></div>
//     	<div class="popup-footer" *ngIf="popup.buttons">
//         	<button *ngIf="popup.buttons.OK" (click)="popup.buttons.OK()">Ok</button>
//         	<button *ngIf="popup.buttons.CANCEL" (click)="popup.buttons.CANCEL()">Cancel</button>
//         </div>
//     </div>`,
//   styles: [`
//     .popup-title, .popup-body, .popup-footer {
//       padding: 10px;
//       text-align: center;
//     }
//     .popup-header  {
//       font-weight: bold;
//       font-size: 18px;
//       border-bottom: 1px solid #ccc;
//     }
//   `] ,
//   providers: [LanguageService],
// })
// export class CustomPopover {
//   private element: HTMLElement;

//   constructor(private _languageService: LanguageService){
    
//   }
// }