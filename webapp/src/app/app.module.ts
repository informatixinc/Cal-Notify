import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule }   from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent }  from './app.component';
import { routing, appRoutingProviders } from './app.routing';

import {Home} from './components/home/home';
import {NavBar} from './components/navigation/navbar';
import {Notifications} from './components/notifications/notifications';

@NgModule({
  imports: [ BrowserModule, routing, FormsModule, HttpModule, ReactiveFormsModule ],
  declarations: [ AppComponent, Home, NavBar, Notifications],
  providers: [appRoutingProviders],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
