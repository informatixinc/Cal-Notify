// import { async, ComponentFixture, TestBed  } from '@angular/core/testing';
// import {Home} from './home';
// import { By }              from '@angular/platform-browser';
// import { DebugElement }    from '@angular/core';

// describe('Home (selector)', () => {

//   let comp:    Home;
//   let fixture: ComponentFixture<Home>;
//   let de:      DebugElement;
//   let el:      HTMLElement;

//   // async beforeEach
//   beforeEach(async(() => {
//     TestBed.configureTestingModule({
//       declarations: [ Home ], // declare the test component
//     })
//     .overrideComponent(Home, {
//         set: {
//             templateUrl: '/src/app/components/home/home.html'
//         }}
//     )
//     .compileComponents();  // compile template and css
//   }));

//   // synchronous beforeEach
//       beforeEach(() => {
//          fixture = TestBed.createComponent(Home);

//     comp = fixture.componentInstance; // BannerComponent test instance

//     // query for the title <h1> by CSS element selector
//     de = fixture.debugElement.query(By.css('h1'));
//     el = de.nativeElement;
//   });

//   it('should display original title', () => {
//   	fixture.detectChanges();
//     expect(el.textContent).toEqual('Welcome to Calnotify');
//   });

//   // it('should display original title', () => {
//   //   fixture.detectChanges();
//   //   expect(el.textContent).toContain(comp.title);
//   // });

//   // it('should display a different test title', () => {
//   //   comp.title = 'Test Title';
//   //   fixture.detectChanges();
//   //   expect(el.textContent).toContain('Test Title');
//   //   });

// });
