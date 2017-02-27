import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {Home} from './components/home/home';
import {SetLocation} from './components/home/set_location';
import {Signup} from './components/signup/signup';
import {Faq} from './components/faq/faq';
import {Contact} from './components/contact/contact';
import {Privacy} from './components/privacy/privacy';
import {EditProfile} from './components/user/editprofile';
import {NotificationSettings} from './components/user/notification_settings';
import {Notify} from './components/admin/notify';
import {Reports} from './components/admin/reports';
import {PasswordReset} from './components/user/password_reset';
import {DashBoard} from './components/user/dashboard';
import {ViewNotification} from './components/notifications/notification';
import {NotificationView} from "./components/notifications/notification_view";
import {AuthGuard} from "./auth_guard";

const appRoutes: Routes = [
  { path: 'password-reset', component: PasswordReset },
  { path: 'signup', component: Signup },
  { path: 'faq', component: Faq },
  { path: 'contact', component: Contact },
  { path: 'privacy', component: Privacy },
  { path: 'account', component: EditProfile, canActivate:[AuthGuard] },
  { path: 'notification', component: NotificationSettings, canActivate:[AuthGuard] },
  { path: 'notificationview', component: NotificationView },
  { path: 'notification/:notification_id', component: ViewNotification },
  { path: 'notify', component: Notify, canActivate:[AuthGuard]},
  { path: 'reports', component: Reports, canActivate:[AuthGuard]},
  { path: 'home', component: Home },
  { path: 'setlocation', component: SetLocation },
  { path: 'dashboard', component: DashBoard, canActivate:[AuthGuard]},
  { path: '**', component: Home },
];

export const appRoutingProviders: any[] = [];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);