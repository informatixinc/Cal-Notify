import {TestBed, ComponentFixture, async } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
import { SpyLocation }         from '@angular/common/testing';
import { By }              from '@angular/platform-browser';
import { DebugElement }    from '@angular/core';
import { AppModule } from '../../app.module';
import {Home} from './home';
import {Faq} from '../faq/faq';
import {ApiRequest} from '../../services/http/api_request';

describe('home', () => {
	let comp:    Home;
	let fixture: ComponentFixture<Home>;
 	let router:   Router;
 	let location: SpyLocation;

    beforeEach(() => {

        TestBed.configureTestingModule({
            declarations: [
		   		Home,
		      	Faq
		    ]
            ,imports: [
            	
            	AppModule,
                RouterTestingModule.withRoutes([])
            ],
        }).compileComponents();

    });

    it('should navigate', () => {
       	fixture = TestBed.createComponent(Home);
        expect(location.path()).toEqual('/faq', 'after initialNavigation()');
		//expectElementOf(DashBoard);
    });


 //  	beforeEach(async(() => {
	//     TestBed.configureTestingModule({
	//       declarations: [ Home ], // declare the test component
	//     })
	//     .compileComponents(); 
	//     fixture = TestBed.createComponent(Home);
	//     comp = fixture.componentInstance;


	//     it('should have a defined component', () => {
	//         expect(Home).toBeDefined();
	//     });
	// 	it('should validate email', function(){

	// 	});
	// 	it('should validate password', function(){

	// 	});
	// }));
});