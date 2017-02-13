import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {Home} from './components/home/home';
import {Signup} from './components/signup/signup';
import {Faq} from './components/faq/faq';
import {Contact} from './components/contact/contact';
import {Privacy} from './components/privacy/privacy';

const appRoutes: Routes = [
  { path: 'signup', component: Signup },
  { path: 'faq', component: Faq },
  { path: 'contact', component: Contact },
  { path: 'privacy', component: Privacy },
  { path: '**', component: Home },
];

export const appRoutingProviders: any[] = [];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);