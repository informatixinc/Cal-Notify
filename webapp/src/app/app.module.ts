import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule }   from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent }  from './app.component';
import { routing, appRoutingProviders } from './app.routing';

import {ApiRequest} from './services/http/api_request';
import {UserState} from './services/user_state/user_state';

import {Home} from './components/home/home';
import {SetLocation} from './components/home/set_location';
import {NavBar} from './components/navigation/navbar';
import {Footer} from './components/navigation/footer';
import {Signup} from './components/signup/signup';
import {Faq} from './components/faq/faq';
import {Contact} from './components/contact/contact';
import {Privacy} from './components/privacy/privacy';
import {EditProfile} from './components/user/editprofile';
import {NotificationSettings} from './components/user/notification_settings';
import {DashBoard} from './components/user/dashboard';
import {Notify} from './components/admin/notify';
import {Reports} from './components/admin/reports';
import {LanguageService} from './services/language/language_service';
import {PasswordReset} from './components/user/password_reset';
import {ViewNotification} from './components/notifications/notification';
import {LoginService} from './components/home/login_service';
import {NotificationView} from "./components/notifications/notification_view";
// import {PopoverDirective} from './components/common/popover';


@NgModule({
  imports: [ BrowserModule, routing, FormsModule, HttpModule, ReactiveFormsModule ],
  declarations: [ AppComponent, Home, NavBar, Footer, Signup, Faq, Contact, Privacy, 
  	EditProfile, NotificationSettings, Notify, Reports, PasswordReset, SetLocation, DashBoard, ViewNotification, NotificationView],
  providers: [appRoutingProviders, ApiRequest, UserState, LanguageService, LoginService],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
