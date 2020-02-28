import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoipAdminSharedModule } from 'app/shared/shared.module';
import { PbxAccountComponent } from './pbx-account.component';
import { PbxAccountDetailComponent } from './pbx-account-detail.component';
import { PbxAccountUpdateComponent } from './pbx-account-update.component';
import { PbxAccountDeletePopupComponent, PbxAccountDeleteDialogComponent } from './pbx-account-delete-dialog.component';
import { pbxAccountRoute, pbxAccountPopupRoute } from './pbx-account.route';

const ENTITY_STATES = [...pbxAccountRoute, ...pbxAccountPopupRoute];

@NgModule({
  imports: [VoipAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PbxAccountComponent,
    PbxAccountDetailComponent,
    PbxAccountUpdateComponent,
    PbxAccountDeleteDialogComponent,
    PbxAccountDeletePopupComponent
  ],
  entryComponents: [PbxAccountDeleteDialogComponent]
})
export class VoipAdminPbxAccountModule {}
