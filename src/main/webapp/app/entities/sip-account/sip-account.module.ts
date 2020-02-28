import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoipAdminSharedModule } from 'app/shared/shared.module';
import { SipAccountComponent } from './sip-account.component';
import { SipAccountDetailComponent } from './sip-account-detail.component';
import { SipAccountUpdateComponent } from './sip-account-update.component';
import { SipAccountDeletePopupComponent, SipAccountDeleteDialogComponent } from './sip-account-delete-dialog.component';
import { sipAccountRoute, sipAccountPopupRoute } from './sip-account.route';

const ENTITY_STATES = [...sipAccountRoute, ...sipAccountPopupRoute];

@NgModule({
  imports: [VoipAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SipAccountComponent,
    SipAccountDetailComponent,
    SipAccountUpdateComponent,
    SipAccountDeleteDialogComponent,
    SipAccountDeletePopupComponent
  ],
  entryComponents: [SipAccountDeleteDialogComponent]
})
export class VoipAdminSipAccountModule {}
