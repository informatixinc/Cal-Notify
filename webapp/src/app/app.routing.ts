import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {Home} from './components/home/home';
import {Signup} from './components/signup/signup';
import {Faq} from './components/faq/faq';
import {Contact} from './components/contact/contact';
import {Privacy} from './components/privacy/privacy';
import {EditProfile} from './components/user/editprofile';
import {NotificationSettings} from './components/user/notification_settings';
import {NotificationView} from './components/notifications/notification_view';
import {Notifications} from './components/notifications/notifications';
import {Message} from './components/notifications/message';
import {Notify} from './components/admin/notify';
import {Reports} from './components/admin/reports';


const appRoutes: Routes = [
  { path: 'signup', component: Signup },
  { path: 'faq', component: Faq },
  { path: 'contact', component: Contact },
  { path: 'privacy', component: Privacy },
  { path: 'message/:message_id', component: Message },
  { path: 'account', component: EditProfile },
  { path: 'notifications', component: NotificationView },
  { path: 'notification', component: NotificationSettings },
  { path: 'notify', component: Notify },
  { path: 'reports', component: Reports },
  { path: '**', component: Home },
];

export const appRoutingProviders: any[] = [];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);