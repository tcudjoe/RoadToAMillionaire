import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedRoutingModule } from './shared-routing.module';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import { HomeComponent } from './pages/home/home.component';
import { LayoutComponent } from './components/layout/layout.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';
import { PricingComponent } from './pages/pricing/pricing.component';
import { SharedNavbarComponent } from './components/shared-navbar/shared-navbar.component';
import { CallToActionComponent } from './components/call-to-action/call-to-action.component';


@NgModule({
  declarations: [
    PageNotFoundComponent,
    HomeComponent,
    LayoutComponent,
    NavbarComponent,
    FooterComponent,
    PricingComponent,
    SharedNavbarComponent,
    CallToActionComponent
  ],
  imports: [
    CommonModule,
    SharedRoutingModule
  ]
})
export class SharedModule { }
