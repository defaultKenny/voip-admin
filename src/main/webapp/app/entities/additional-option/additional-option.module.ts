import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoipAdminSharedModule } from 'app/shared/shared.module';
import { AdditionalOptionComponent } from './additional-option.component';
import { AdditionalOptionDetailComponent } from './additional-option-detail.component';
import { AdditionalOptionUpdateComponent } from './additional-option-update.component';
import { AdditionalOptionDeletePopupComponent, AdditionalOptionDeleteDialogComponent } from './additional-option-delete-dialog.component';
import { additionalOptionRoute, additionalOptionPopupRoute } from './additional-option.route';

const ENTITY_STATES = [...additionalOptionRoute, ...additionalOptionPopupRoute];

@NgModule({
  imports: [VoipAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AdditionalOptionComponent,
    AdditionalOptionDetailComponent,
    AdditionalOptionUpdateComponent,
    AdditionalOptionDeleteDialogComponent,
    AdditionalOptionDeletePopupComponent
  ],
  entryComponents: [AdditionalOptionDeleteDialogComponent]
})
export class VoipAdminAdditionalOptionModule {}
