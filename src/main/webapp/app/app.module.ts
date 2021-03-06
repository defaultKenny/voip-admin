import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import './vendor';
import { VoipAdminSharedModule } from 'app/shared/shared.module';
import { VoipAdminCoreModule } from 'app/core/core.module';
import { VoipAdminAppRoutingModule } from './app-routing.module';
import { VoipAdminHomeModule } from './home/home.module';
import { VoipAdminEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    VoipAdminSharedModule,
    VoipAdminCoreModule,
    VoipAdminHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    VoipAdminEntityModule,
    VoipAdminAppRoutingModule,
    NgbModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class VoipAdminAppModule {}
