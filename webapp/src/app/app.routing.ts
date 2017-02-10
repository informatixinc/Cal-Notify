import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {Home} from './components/home/home';
import {Signup} from './components/signup/signup';

const appRoutes: Routes = [
  { path: '**', component: Home },
];

export const appRoutingProviders: any[] = [];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);